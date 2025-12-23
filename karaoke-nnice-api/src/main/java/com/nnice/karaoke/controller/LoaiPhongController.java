package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.LoaiPhong;
import com.nnice.karaoke.service.LoaiPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loai-phong")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoaiPhongController {
    
    @Autowired
    private LoaiPhongService loaiPhongService;
    
    /**
     * GET /v1/loai-phong/tat-ca - Lấy tất cả loại phòng
     */
    @GetMapping("/tat-ca")
    public ResponseEntity<List<LoaiPhong>> getAllLoaiPhong() {
        System.out.println("Controller: GET /v1/loai-phong/tat-ca");
        try {
            List<LoaiPhong> loaiPhongs = loaiPhongService.getAllLoaiPhong();
            System.out.println("Controller: Returning " + loaiPhongs.size() + " room types");
            return ResponseEntity.ok(loaiPhongs);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /v1/loai-phong/{maLoai} - Lấy loại phòng theo ID
     */
    @GetMapping("/{maLoai}")
    public ResponseEntity<LoaiPhong> getLoaiPhongById(@PathVariable Integer maLoai) {
        System.out.println("Controller: GET /v1/loai-phong/" + maLoai);
        Optional<LoaiPhong> loaiPhong = loaiPhongService.getLoaiPhongById(maLoai);
        
        if (loaiPhong.isPresent()) {
            return ResponseEntity.ok(loaiPhong.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * POST /v1/loai-phong - Tạo loại phòng mới
     */
    @PostMapping
    public ResponseEntity<LoaiPhong> createLoaiPhong(@RequestBody LoaiPhong loaiPhong) {
        System.out.println("Controller: POST /v1/loai-phong");
        System.out.println("Request body: " + loaiPhong.getTenLoai() + ", " + loaiPhong.getSucChua() + ", " + loaiPhong.getGiaTheoGio());
        
        try {
            LoaiPhong createdLoaiPhong = loaiPhongService.createLoaiPhong(loaiPhong);
            System.out.println("Controller: Created room type with ID: " + createdLoaiPhong.getMaLoai());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoaiPhong);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /v1/loai-phong/{maLoai} - Cập nhật loại phòng
     */
    @PutMapping("/{maLoai}")
    public ResponseEntity<LoaiPhong> updateLoaiPhong(
            @PathVariable Integer maLoai,
            @RequestBody LoaiPhong loaiPhongDetails) {
        System.out.println("Controller: PUT /v1/loai-phong/" + maLoai);
        System.out.println("Request body: " + loaiPhongDetails.getTenLoai() + ", " + loaiPhongDetails.getSucChua() + ", " + loaiPhongDetails.getGiaTheoGio());
        
        try {
            LoaiPhong updatedLoaiPhong = loaiPhongService.updateLoaiPhong(maLoai, loaiPhongDetails);
            
            if (updatedLoaiPhong != null) {
                System.out.println("Controller: Updated room type with ID: " + maLoai);
                return ResponseEntity.ok(updatedLoaiPhong);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /v1/loai-phong/{maLoai} - Xóa loại phòng
     */
    @DeleteMapping("/{maLoai}")
    public ResponseEntity<Void> deleteLoaiPhong(@PathVariable Integer maLoai) {
        System.out.println("Controller: DELETE /v1/loai-phong/" + maLoai);
        
        try {
            Optional<LoaiPhong> loaiPhong = loaiPhongService.getLoaiPhongById(maLoai);
            
            if (loaiPhong.isPresent()) {
                loaiPhongService.deleteLoaiPhong(maLoai);
                System.out.println("Controller: Deleted room type with ID: " + maLoai);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
