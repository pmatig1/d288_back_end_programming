package com.example.D288_Task1_Backend_Project.controllers;

import com.example.D288_Task1_Backend_Project.services.CheckoutService;
import com.example.D288_Task1_Backend_Project.services.Purchase;
import com.example.D288_Task1_Backend_Project.services.PurchaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@Valid @RequestBody Purchase purchase) {
        PurchaseResponse response = checkoutService.placeOrder(purchase);
        return ResponseEntity.ok(response);
    }
}
