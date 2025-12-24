package com.nnice.karaoke.controller;

import com.nnice.karaoke.dto.response.SanhTiecResponse;
import com.nnice.karaoke.entity.SanhTiec;
import com.nnice.karaoke.repository.SanhTiecRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sanh-tiec")
@Tag(name = "Sảnh Tiệc", description = "API quản lý sảnh tiệc")
@CrossOrigin(origins = "*")
public class SanhTiecController {

    @Autowired
    private SanhTiecRepository sanhTiecRepository;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả sảnh tiệc")
    public ResponseEntity<List<SanhTiecResponse>> getAllSanhTiec() {
        List<SanhTiec> list = sanhTiecRepository.findAll();
        List<SanhTiecResponse> response = list.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết sảnh tiệc theo ID")
    public ResponseEntity<SanhTiecResponse> getSanhTiecById(@PathVariable Integer id) {
        return sanhTiecRepository.findById(id)
                .map(this::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/theo-suc-chua")
    @Operation(summary = "Tìm sảnh theo sức chứa tối thiểu")
    public ResponseEntity<List<SanhTiecResponse>> findBySucChua(
            @RequestParam Integer sucChua,
            @RequestParam(defaultValue = "TRONG") String trangThai) {
        List<SanhTiec> list = sanhTiecRepository.findBySucChuaGreaterThanEqualAndTrangThai(sucChua, trangThai);
        List<SanhTiecResponse> response = list.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Tạo sảnh tiệc mới")
    public ResponseEntity<SanhTiecResponse> createSanhTiec(@RequestBody SanhTiec sanhTiec) {
        sanhTiec.setTrangThai("TRONG");
        SanhTiec saved = sanhTiecRepository.save(sanhTiec);
        return new ResponseEntity<>(convertToResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật sảnh tiệc")
    public ResponseEntity<SanhTiecResponse> updateSanhTiec(
            @PathVariable Integer id,
            @RequestBody SanhTiec sanhTiecRequest) {
        return sanhTiecRepository.findById(id)
                .map(sanh -> {
                    sanh.setTenSanh(sanhTiecRequest.getTenSanh());
                    sanh.setSucChua(sanhTiecRequest.getSucChua());
                    sanh.setDienTich(sanhTiecRequest.getDienTich());
                    sanh.setGiaThue(sanhTiecRequest.getGiaThue());
                    sanh.setTrangThai(sanhTiecRequest.getTrangThai());
                    sanh.setMoTa(sanhTiecRequest.getMoTa());
                    SanhTiec updated = sanhTiecRepository.save(sanh);
                    return ResponseEntity.ok(convertToResponse(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sảnh tiệc")
    public ResponseEntity<Void> deleteSanhTiec(@PathVariable Integer id) {
        if (sanhTiecRepository.existsById(id)) {
            sanhTiecRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private SanhTiecResponse convertToResponse(SanhTiec sanh) {
        return SanhTiecResponse.builder()
                .maSanh(sanh.getMaSanh())
                .tenSanh(sanh.getTenSanh())
                .sucChua(sanh.getSucChua())
                .dienTich(sanh.getDienTich())
                .giaThue(sanh.getGiaThue())
                .trangThai(sanh.getTrangThai())
                .moTa(sanh.getMoTa())
                .build();
    }
}
