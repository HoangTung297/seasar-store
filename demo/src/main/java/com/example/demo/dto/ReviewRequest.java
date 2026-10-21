package com.example.demo.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private String productId;
    private int rating;
    private String comment;
}