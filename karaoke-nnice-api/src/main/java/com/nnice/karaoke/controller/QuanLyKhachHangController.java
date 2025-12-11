package com.nnice.karaoke.controller;

import com.nnice.karaoke.dto.request.MemberRegistrationRequest;
import com.nnice.karaoke.dto.response.MemberRegistrationResponse;
import com.nnice.karaoke.entity.KhachHang;
import com.nnice.karaoke.exception.ResourceNotFoundException;
import com.nnice.karaoke.repository.KhachHangRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/khach-hang")
@Tag(name = "Quản Lý Khách Hàng", description = "API quản lý khách hàng")
public class QuanLyKhachHangController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @PostMapping("/dang-ky")
    @Operation(summary = "Đăng ký khách hàng mới")
    public ResponseEntity<MemberRegistrationResponse> dangKyKhachHang(
            @RequestBody MemberRegistrationRequest request) {
        if (request.getTenKH() == null || request.getTenKH().isEmpty()) {
            throw new RuntimeException("Tên khách hàng không được để trống");
        }

        KhachHang khachHang = KhachHang.builder()
                .tenKH(request.getTenKH())
                .sdt(request.getSdt())
                .email(request.getEmail())
                .cmnd(request.getCmnd())
                .build();

        KhachHang saved = khachHangRepository.save(khachHang);
        return new ResponseEntity<>(convertToResponse(saved), HttpStatus.CREATED);
    }

    @GetMapping("/sdt/{sdt}")
    @Operation(summary = "Tìm khách hàng theo số điện thoại")
    public ResponseEntity<MemberRegistrationResponse> findBySdt(@PathVariable String sdt) {
        if (sdt == null || sdt.isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống");
        }

        KhachHang khachHang = khachHangRepository.findAll().stream()
                .filter(k -> k.getSdt() != null && k.getSdt().equals(sdt))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với số điện thoại: " + sdt));

        return ResponseEntity.ok(convertToResponse(khachHang));
    }

    @GetMapping("/{maKH}")
    @Operation(summary = "Lấy thông tin khách hàng theo ID")
    public ResponseEntity<MemberRegistrationResponse> getKhachHang(@PathVariable Integer maKH) {
        if (maKH == null) {
            throw new RuntimeException("Mã khách hàng không được để trống");
        }

        KhachHang khachHang = khachHangRepository.findById(maKH)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với ID: " + maKH));

        return ResponseEntity.ok(convertToResponse(khachHang));
    }

    @PutMapping("/{maKH}")
    @Operation(summary = "Cập nhật thông tin khách hàng")
    public ResponseEntity<MemberRegistrationResponse> updateKhachHang(
            @PathVariable Integer maKH,
            @RequestBody MemberRegistrationRequest request) {
        if (maKH == null) {
            throw new RuntimeException("Mã khách hàng không được để trống");
        }

        KhachHang khachHang = khachHangRepository.findById(maKH)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với ID: " + maKH));

        if (request.getTenKH() != null) khachHang.setTenKH(request.getTenKH());
        if (request.getSdt() != null) khachHang.setSdt(request.getSdt());
        if (request.getEmail() != null) khachHang.setEmail(request.getEmail());
        if (request.getCmnd() != null) khachHang.setCmnd(request.getCmnd());
        KhachHang updated = khachHangRepository.save(khachHang);
        return ResponseEntity.ok(convertToResponse(updated));
    }

    @GetMapping("/tat-ca")
    @Operation(summary = "Lấy danh sách tất cả khách hàng")
    public ResponseEntity<List<KhachHang>> getAllKhachHang() {
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        return ResponseEntity.ok(khachHangList);
    }

    private MemberRegistrationResponse convertToResponse(KhachHang khachHang) {
        return MemberRegistrationResponse.builder()
                .maKH(khachHang.getMaKH())
                .hoTen(khachHang.getTenKH())
                .soDienThoai(khachHang.getSdt())
                .email(khachHang.getEmail())   
                .cmndCccd(khachHang.getCmnd())
                .build();
    }
}
