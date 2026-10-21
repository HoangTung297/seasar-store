package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    // Lấy lịch sử mua hàng của 1 user cụ thể
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}