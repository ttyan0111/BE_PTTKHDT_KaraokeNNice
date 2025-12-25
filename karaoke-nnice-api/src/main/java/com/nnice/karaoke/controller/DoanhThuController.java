package com.nnice.karaoke.controller;

import com.nnice.karaoke.service.DoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doanh-thu")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DoanhThuController {
    
    @Autowired
    private DoanhThuService doanhThuService;
    
    /**
     * GET /v1/doanh-thu/theo-thang?year=2025 - Lấy doanh thu theo tháng
     */
    @GetMapping("/theo-thang")
    public ResponseEntity<Map<String, Object>> getMonthlyRevenue(@RequestParam(defaultValue = "2025") int year) {
        try {
            Map<String, Object> data = doanhThuService.getRevenueByMonth(year);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * GET /v1/doanh-thu/theo-nam - Lấy doanh thu theo năm
     */
    @GetMapping("/theo-nam")
    public ResponseEntity<Map<String, Object>> getYearlyRevenue() {
        try {
            Map<String, Object> data = doanhThuService.getRevenueByYear();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * GET /v1/doanh-thu/hom-nay - Lấy doanh thu hôm nay
     */
    @GetMapping("/hom-nay")
    public ResponseEntity<Map<String, Object>> getTodayRevenue() {
        try {
            BigDecimal revenue = doanhThuService.getTodayRevenue();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", revenue);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * GET /v1/doanh-thu/thang-nay - Lấy doanh thu tháng hiện tại
     */
    @GetMapping("/thang-nay")
    public ResponseEntity<Map<String, Object>> getCurrentMonthRevenue() {
        try {
            BigDecimal revenue = doanhThuService.getCurrentMonthRevenue();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", revenue);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * GET /v1/doanh-thu/nam-nay - Lấy doanh thu năm hiện tại
     */
    @GetMapping("/nam-nay")
    public ResponseEntity<Map<String, Object>> getCurrentYearRevenue() {
        try {
            BigDecimal revenue = doanhThuService.getCurrentYearRevenue();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", revenue);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Helper method để tạo error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
}
