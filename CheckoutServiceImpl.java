package com.example.D288_Task1_Backend_Project.services;

import com.example.D288_Task1_Backend_Project.dao.CartItemRepository;
import com.example.D288_Task1_Backend_Project.dao.CartRepository;
import com.example.D288_Task1_Backend_Project.dao.CustomerRepository;
import com.example.D288_Task1_Backend_Project.dao.ExcursionRepository;
import com.example.D288_Task1_Backend_Project.dao.VacationRepository;
import com.example.D288_Task1_Backend_Project.entities.Cart;
import com.example.D288_Task1_Backend_Project.entities.CartItem;
import com.example.D288_Task1_Backend_Project.entities.Customer;
import com.example.D288_Task1_Backend_Project.entities.Excursion;
import com.example.D288_Task1_Backend_Project.entities.StatusType;
import com.example.D288_Task1_Backend_Project.entities.Vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final VacationRepository vacationRepository;
    private final ExcursionRepository excursionRepository;

    public CheckoutServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               CustomerRepository customerRepository,
                               VacationRepository vacationRepository,
                               ExcursionRepository excursionRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.vacationRepository = vacationRepository;
        this.excursionRepository = excursionRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        validatePurchase(purchase);

        Customer customer = customerRepository.findById(purchase.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer was not found."));

        Cart cart = purchase.getCart();
        cart.setCustomer(customer);
        cart.setStatus(StatusType.ordered);
        cart.setOrderTrackingNumber(UUID.randomUUID().toString());

        Cart savedCart = cartRepository.save(cart);

        for (CartItem incomingItem : purchase.getCartItems()) {
            if (incomingItem.getVacation() == null || incomingItem.getVacation().getId() == null) {
                throw new IllegalArgumentException("Each cart item must contain a vacation.");
            }

            Vacation vacation = vacationRepository.findById(incomingItem.getVacation().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Vacation was not found."));

            CartItem item = new CartItem();
            item.setCart(savedCart);
            item.setVacation(vacation);
            item.setExcursions(new HashSet<>());

            if (incomingItem.getExcursions() != null) {
                for (Excursion incomingExcursion : incomingItem.getExcursions()) {
                    if (incomingExcursion.getId() == null) {
                        throw new IllegalArgumentException("Each excursion must contain an id.");
                    }
                    Excursion excursion = excursionRepository.findById(incomingExcursion.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Excursion was not found."));
                    item.getExcursions().add(excursion);
                }
            }

            cartItemRepository.save(item);
        }

        return new PurchaseResponse(savedCart.getOrderTrackingNumber());
    }

    private void validatePurchase(Purchase purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase is required.");
        }
        if (purchase.getCustomer() == null || purchase.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Customer is required.");
        }
        if (purchase.getCart() == null) {
            throw new IllegalArgumentException("Cart is required.");
        }
        if (purchase.getCart().getPackage_price() == null ||
                purchase.getCart().getPackage_price().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Package price must be zero or greater.");
        }
        if (purchase.getCart().getParty_size() < 1) {
            throw new IllegalArgumentException("Party size must be at least 1.");
        }
        if (purchase.getCartItems() == null || purchase.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("At least one cart item is required.");
        }
    }
}