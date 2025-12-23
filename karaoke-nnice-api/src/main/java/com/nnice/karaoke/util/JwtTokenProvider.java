package com.nnice.karaoke.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:MyKaraokeSecretKeyForJWTTokenGenerationAndValidation2024NNiceSystemLongKeyWith64CharsMinimum}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long jwtExpiration;

    /**
     * Tạo JWT Token
     */
    public String generateToken(Integer maTaiKhoan, String tenDangNhap, String loaiTaiKhoan) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("maTaiKhoan", maTaiKhoan);
        claims.put("loaiTaiKhoan", loaiTaiKhoan);
        return createToken(claims, tenDangNhap);
    }

    /**
     * Tạo JWT Token với thêm thông tin
     */
    public String generateToken(Integer maTaiKhoan, String tenDangNhap, String loaiTaiKhoan, 
                                Integer maKhachHang, String hoTen) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("maTaiKhoan", maTaiKhoan);
        claims.put("loaiTaiKhoan", loaiTaiKhoan);
        claims.put("maKhachHang", maKhachHang);
        claims.put("hoTen", hoTen);
        return createToken(claims, tenDangNhap);
    }

    /**
     * Tạo JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Lấy tên đăng nhập từ token (simple decode)
     */
    public String getTenDangNhapFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length > 1) {
                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                // payload là JSON, lấy "sub" field
                if (payload.contains("\"sub\"")) {
                    int start = payload.indexOf("\"sub\":\"") + 7;
                    int end = payload.indexOf("\"", start);
                    return payload.substring(start, end);
                }
            }
        } catch (Exception e) {
            // Silent fail
        }
        return null;
    }

    /**
     * Lấy maTaiKhoan từ token (simple decode)
     */
    public Integer getMaTaiKhoanFromToken(String token) {
        return (Integer) getClaimFromToken(token, "maTaiKhoan");
    }

    /**
     * Lấy loaiTaiKhoan từ token (simple decode)
     */
    public String getLoaiTaiKhoanFromToken(String token) {
        return (String) getClaimFromToken(token, "loaiTaiKhoan");
    }

    /**
     * Lấy maKhachHang từ token (simple decode)
     */
    public Integer getMaKhachHangFromToken(String token) {
        Object value = getClaimFromToken(token, "maKhachHang");
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }

    /**
     * Kiểm tra token có hợp lệ không (Simple - chỉ check format)
     */
    public boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }
            // JWT có 3 phần: header.payload.signature
            String[] parts = token.split("\\.");
            return parts.length == 3;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lấy claim từ token (simple JSON decode)
     */
    private Object getClaimFromToken(String token, String claimName) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length > 1) {
                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                // Simple JSON parsing - tìm "claimName": value
                String searchKey = "\"" + claimName + "\":";
                int startIndex = payload.indexOf(searchKey);
                if (startIndex != -1) {
                    int valueStart = startIndex + searchKey.length();
                    // Skip whitespace
                    while (valueStart < payload.length() && Character.isWhitespace(payload.charAt(valueStart))) {
                        valueStart++;
                    }
                    
                    char firstChar = payload.charAt(valueStart);
                    if (firstChar == '"') {
                        // String value
                        int endIndex = payload.indexOf('"', valueStart + 1);
                        return payload.substring(valueStart + 1, endIndex);
                    } else if (firstChar == '{' || firstChar == '[') {
                        // Object/Array - return as is
                        return payload.substring(valueStart);
                    } else {
                        // Number
                        int endIndex = valueStart;
                        while (endIndex < payload.length() && 
                               (Character.isDigit(payload.charAt(endIndex)) || payload.charAt(endIndex) == '.')) {
                            endIndex++;
                        }
                        String numberStr = payload.substring(valueStart, endIndex);
                        try {
                            if (numberStr.contains(".")) {
                                return Double.parseDouble(numberStr);
                            } else {
                                return Integer.parseInt(numberStr);
                            }
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Silent fail
        }
        return null;
    }
}
