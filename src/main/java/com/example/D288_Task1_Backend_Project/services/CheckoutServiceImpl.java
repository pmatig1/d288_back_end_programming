package com.example.D288_Task1_Backend_Project.services;

import com.example.D288_Task1_Backend_Project.dao.CartItemRepository;
import com.example.D288_Task1_Backend_Project.dao.CartRepository;
import com.example.D288_Task1_Backend_Project.dao.CustomerRepository;
import com.example.D288_Task1_Backend_Project.entities.Cart;
import com.example.D288_Task1_Backend_Project.entities.CartItem;
import com.example.D288_Task1_Backend_Project.entities.Customer;
import com.example.D288_Task1_Backend_Project.entities.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository,
                               CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // 1. Get and save Customer
        Customer customer = purchase.getCustomer();
        if (customer != null) {
            customerRepository.save(customer);
        }

        // 2. Get Cart and populate details
        Cart cart = purchase.getCart();
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);
        cart.setCustomer(customer);

        // Save Cart to generate cart_id in DB
        cartRepository.save(cart);

        // 3. Get CartItems, attach saved Cart reference, and save individually
        Set<CartItem> cartItems = purchase.getCartItems();
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                item.setCart(cart);
                cartItemRepository.save(item);
            }
        }

        return new PurchaseResponse(orderTrackingNumber);
    }
}