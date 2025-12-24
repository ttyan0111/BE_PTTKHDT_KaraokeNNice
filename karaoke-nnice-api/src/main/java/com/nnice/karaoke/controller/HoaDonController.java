package com.nnice.karaoke.controller;

import com.nnice.karaoke.dto.request.HoaDonRequest;
import com.nnice.karaoke.dto.response.HoaDonResponse;
import com.nnice.karaoke.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hoa-don")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HoaDonController {
    
    private final HoaDonService hoaDonService;
    
    /**
     * Lấy tất cả hóa đơn
     */
    @GetMapping("/tat-ca")
    public ResponseEntity<List<HoaDonResponse>> getAll() {
        try {
            List<HoaDonResponse> hoaDons = hoaDonService.getAll();
            return ResponseEntity.ok(hoaDons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy hóa đơn theo ID
     */
    @GetMapping("/{maHD}")
    public ResponseEntity<HoaDonResponse> getById(@PathVariable Integer maHD) {
        try {
            HoaDonResponse hoaDon = hoaDonService.getById(maHD);
            if (hoaDon == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lấy hóa đơn theo trạng thái
     */
    @GetMapping("/theo-trang-thai")
    public ResponseEntity<List<HoaDonResponse>> getByTrangThai(@RequestParam String trangThai) {
        try {
            List<HoaDonResponse> hoaDons = hoaDonService.getByTrangThai(trangThai);
            return ResponseEntity.ok(hoaDons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Tạo mới hóa đơn
     */
    @PostMapping("/tao")
    public ResponseEntity<HoaDonResponse> create(@RequestBody HoaDonRequest request) {
        try {
            HoaDonResponse createdHoaDon = hoaDonService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Cập nhật hóa đơn
     */
    @PutMapping("/cap-nhat")
    public ResponseEntity<HoaDonResponse> update(@RequestBody HoaDonRequest request) {
        try {
            HoaDonResponse updatedHoaDon = hoaDonService.update(request);
            if (updatedHoaDon == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Xóa hóa đơn
     */
    @DeleteMapping("/{maHD}")
    public ResponseEntity<Void> delete(@PathVariable Integer maHD) {
        try {
            boolean deleted = hoaDonService.delete(maHD);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
