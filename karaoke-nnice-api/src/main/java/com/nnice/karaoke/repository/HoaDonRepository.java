package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    /**
     * Tìm tất cả hóa đơn theo trạng thái
     */
    List<HoaDon> findByTrangThai(String trangThai);
    
    /**
     * Lấy doanh thu theo tháng trong năm
     */
    @Query("SELECT MONTH(h.ngayLap) as month, SUM(h.tongTien) as revenue " +
           "FROM HoaDon h " +
           "WHERE YEAR(h.ngayLap) = :year AND h.trangThai = 'Hoai thanh toan' " +
           "GROUP BY MONTH(h.ngayLap) " +
           "ORDER BY MONTH(h.ngayLap)")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);
    
    /**
     * Lấy doanh thu theo năm
     */
    @Query("SELECT YEAR(h.ngayLap) as year, SUM(h.tongTien) as revenue " +
           "FROM HoaDon h " +
           "WHERE h.trangThai = 'Hoai thanh toan' " +
           "GROUP BY YEAR(h.ngayLap) " +
           "ORDER BY YEAR(h.ngayLap) DESC")
    List<Object[]> getYearlyRevenue();
    
    /**
     * Lấy doanh thu hôm nay
     */
    @Query("SELECT SUM(h.tongTien) FROM HoaDon h " +
           "WHERE DATE(h.ngayLap) = CURDATE() AND h.trangThai = 'Hoai thanh toan'")
    BigDecimal getTodayRevenue();
    
    /**
     * Lấy doanh thu tháng hiện tại
     */
    @Query("SELECT SUM(h.tongTien) FROM HoaDon h " +
           "WHERE MONTH(h.ngayLap) = MONTH(CURDATE()) " +
           "AND YEAR(h.ngayLap) = YEAR(CURDATE()) " +
           "AND h.trangThai = 'Hoai thanh toan'")
    BigDecimal getCurrentMonthRevenue();
    
    /**
     * Lấy doanh thu năm hiện tại
     */
    @Query("SELECT SUM(h.tongTien) FROM HoaDon h " +
           "WHERE YEAR(h.ngayLap) = YEAR(CURDATE()) " +
           "AND h.trangThai = 'Hoai thanh toan'")
    BigDecimal getCurrentYearRevenue();
}

