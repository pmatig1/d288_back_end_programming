package com.example.D288_Task1_Backend_Project.services;

import com.example.D288_Task1_Backend_Project.entities.Customer;
import lombok.Getter;
import lombok.Setter;
import com.example.D288_Task1_Backend_Project.entities.Cart;
import com.example.D288_Task1_Backend_Project.entities.CartItem;
import java.util.Set;

@Getter @Setter
public class Purchase {
    private Cart cart;
    private Set<CartItem> cartItems;

    public Customer getCustomer() {
        return null;
    }
}