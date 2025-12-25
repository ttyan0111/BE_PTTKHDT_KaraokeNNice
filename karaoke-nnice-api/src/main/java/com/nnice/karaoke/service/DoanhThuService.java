package com.nnice.karaoke.service;

import com.nnice.karaoke.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DoanhThuService {
    
    @Autowired
    private HoaDonRepository hoaDonRepository;
    
    /**
     * Lấy doanh thu theo tháng của năm hiện tại
     */
    public Map<String, Object> getRevenueByMonth(int year) {
        List<Object[]> results = hoaDonRepository.getMonthlyRevenue(year);
        Map<Integer, BigDecimal> monthlyData = new TreeMap<>();
        
        // Khởi tạo tất cả các tháng với giá trị 0
        for (int i = 1; i <= 12; i++) {
            monthlyData.put(i, BigDecimal.ZERO);
        }
        
        // Cập nhật dữ liệu từ query
        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue();
            BigDecimal revenue = row[1] != null ? new BigDecimal(row[1].toString()) : BigDecimal.ZERO;
            monthlyData.put(month, revenue);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("year", year);
        response.put("data", monthlyData);
        
        // Tính tổng doanh thu năm
        BigDecimal totalYear = monthlyData.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.put("total", totalYear);
        
        return response;
    }
    
    /**
     * Lấy doanh thu theo năm (5 năm gần nhất)
     */
    public Map<String, Object> getRevenueByYear() {
        List<Object[]> results = hoaDonRepository.getYearlyRevenue();
        Map<Integer, BigDecimal> yearlyData = new TreeMap<>();
        
        for (Object[] row : results) {
            Integer year = ((Number) row[0]).intValue();
            BigDecimal revenue = row[1] != null ? new BigDecimal(row[1].toString()) : BigDecimal.ZERO;
            yearlyData.put(year, revenue);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("data", yearlyData);
        
        // Tính tổng doanh thu toàn bộ
        BigDecimal totalAll = yearlyData.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.put("total", totalAll);
        
        return response;
    }
    
    /**
     * Lấy doanh thu hôm nay
     */
    public BigDecimal getTodayRevenue() {
        BigDecimal revenue = hoaDonRepository.getTodayRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
    
    /**
     * Lấy doanh thu tháng hiện tại
     */
    public BigDecimal getCurrentMonthRevenue() {
        BigDecimal revenue = hoaDonRepository.getCurrentMonthRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
    
    /**
     * Lấy doanh thu năm hiện tại
     */
    public BigDecimal getCurrentYearRevenue() {
        BigDecimal revenue = hoaDonRepository.getCurrentYearRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
}
