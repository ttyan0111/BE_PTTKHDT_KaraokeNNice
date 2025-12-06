package com.nnice.karaoke.controller;

import com.nnice.karaoke.service.QuanLyNhapKhoService;
import com.nnice.karaoke.entity.PhieuNhap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/nhap-kho")
@Tag(name = "Quản Lý Nhập Kho", description = "API quản lý nhập kho hàng hóa")
public class QuanLyNhapKhoController {

    @Autowired
    private QuanLyNhapKhoService quanLyNhapKhoService;

    @PostMapping
    @Operation(summary = "Tạo phiếu nhập mới")
    public ResponseEntity<PhieuNhap> taoPhieuNhap(@RequestBody PhieuNhap phieuNhap) {
        PhieuNhap created = quanLyNhapKhoService.taoPhieuNhap(phieuNhap);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{maPhieuNhap}")
    @Operation(summary = "Xem chi tiết phiếu nhập")
    public ResponseEntity<?> xemChiTiet(@PathVariable Integer maPhieuNhap) {
        try {
            Optional<PhieuNhap> phieu = quanLyNhapKhoService.xemChiTiet(maPhieuNhap);
            return phieu.map(ResponseEntity::ok)
                    .orElseThrow(() -> new com.nnice.karaoke.exception.ResourceNotFoundException("Phiếu nhập không tìm thấy"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi: " + e.getMessage());
        }
    }

    @PutMapping("/{maPhieuNhap}")
    @Operation(summary = "Cập nhật phiếu nhập")
    public ResponseEntity<PhieuNhap> capNhatPhieuNhap(
            @PathVariable Integer maPhieuNhap,
            @RequestBody PhieuNhap phieuNhap) {
        phieuNhap.setMaPhieuNhap(maPhieuNhap);
        PhieuNhap updated = quanLyNhapKhoService.capNhatPhieuNhap(phieuNhap);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    @Operation(summary = "Danh sách phiếu nhập")
    public ResponseEntity<List<PhieuNhap>> danhSachPhieuNhap() {
        List<PhieuNhap> list = quanLyNhapKhoService.danhSachPhieuNhap();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/cap-nhat-ton-kho/{maMatHang}")
    @Operation(summary = "Cập nhật tồn kho")
    public ResponseEntity<Void> capNhatTonKho(
            @PathVariable Integer maMatHang,
            @RequestParam int soLuong) {
        quanLyNhapKhoService.capNhatTonKho(maMatHang, soLuong);
        return ResponseEntity.ok().build();
    }
}
