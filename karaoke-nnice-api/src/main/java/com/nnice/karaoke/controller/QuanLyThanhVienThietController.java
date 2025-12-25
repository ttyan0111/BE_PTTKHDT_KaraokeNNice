package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.TheThanhVien;
import com.nnice.karaoke.exception.ResourceNotFoundException;
import com.nnice.karaoke.service.QuanLyThanhVienThietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/thanh-vien")
@Tag(name = "Quản Lý Thành Viên", description = "API quản lý thành viên thiết")
public class QuanLyThanhVienThietController {

    @Autowired
    private QuanLyThanhVienThietService quanLyThanhVienThietService;

    @GetMapping
    @Operation(summary = "Lấy tất cả thành viên")
    public ResponseEntity<List<TheThanhVien>> getAllThanhVien() {
        List<TheThanhVien> members = quanLyThanhVienThietService.danhSach();
        return ResponseEntity.ok(members);
    }

    @PostMapping
    @Operation(summary = "Đăng ký thành viên mới")
    public ResponseEntity<String> dangKyThanhVienMoi() {
        String maThanhVien = quanLyThanhVienThietService.taoMaThanhVien();
        return new ResponseEntity<>(maThanhVien, HttpStatus.CREATED);
    }

    @GetMapping("/ma-thanh-vien")
    @Operation(summary = "Tạo mã thành viên duy nhất")
    public ResponseEntity<String> taoMaThanhVien() {
        String ma = quanLyThanhVienThietService.taoMaThanhVien();
        return ResponseEntity.ok(ma);
    }

    @GetMapping("/ma/{maThanhVien}")
    @Operation(summary = "Tra cứu thông tin thành viên")
    public ResponseEntity<TheThanhVien> traCuuThongTin(@PathVariable Integer maThanhVien) {
        Optional<TheThanhVien> member = quanLyThanhVienThietService.traCuuThongTin(maThanhVien);
        return member.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Thành viên không tìm thấy"));
    }

    @GetMapping("/sdt/{sdt}")
    @Operation(summary = "Tra cứu theo số điện thoại")
    public ResponseEntity<TheThanhVien> traCuuTheoSDT(@PathVariable String sdt) {
        Optional<TheThanhVien> member = quanLyThanhVienThietService.traCuuTheoSDT(sdt);
        return member.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Thành viên không tìm thấy"));
    }

    @PutMapping("/{maThanhVien}")
    @Operation(summary = "Cập nhật thông tin thành viên")
    public ResponseEntity<TheThanhVien> capNhatThongTin(
            @PathVariable Integer maThanhVien,
            @RequestBody TheThanhVien theThanhVien) {
        theThanhVien.setMaThe(maThanhVien);
        TheThanhVien updated = quanLyThanhVienThietService.capNhatThongTin(theThanhVien);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{maThanhVien}/lich-su")
    @Operation(summary = "Xem lịch sử (điểm, hạng, ghi chú)")
    public ResponseEntity<Void> xemLichSu(@PathVariable Integer maThanhVien) {
        quanLyThanhVienThietService.xemLichSu(maThanhVien);
        return ResponseEntity.ok().build();
    }
}
