package com.nnice.karaoke.controller;

import com.nnice.karaoke.dto.LoginRequest;
import com.nnice.karaoke.dto.LoginResponse;
import com.nnice.karaoke.dto.RegisterRequest;
import com.nnice.karaoke.dto.RegisterResponse;
import com.nnice.karaoke.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    /**
     * Endpoint Đăng nhập
     * POST /api/auth/login
     * Request body: { "tenDangNhap": "...", "matKhau": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.getTenDangNhap() == null || request.getTenDangNhap().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên đăng nhập không được để trống");
            }

            if (request.getMatKhau() == null || request.getMatKhau().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được để trống");
            }

            LoginResponse response = taiKhoanService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng nhập: " + e.getMessage());
        }
    }

    /**
     * Endpoint Đăng ký
     * POST /api/auth/register
     * Request body: {
     *   "hoTen": "...",
     *   "soDienThoai": "...",
     *   "email": "...",
     *   "tenDangNhap": "...",
     *   "matKhau": "...",
     *   "confirmMatKhau": "..."
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validate basic fields
            if (request.getHoTen() == null || request.getHoTen().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Họ tên không được để trống");
            }

            if (request.getSoDienThoai() == null || request.getSoDienThoai().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Số điện thoại không được để trống");
            }

            if (request.getTenDangNhap() == null || request.getTenDangNhap().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Tên đăng nhập không được để trống");
            }

            if (request.getMatKhau() == null || request.getMatKhau().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Mật khẩu không được để trống");
            }

            RegisterResponse response = taiKhoanService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng ký: " + e.getMessage());
        }
    }

    /**
     * Endpoint Kiểm tra token
     * GET /api/auth/verify?token=...
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Token không được để trống");
            }

            boolean isValid = taiKhoanService.getTaiKhoanFromToken(token) != null;
            if (isValid) {
                return ResponseEntity.ok().body("Token hợp lệ");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ: " + e.getMessage());
        }
    }
}
