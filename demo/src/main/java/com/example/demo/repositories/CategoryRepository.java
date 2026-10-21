package com.example.demo.repositories;

import com.example.demo.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

// Kế thừa MongoRepository, truyền vào Tên Model và Kiểu dữ liệu của ID (String)
public interface CategoryRepository extends MongoRepository<Category, String> {
}