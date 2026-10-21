package com.example.demo.models;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private String color;
    private int size;
    private int quantity;
}