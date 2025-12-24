package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
    
    /**
     * Lấy danh sách thanh toán của một đơn đặt tiệc
     */
    List<ThanhToan> findByDonDatTiec_MaDonDatTiec(Integer maDonDatTiec);
    
    /**
     * Kiểm tra đã thanh toán cọc chưa
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
           "FROM ThanhToan t " +
           "WHERE t.donDatTiec.maDonDatTiec = :maDonDatTiec " +
           "AND t.loaiThanhToan = 'DAT_COC' " +
           "AND t.trangThai = 'THANH_CONG'")
    boolean isDaThanhToanCoc(@Param("maDonDatTiec") Integer maDonDatTiec);
}
