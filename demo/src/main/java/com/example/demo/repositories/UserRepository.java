package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Khai báo sẵn các hàm này để dùng cho tính năng Đăng nhập / Đăng ký sau này
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}