package com.example.demo.dto;

import com.example.demo.models.ShippingAddress;
import lombok.Data;

@Data
public class CheckoutRequest {
    private String paymentMethod; // VD: "COD", "VNPAY"
    private ShippingAddress shippingAddress;
}