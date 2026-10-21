package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. LẤY DANH SÁCH (Có phân trang, tìm kiếm theo tên và lọc theo danh mục) - Dành cho User
    public Page<Product> getProducts(String keyword, String categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else if (categoryId != null && !categoryId.isEmpty()) {
            return productRepository.findByCategoryId(categoryId, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    // 2. LẤY CHI TIẾT SẢN PHẨM THEO ID - Dành cho trang ProductDetail
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // 3. LẤY TOÀN BỘ SẢN PHẨM (Không phân trang) - Có thể dùng cho Admin xem tổng quát
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 4. THÊM SẢN PHẨM MỚI - Dành cho Admin
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 5. CẬP NHẬT SẢN PHẨM - Dành cho Admin
    public Product updateProduct(String id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBasePrice(updatedProduct.getBasePrice());
            existingProduct.setCategoryId(updatedProduct.getCategoryId());
            existingProduct.setImages(updatedProduct.getImages());
            existingProduct.setVariants(updatedProduct.getVariants());

            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    // 6. XÓA SẢN PHẨM - Dành cho Admin
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không thể xóa. Không tìm thấy ID: " + id);
        }
        productRepository.deleteById(id);
    }
}