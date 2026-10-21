package com.example.demo.services;

import com.example.demo.dto.CheckoutRequest;
import com.example.demo.models.*;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired private CartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;

    public Order checkout(String userId, CheckoutRequest request) {
        // 1. Lấy giỏ hàng của user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng!"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng đang trống!");
        }

        // 2. Duyệt qua giỏ hàng để lấy giá trị thực tế của sản phẩm
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cart.getItems()) {
            // Truy vấn DB để lấy Tên và Giá mới nhất, đề phòng giá đã thay đổi
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + cartItem.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setName(product.getName());
            orderItem.setColor(cartItem.getColor());
            orderItem.setSize(cartItem.getSize());
            orderItem.setPrice(product.getBasePrice()); // Lưu cứng giá tại thời điểm đặt
            orderItem.setQuantity(cartItem.getQuantity());

            orderItems.add(orderItem);
            totalAmount += (product.getBasePrice() * cartItem.getQuantity());
        }

        // 3. Khởi tạo Đơn hàng
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("PENDING"); // Trạng thái chờ xác nhận
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaid(false);
        order.setShippingAddress(request.getShippingAddress());
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 4. Xóa sạch dữ liệu trong giỏ hàng sau khi đặt thành công
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    // Lấy lịch sử mua hàng của user
    public List<Order> getMyOrders(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Dành cho Admin: Lấy danh sách toàn bộ đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Dành cho Admin: Cập nhật trạng thái đơn hàng
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã đơn hàng: " + orderId));

        // Cập nhật trạng thái (VD: PENDING, SHIPPING, DELIVERED, CANCELLED)
        order.setStatus(status);
        return orderRepository.save(order);
    }
}