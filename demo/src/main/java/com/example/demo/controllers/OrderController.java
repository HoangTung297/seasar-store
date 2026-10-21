package com.example.demo.controllers;

import com.example.demo.dto.CheckoutRequest;
import com.example.demo.models.Order;
import com.example.demo.models.User;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    // API Đặt hàng (Checkout)
    // POST http://localhost:8080/api/orders/checkout
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        try {
            String userId = getCurrentUserId();
            Order order = orderService.checkout(userId, request);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API Xem lịch sử đơn hàng cá nhân
    // GET http://localhost:8080/api/orders/my-orders
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders() {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }

    // 1. API cho Admin: Xem tất cả đơn hàng
    // GET http://localhost:8080/api/orders/all
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 2. API cho Admin: Đổi trạng thái đơn hàng
    // PUT http://localhost:8080/api/orders/{id}/status?status=SHIPPING
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String id,
            @RequestParam String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}