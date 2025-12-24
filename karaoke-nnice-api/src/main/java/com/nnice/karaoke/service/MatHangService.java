package com.nnice.karaoke.service;

import com.nnice.karaoke.entity.MatHang;
import com.nnice.karaoke.repository.MatHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MatHangService {
    
    @Autowired
    private MatHangRepository matHangRepository;
    
    /**
     * Lấy tất cả mặt hàng
     */
    public List<MatHang> getAllMatHang() {
        System.out.println("MatHangService: Fetching all items...");
        List<MatHang> matHangs = matHangRepository.findAll();
        System.out.println("MatHangService: Found " + matHangs.size() + " items");
        return matHangs;
    }
    
    /**
     * Lấy mặt hàng theo loại
     */
    public List<MatHang> getMatHangByLoai(String loaiHang) {
        System.out.println("MatHangService: Fetching items by type: " + loaiHang);
        List<MatHang> matHangs = matHangRepository.findByLoaiHang(loaiHang);
        System.out.println("MatHangService: Found " + matHangs.size() + " items for type: " + loaiHang);
        return matHangs;
    }
    
    /**
     * Lấy mặt hàng theo ID
     */
    public Optional<MatHang> getMatHangById(Integer maHang) {
        System.out.println("MatHangService: Fetching item with ID: " + maHang);
        return matHangRepository.findById(maHang);
    }
    
    /**
     * Tạo mặt hàng mới
     */
    public MatHang createMatHang(MatHang matHang) {
        System.out.println("MatHangService: Creating new item: " + matHang.getTenHang());
        return matHangRepository.save(matHang);
    }
    
    /**
     * Cập nhật mặt hàng
     */
    public MatHang updateMatHang(MatHang matHang) {
        System.out.println("MatHangService: Updating item: " + matHang.getTenHang());
        if (matHangRepository.existsById(matHang.getMaHang())) {
            return matHangRepository.save(matHang);
        } else {
            throw new RuntimeException("Item not found: " + matHang.getMaHang());
        }
    }
    
    /**
     * Xóa mặt hàng
     */
    public void deleteMatHang(Integer maHang) {
        System.out.println("MatHangService: Deleting item with ID: " + maHang);
        if (matHangRepository.existsById(maHang)) {
            matHangRepository.deleteById(maHang);
        } else {
            throw new RuntimeException("Item not found: " + maHang);
        }
    }
}
