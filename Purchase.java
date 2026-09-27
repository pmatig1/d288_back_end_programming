package com.example.D288_Task1_Backend_Project.services;

import com.example.D288_Task1_Backend_Project.entities.Cart;
import com.example.D288_Task1_Backend_Project.entities.CartItem;
import com.example.D288_Task1_Backend_Project.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems = new HashSet<>();
}