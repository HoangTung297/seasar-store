package com.example.demo.controllers;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email không tồn tại trong hệ thống!");
        }

        User user = userOptional.get();

        // 2. Kiểm tra mật khẩu (So sánh mật khẩu người dùng nhập và mật khẩu đã băm BCrypt trong DB)
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            // 3. Nếu hợp lệ, sinh ra chuỗi JWT Token
            String token = jwtUtils.generateToken(user.getEmail());

            // 4. Trả về Token và một vài thông tin cơ bản cho Frontend
            return ResponseEntity.ok(new LoginResponse(token, user.getEmail(), user.getFullName(), user.getRoles()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không chính xác!");
        }
    }
}