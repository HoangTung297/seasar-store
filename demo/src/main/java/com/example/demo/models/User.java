package com.example.demo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private String fullName;
    private String phone;

    // Lưu các quyền của người dùng, ví dụ: "ROLE_USER", "ROLE_ADMIN"
    private List<String> roles;
}