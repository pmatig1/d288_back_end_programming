package com.example.demo.services;

import lombok.Getter;
import lombok.Setter;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import java.util.Set;

@Getter @Setter
public class Purchase {
    private Cart cart;
    private Set<CartItem> cartItems;
}
