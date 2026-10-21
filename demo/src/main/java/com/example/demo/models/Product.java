package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String categoryId; // Khóa ngoại liên kết tới ID của Category
    private String name;
    private String slug;
    private String description;
    private double basePrice;

    private List<String> images;

    // Nhúng mảng các biến thể (size, màu) vào thẳng Document này
    private List<ProductVariant> variants;
}