package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.Phong;
import com.nnice.karaoke.service.PhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/phong")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhongController {
    
    @Autowired
    private PhongService phongService;
    
    /**
     * GET /api/v1/phong/tat-ca - Lấy tất cả phòng
     */
    @GetMapping("/tat-ca")
    public ResponseEntity<List<Phong>> getAllPhong() {
        System.out.println("Controller: GET /api/v1/phong/tat-ca");
        try {
            List<Phong> phongs = phongService.getAllPhong();
            System.out.println("Controller: Returning " + phongs.size() + " rooms");
            return ResponseEntity.ok(phongs);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/v1/phong/{maPhong} - Lấy phòng theo ID
     */
    @GetMapping("/{maPhong}")
    public ResponseEntity<Phong> getPhongById(@PathVariable Integer maPhong) {
        System.out.println("Controller: GET /api/v1/phong/" + maPhong);
        Optional<Phong> phong = phongService.getPhongById(maPhong);
        
        if (phong.isPresent()) {
            return ResponseEntity.ok(phong.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * POST /api/v1/phong - Tạo phòng mới
     */
    @PostMapping
    public ResponseEntity<Phong> createPhong(@RequestBody Phong phong) {
        System.out.println("Controller: POST /api/v1/phong");
        System.out.println("Request body: " + phong.getTenPhong() + ", " + phong.getMaLoai() + ", " + phong.getTrangThai());
        
        try {
            Phong createdPhong = phongService.createPhong(phong);
            System.out.println("Controller: Created room with ID: " + createdPhong.getMaPhong());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPhong);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /api/v1/phong/{maPhong} - Cập nhật phòng
     */
    @PutMapping("/{maPhong}")
    public ResponseEntity<Phong> updatePhong(
            @PathVariable Integer maPhong,
            @RequestBody Phong phongDetails) {
        System.out.println("Controller: PUT /api/v1/phong/" + maPhong);
        System.out.println("Request body: " + phongDetails.getTenPhong() + ", " + phongDetails.getMaLoai() + ", " + phongDetails.getTrangThai());
        
        try {
            Phong updatedPhong = phongService.updatePhong(maPhong, phongDetails);
            
            if (updatedPhong != null) {
                System.out.println("Controller: Updated room with ID: " + maPhong);
                return ResponseEntity.ok(updatedPhong);
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
     * DELETE /api/v1/phong/{maPhong} - Xóa phòng
     */
    @DeleteMapping("/{maPhong}")
    public ResponseEntity<Void> deletePhong(@PathVariable Integer maPhong) {
        System.out.println("Controller: DELETE /api/v1/phong/" + maPhong);
        
        try {
            Optional<Phong> phong = phongService.getPhongById(maPhong);
            
            if (phong.isPresent()) {
                phongService.deletePhong(maPhong);
                System.out.println("Controller: Deleted room with ID: " + maPhong);
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
