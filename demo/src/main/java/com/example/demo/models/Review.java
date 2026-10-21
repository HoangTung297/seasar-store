package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;

    private String productId;
    private String userId;

    private String userName; // Lưu sẵn tên người đánh giá để hiển thị cho nhanh

    private int rating; // Số sao từ 1 đến 5
    private String comment;

    private LocalDateTime createdAt;
}