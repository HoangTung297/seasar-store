package com.example.demo.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String productId;
    private String color;
    private int size;
    private int quantity;
}