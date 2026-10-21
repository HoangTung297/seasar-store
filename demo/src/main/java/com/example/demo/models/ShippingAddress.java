package com.example.demo.models;

import lombok.Data;

@Data
public class ShippingAddress {
    private String receiverName;
    private String phone;
    private String address;
}