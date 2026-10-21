package com.example.demo.repositories;

import com.example.demo.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    // Tìm giỏ hàng theo ID người dùng
    Optional<Cart> findByUserId(String userId);
}