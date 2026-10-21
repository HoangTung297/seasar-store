package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Lấy giỏ hàng của user. Nếu chưa có (user mới), hệ thống tự tạo một giỏ rỗng.
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });
    }

    // Xử lý logic thêm hàng
    public Cart addToCart(String userId, String productId, String color, int size, int quantity) {
        Cart cart = getCartByUserId(userId);

        // Kiểm tra xem sản phẩm (khớp cả màu và size) đã tồn tại trong giỏ chưa
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId) &&
                        item.getColor().equals(color) &&
                        item.getSize() == size)
                .findFirst();

        if (existingItem.isPresent()) {
            // Đã có -> Cộng dồn số lượng
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Chưa có -> Thêm mục mới vào mảng
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setColor(color);
            newItem.setSize(size);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        // Lưu bản ghi giỏ hàng đã cập nhật xuống MongoDB
        return cartRepository.save(cart);
    }
}