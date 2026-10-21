package com.example.demo.models;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private String name;      // Tên sản phẩm (lưu cứng)
    private String color;
    private int size;
    private double price;     // Giá tại thời điểm đặt hàng (lưu cứng)
    private int quantity;
}