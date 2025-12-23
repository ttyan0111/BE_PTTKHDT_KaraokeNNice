package com.nnice.karaoke.service;

import com.nnice.karaoke.entity.Phong;
import com.nnice.karaoke.repository.PhongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PhongService {
    
    @Autowired
    private PhongRepository phongRepository;
    
    /**
     * Lấy tất cả phòng
     */
    public List<Phong> getAllPhong() {
        System.out.println("PhongService: Fetching all rooms...");
        List<Phong> phongs = phongRepository.findAll();
        System.out.println("PhongService: Found " + phongs.size() + " rooms");
        return phongs;
    }
    
    /**
     * Lấy phòng theo ID
     */
    public Optional<Phong> getPhongById(Integer maPhong) {
        System.out.println("PhongService: Fetching room with ID: " + maPhong);
        return phongRepository.findById(maPhong);
    }
    
    /**
     * Tạo phòng mới
     */
    public Phong createPhong(Phong phong) {
        System.out.println("PhongService: Creating new room: " + phong.getTenPhong());
        return phongRepository.save(phong);
    }
    
    /**
     * Cập nhật phòng
     */
    public Phong updatePhong(Integer maPhong, Phong phongDetails) {
        System.out.println("PhongService: Updating room with ID: " + maPhong);
        Optional<Phong> phong = phongRepository.findById(maPhong);
        
        if (phong.isPresent()) {
            Phong existingPhong = phong.get();
            
            if (phongDetails.getTenPhong() != null) {
                existingPhong.setTenPhong(phongDetails.getTenPhong());
            }
            if (phongDetails.getTrangThai() != null) {
                existingPhong.setTrangThai(phongDetails.getTrangThai());
            }
            if (phongDetails.getMaLoai() != null) {
                existingPhong.setMaLoai(phongDetails.getMaLoai());
            }
            if (phongDetails.getMaCS() != null) {
                existingPhong.setMaCS(phongDetails.getMaCS());
            }
            
            return phongRepository.save(existingPhong);
        }
        
        return null;
    }
    
    /**
     * Xóa phòng
     */
    public void deletePhong(Integer maPhong) {
        System.out.println("PhongService: Deleting room with ID: " + maPhong);
        phongRepository.deleteById(maPhong);
    }
}
