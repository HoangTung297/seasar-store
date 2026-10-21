package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    private String userId;

    // Trạng thái: PENDING (Chờ xác nhận), SHIPPING (Đang giao), DELIVERED (Đã giao), CANCELLED (Đã hủy)
    private String status;

    // Phương thức thanh toán: COD, VNPAY, MOMO
    private String paymentMethod;
    private boolean isPaid;

    private ShippingAddress shippingAddress;

    // Nhúng mảng chi tiết đơn hàng vào
    private List<OrderItem> items;

    private double totalAmount; // Tổng tiền đơn hàng
    private LocalDateTime createdAt;
}