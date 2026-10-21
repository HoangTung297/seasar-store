package com.example.demo.controllers;

import com.example.demo.dto.ReviewRequest;
import com.example.demo.models.Review;
import com.example.demo.models.User;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 1. API Xem danh sách đánh giá của 1 sản phẩm (Công khai)
    // GET http://localhost:8080/api/reviews/product/SP_002
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    // 2. API Thêm đánh giá (Bắt buộc đăng nhập)
    // POST http://localhost:8080/api/reviews
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            Review review = reviewService.addReview(user.getId(), request);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}