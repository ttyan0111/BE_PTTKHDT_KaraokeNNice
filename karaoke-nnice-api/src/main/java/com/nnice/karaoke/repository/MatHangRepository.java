package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.MatHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatHangRepository extends JpaRepository<MatHang, Integer> {
    List<MatHang> findByLoaiHang(String loaiHang);
}
