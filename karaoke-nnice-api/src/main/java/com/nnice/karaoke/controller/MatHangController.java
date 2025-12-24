package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.MatHang;
import com.nnice.karaoke.service.MatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mat-hang")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MatHangController {
    
    @Autowired
    private MatHangService matHangService;
    
    /**
     * GET /v1/mat-hang/tat-ca - Lấy tất cả mặt hàng
     */
    @GetMapping("/tat-ca")
    public ResponseEntity<List<MatHang>> getAllMatHang() {
        System.out.println("Controller: GET /v1/mat-hang/tat-ca");
        try {
            List<MatHang> matHangs = matHangService.getAllMatHang();
            System.out.println("Controller: Returning " + matHangs.size() + " items");
            return ResponseEntity.ok(matHangs);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /v1/mat-hang/theo-loai?loaiHang=Đồ Ăn - Lấy mặt hàng theo loại
     */
    @GetMapping("/theo-loai")
    public ResponseEntity<List<MatHang>> getMatHangByLoai(@RequestParam String loaiHang) {
        System.out.println("Controller: GET /v1/mat-hang/theo-loai?loaiHang=" + loaiHang);
        try {
            List<MatHang> matHangs = matHangService.getMatHangByLoai(loaiHang);
            return ResponseEntity.ok(matHangs);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /v1/mat-hang/{maHang} - Lấy mặt hàng theo ID
     */
    @GetMapping("/{maHang}")
    public ResponseEntity<MatHang> getMatHangById(@PathVariable Integer maHang) {
        System.out.println("Controller: GET /v1/mat-hang/" + maHang);
        Optional<MatHang> matHang = matHangService.getMatHangById(maHang);
        
        if (matHang.isPresent()) {
            return ResponseEntity.ok(matHang.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * POST /v1/mat-hang/tao - Tạo mặt hàng mới
     */
    @PostMapping("/tao")
    public ResponseEntity<MatHang> createMatHang(@RequestBody MatHang matHang) {
        System.out.println("Controller: POST /v1/mat-hang/tao - Creating: " + matHang.getTenHang());
        try {
            MatHang created = matHangService.createMatHang(matHang);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /v1/mat-hang/cap-nhat - Cập nhật mặt hàng
     */
    @PutMapping("/cap-nhat")
    public ResponseEntity<MatHang> updateMatHang(@RequestBody MatHang matHang) {
        System.out.println("Controller: PUT /v1/mat-hang/cap-nhat - Updating: " + matHang.getTenHang());
        try {
            MatHang updated = matHangService.updateMatHang(matHang);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /v1/mat-hang/{maHang} - Xóa mặt hàng
     */
    @DeleteMapping("/{maHang}")
    public ResponseEntity<Void> deleteMatHang(@PathVariable Integer maHang) {
        System.out.println("Controller: DELETE /v1/mat-hang/" + maHang);
        try {
            matHangService.deleteMatHang(maHang);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Controller Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
