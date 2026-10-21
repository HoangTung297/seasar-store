package com.example.demo.components;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;

    // Thêm 3 Repository mới
    @Autowired private CartRepository cartRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ReviewRepository reviewRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // --- 1. SEEDER TÀI KHOẢN ---
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setId("USER_ADMIN_01");
            admin.setEmail("admin@seasarstore.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFullName("Admin Bóng Rổ");
            admin.setPhone("0987654321");
            admin.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
            userRepository.save(admin);
            System.out.println("Đã tạo tài khoản Admin");
        }

        // --- 2. SEEDER DANH MỤC ---
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category(); cat1.setId("CAT_GIAY"); cat1.setName("Giày Bóng Rổ"); cat1.setSlug("giay-bong-ro"); categoryRepository.save(cat1);
            Category cat2 = new Category(); cat2.setId("CAT_QUANAO"); cat2.setName("Quần Áo Bóng Rổ"); cat2.setSlug("quan-ao-bong-ro"); categoryRepository.save(cat2);
            Category cat3 = new Category(); cat3.setId("CAT_PHUKIEN"); cat3.setName("Phụ Kiện"); cat3.setSlug("phu-kien"); categoryRepository.save(cat3);
        }

        // --- 3. SEEDER SẢN PHẨM ---
        if (productRepository.count() == 0) {
            Random random = new Random();
            String[] shoeBrands = {"Nike", "Jordan", "Li-Ning", "Anta", "Adidas"};
            String[] colors = {"Bred (Đen/Đỏ)", "Trắng", "Xanh Navy", "Oreo"};

            for (int i = 1; i <= 50; i++) {
                Product p = new Product();
                p.setId("SP_" + String.format("%03d", i));
                int catIndex = random.nextInt(3);
                p.setCategoryId(catIndex == 0 ? "CAT_GIAY" : (catIndex == 1 ? "CAT_QUANAO" : "CAT_PHUKIEN"));
                p.setName((catIndex == 0 ? "Giày" : (catIndex == 1 ? "Áo" : "Balo")) + " " + shoeBrands[random.nextInt(shoeBrands.length)] + " Pro " + i);
                p.setBasePrice(800000 + random.nextInt(370) * 10000);
                p.setImages(Arrays.asList("https://via.placeholder.com/500x500?text=SP+" + i));

                ProductVariant var1 = new ProductVariant(); var1.setColor(colors[random.nextInt(colors.length)]); var1.setSize(41); var1.setStock(15);
                ProductVariant var2 = new ProductVariant(); var2.setColor(colors[random.nextInt(colors.length)]); var2.setSize(42); var2.setStock(5);
                p.setVariants(Arrays.asList(var1, var2));
                productRepository.save(p);
            }
            System.out.println("Đã tự động sinh 50 sản phẩm!");
        }

        // --- 4. SEEDER GIỎ HÀNG (Cho Admin) ---
        if (cartRepository.count() == 0) {
            Cart cart = new Cart();
            cart.setUserId("USER_ADMIN_01"); // Giỏ hàng của tài khoản admin

            CartItem item = new CartItem();
            item.setProductId("SP_001");
            item.setColor("Trắng");
            item.setSize(42);
            item.setQuantity(2);

            cart.setItems(Arrays.asList(item));
            cartRepository.save(cart);
            System.out.println("Đã tạo 1 Giỏ hàng mẫu");
        }

        // --- 5. SEEDER ĐƠN HÀNG (Admin đã đặt) ---
        if (orderRepository.count() == 0) {
            Order order = new Order();
            order.setUserId("USER_ADMIN_01");
            order.setStatus("DELIVERED"); // Đã giao hàng thành công
            order.setPaymentMethod("COD");
            order.setPaid(true);
            order.setCreatedAt(LocalDateTime.now().minusDays(2)); // Đặt từ 2 ngày trước

            ShippingAddress address = new ShippingAddress();
            address.setReceiverName("Nguyễn Hoàng Tùng");
            address.setPhone("0912345678");
            address.setAddress("Trường Đại học Xây dựng Hà Nội (HUCE), 55 Giải Phóng");
            order.setShippingAddress(address);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId("SP_002");
            orderItem.setName("Giày Bóng Rổ Jordan Pro 2");
            orderItem.setColor("Bred (Đen/Đỏ)");
            orderItem.setSize(42);
            orderItem.setPrice(3500000); // Lưu cứng giá lúc mua
            orderItem.setQuantity(1);

            order.setItems(Arrays.asList(orderItem));
            order.setTotalAmount(3500000);
            orderRepository.save(order);
            System.out.println("Đã tạo 1 Đơn hàng mẫu");
        }

        // --- 6. SEEDER ĐÁNH GIÁ SẢN PHẨM ---
        if (reviewRepository.count() == 0) {
            Review review1 = new Review();
            review1.setProductId("SP_002"); // Đánh giá cho sản phẩm vừa mua
            review1.setUserId("USER_ADMIN_01");
            review1.setUserName("Nguyễn Hoàng Tùng");
            review1.setRating(5);
            review1.setComment("Giày lên chân cực êm, cày sân outdoor thoải mái. Giao hàng rất nhanh!");
            review1.setCreatedAt(LocalDateTime.now().minusDays(1));
            reviewRepository.save(review1);

            Review review2 = new Review();
            review2.setProductId("SP_002");
            review2.setUserId("USER_GUEST_01");
            review2.setUserName("Khách Hàng Ẩn Danh");
            review2.setRating(4);
            review2.setComment("Form hơi ôm xíu, khuyên ae nên tăng nửa size nhé.");
            review2.setCreatedAt(LocalDateTime.now());
            reviewRepository.save(review2);

            System.out.println("Đã tạo 2 Đánh giá mẫu");
        }
    }
}