package com.nnice.karaoke.service;

import com.nnice.karaoke.entity.LoaiPhong;
import com.nnice.karaoke.repository.LoaiPhongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoaiPhongService {
    
    @Autowired
    private LoaiPhongRepository loaiPhongRepository;
    
    /**
     * Lấy tất cả loại phòng
     */
    public List<LoaiPhong> getAllLoaiPhong() {
        System.out.println("LoaiPhongService: Fetching all room types...");
        List<LoaiPhong> loaiPhongs = loaiPhongRepository.findAll();
        System.out.println("LoaiPhongService: Found " + loaiPhongs.size() + " room types");
        return loaiPhongs;
    }
    
    /**
     * Lấy loại phòng theo ID
     */
    public Optional<LoaiPhong> getLoaiPhongById(Integer maLoai) {
        System.out.println("LoaiPhongService: Fetching room type with ID: " + maLoai);
        return loaiPhongRepository.findById(maLoai);
    }
    
    /**
     * Tạo loại phòng mới
     */
    public LoaiPhong createLoaiPhong(LoaiPhong loaiPhong) {
        System.out.println("LoaiPhongService: Creating new room type: " + loaiPhong.getTenLoai());
        return loaiPhongRepository.save(loaiPhong);
    }
    
    /**
     * Cập nhật loại phòng
     */
    public LoaiPhong updateLoaiPhong(Integer maLoai, LoaiPhong loaiPhongDetails) {
        System.out.println("LoaiPhongService: Updating room type with ID: " + maLoai);
        Optional<LoaiPhong> loaiPhong = loaiPhongRepository.findById(maLoai);
        
        if (loaiPhong.isPresent()) {
            LoaiPhong existingLoaiPhong = loaiPhong.get();
            
            if (loaiPhongDetails.getTenLoai() != null) {
                existingLoaiPhong.setTenLoai(loaiPhongDetails.getTenLoai());
            }
            if (loaiPhongDetails.getSucChua() != null) {
                existingLoaiPhong.setSucChua(loaiPhongDetails.getSucChua());
            }
            if (loaiPhongDetails.getGiaTheoGio() != null) {
                existingLoaiPhong.setGiaTheoGio(loaiPhongDetails.getGiaTheoGio());
            }
            if (loaiPhongDetails.getMoTa() != null) {
                existingLoaiPhong.setMoTa(loaiPhongDetails.getMoTa());
            }
            
            return loaiPhongRepository.save(existingLoaiPhong);
        }
        
        return null;
    }
    
    /**
     * Xóa loại phòng
     */
    public void deleteLoaiPhong(Integer maLoai) {
        System.out.println("LoaiPhongService: Deleting room type with ID: " + maLoai);
        loaiPhongRepository.deleteById(maLoai);
    }
}
