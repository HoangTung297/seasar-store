package com.example.demo.controllers;

import com.example.demo.dto.AddToCartRequest;
import com.example.demo.models.Cart;
import com.example.demo.models.User;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Hàm tiện ích: Tự động trích xuất ID người dùng từ mã Token đang gọi API
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    // 1. API Xem giỏ hàng cá nhân
    // GET http://localhost:8080/api/carts/my-cart
    @GetMapping("/my-cart")
    public ResponseEntity<Cart> getMyCart() {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    // 2. API Thêm hàng vào giỏ
    // POST http://localhost:8080/api/carts/add
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request) {
        String userId = getCurrentUserId();
        Cart updatedCart = cartService.addToCart(
                userId,
                request.getProductId(),
                request.getColor(),
                request.getSize(),
                request.getQuantity()
        );
        return ResponseEntity.ok(updatedCart);
    }
}