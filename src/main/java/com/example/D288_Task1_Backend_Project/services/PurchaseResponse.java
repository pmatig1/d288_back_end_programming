package com.example.D288_Task1_Backend_Project.services;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseResponse {
    private String orderTrackingNumber;

    public PurchaseResponse(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
}
