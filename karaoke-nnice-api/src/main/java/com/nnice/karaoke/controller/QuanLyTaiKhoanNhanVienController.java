package com.nnice.karaoke.controller;

import com.nnice.karaoke.entity.NhanVien;
import com.nnice.karaoke.service.QuanLyTaiKhoanNhanVienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/quan-ly-tai-khoan-nhan-vien")
@Tag(name = "Quản Lý Tài Khoản Nhân Viên", description = "API quản lý tài khoản nhân viên")
public class QuanLyTaiKhoanNhanVienController {

    @Autowired
    private QuanLyTaiKhoanNhanVienService quanLyTaiKhoanNhanVienService;

    @PostMapping("/tao-tai-khoan")
    @Operation(summary = "Tạo tài khoản nhân viên mới")
    public ResponseEntity<NhanVien> taoTaiKhoan(
            @RequestBody NhanVien nhanVien,
            @RequestParam String username,
            @RequestParam String password) {
        NhanVien result = quanLyTaiKhoanNhanVienService.taoTaiKhoan(nhanVien, username, password);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/xem-thong-tin/{maNhanVien}")
    @Operation(summary = "Xem thông tin tài khoản")
    public ResponseEntity<NhanVien> xemThongTin(@PathVariable Integer maNhanVien) {
        Optional<NhanVien> result = quanLyTaiKhoanNhanVienService.xemThongTin(maNhanVien);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/cap-nhat")
    @Operation(summary = "Cập nhật thông tin tài khoản")
    public ResponseEntity<NhanVien> capNhatThongTin(@RequestBody NhanVien nhanVien) {
        NhanVien result = quanLyTaiKhoanNhanVienService.capNhatThongTin(nhanVien);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/vo-hieu-hoa/{maNhanVien}")
    @Operation(summary = "Xóa/vô hiệu hóa tài khoản")
    public ResponseEntity<Void> voHieuHoaTaiKhoan(@PathVariable Integer maNhanVien) {
        quanLyTaiKhoanNhanVienService.voHieuHoaTaiKhoan(maNhanVien);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/thiet-lap-quyen/{maNhanVien}")
    @Operation(summary = "Thiết lập quyền truy cập theo vai trò")
    public ResponseEntity<Void> thietLapQuyenTruCap(
            @PathVariable Integer maNhanVien,
            @RequestParam String vaiTro) {
        quanLyTaiKhoanNhanVienService.thietLapQuyenTruCap(maNhanVien, vaiTro);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dat-lai-mat-khau/{maNhanVien}")
    @Operation(summary = "Đặt lại mật khẩu")
    public ResponseEntity<Void> datLaiMatKhau(
            @PathVariable Integer maNhanVien,
            @RequestParam(required = false) String matKhauMacDinh) {
        quanLyTaiKhoanNhanVienService.datLaiMatKhau(maNhanVien, matKhauMacDinh);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/danh-sach")
    @Operation(summary = "Danh sách tài khoản")
    public ResponseEntity<List<NhanVien>> danhSachTaiKhoan() {
        List<NhanVien> result = quanLyTaiKhoanNhanVienService.danhSachTaiKhoan();
        System.out.println("=== DANH SÁCH NHÂN VIÊN ===");
        System.out.println("Total employees: " + result.size());
        result.forEach(nv -> System.out.println("NV: " + nv.getMaNV() + " - " + nv.getHoTen()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/danh-sach-theo-vai-tro")
    @Operation(summary = "Danh sách theo vai trò")
    public ResponseEntity<List<NhanVien>> danhSachTheoVaiTro(@RequestParam String vaiTro) {
        List<NhanVien> result = quanLyTaiKhoanNhanVienService.danhSachTheoVaiTro(vaiTro);
        return ResponseEntity.ok(result);
    }
}
