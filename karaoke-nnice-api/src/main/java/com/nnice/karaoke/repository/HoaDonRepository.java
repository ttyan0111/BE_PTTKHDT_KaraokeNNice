package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    /**
     * Tìm tất cả hóa đơn theo trạng thái
     */
    List<HoaDon> findByTrangThai(String trangThai);
}
