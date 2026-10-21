package com.example.demo.repositories;

import com.example.demo.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    // Lấy tất cả đánh giá của 1 sản phẩm cụ thể
    List<Review> findByProductIdOrderByCreatedAtDesc(String productId);
}