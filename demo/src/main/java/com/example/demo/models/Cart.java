package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;

    // Mỗi giỏ hàng gắn với 1 tài khoản
    private String userId;

    // Mảng các sản phẩm trong giỏ
    private List<CartItem> items;
}