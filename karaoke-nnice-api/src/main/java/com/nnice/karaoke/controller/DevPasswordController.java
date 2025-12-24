package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.TaiKhoan;
import com.nnice.karaoke.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller để generate và update password hash
 * CHỈ DÙNG CHO DEVELOPMENT/TESTING
 */
@RestController
@RequestMapping("/api/dev")
@CrossOrigin(origins = "*")
public class DevPasswordController {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Generate BCrypt hash cho một password
     * GET /api/dev/generate-hash?password=admin123
     */
    @GetMapping("/generate-hash")
    public Map<String, String> generateHash(@RequestParam String password) {
        String hash = passwordEncoder.encode(password);
        
        Map<String, String> result = new HashMap<>();
        result.put("password", password);
        result.put("hash", hash);
        result.put("hashLength", String.valueOf(hash.length()));
        
        // Test ngay
        boolean matches = passwordEncoder.matches(password, hash);
        result.put("testMatches", String.valueOf(matches));
        
        return result;
    }

    /**
     * Update tất cả tài khoản test với password "admin123"
     * POST /api/dev/update-all-passwords
     */
    @PostMapping("/update-all-passwords")
    public Map<String, Object> updateAllPasswords() {
        String password = "admin123";
        String hash = passwordEncoder.encode(password);
        
        List<TaiKhoan> accounts = taiKhoanRepository.findAll();
        int updated = 0;
        
        for (TaiKhoan account : accounts) {
            if (account.getTenDangNhap().matches("admin|tieptan|ketoan|bep|phucvu")) {
                account.setMatKhauHash(hash);
                taiKhoanRepository.save(account);
                updated++;
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("newHash", hash);
        result.put("accountsUpdated", updated);
        result.put("testMatches", passwordEncoder.matches(password, hash));
        
        return result;
    }

    /**
     * Kiểm tra một tài khoản cụ thể
     * GET /api/dev/test-account?username=admin&password=admin123
     */
    @GetMapping("/test-account")
    public Map<String, Object> testAccount(
            @RequestParam String username,
            @RequestParam String password
    ) {
        TaiKhoan account = taiKhoanRepository.findByTenDangNhap(username).orElse(null);
        
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("password", password);
        
        if (account == null) {
            result.put("found", false);
            result.put("message", "Tài khoản không tồn tại");
        } else {
            result.put("found", true);
            result.put("hashInDB", account.getMatKhauHash());
            result.put("hashLength", account.getMatKhauHash().length());
            result.put("status", account.getTrangThai());
            
            boolean matches = passwordEncoder.matches(password, account.getMatKhauHash());
            result.put("passwordMatches", matches);
            
            if (!matches) {
                // Generate hash mới và so sánh
                String newHash = passwordEncoder.encode(password);
                result.put("newHashGenerated", newHash);
                result.put("newHashMatches", passwordEncoder.matches(password, newHash));
            }
        }
        
        return result;
    }
}
