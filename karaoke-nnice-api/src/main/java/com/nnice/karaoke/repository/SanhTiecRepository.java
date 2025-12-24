package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.SanhTiec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SanhTiecRepository extends JpaRepository<SanhTiec, Integer> {
    
    /**
     * Tìm các sảnh trống trong khoảng thời gian
     */
    @Query("SELECT s FROM SanhTiec s WHERE s.trangThai = 'TRONG' " +
           "AND s.maSanh NOT IN (" +
           "SELECT d.sanhTiec.maSanh FROM DonDatTiec d " +
           "WHERE d.trangThai != 'HUY' " +
           "AND d.ngayToChuc BETWEEN :tuNgay AND :denNgay)")
    List<SanhTiec> findSanhTrong(@Param("tuNgay") LocalDateTime tuNgay, 
                                   @Param("denNgay") LocalDateTime denNgay);
    
    /**
     * Tìm sảnh theo sức chứa tối thiểu
     */
    List<SanhTiec> findBySucChuaGreaterThanEqualAndTrangThai(Integer sucChua, String trangThai);
}
