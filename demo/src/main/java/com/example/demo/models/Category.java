package com.example.demo.models;

import lombok.Data; // Lombok giúp tự tạo getter/setter ẩn
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String slug;
    private String description;
}