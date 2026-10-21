package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    // Tìm kiếm theo tên sản phẩm (chữ hoa/thường đều được) và phân trang
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Lọc sản phẩm theo danh mục và phân trang
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
}