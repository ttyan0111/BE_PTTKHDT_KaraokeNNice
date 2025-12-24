package com.nnice.karaoke.controller;

import com.nnice.karaoke.dto.request.GoiTiecRequest;
import com.nnice.karaoke.dto.response.GoiTiecResponse;
import com.nnice.karaoke.entity.GoiTiec;
import com.nnice.karaoke.repository.GoiTiecRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/goi-tiec")
@Tag(name = "Gói Tiệc", description = "API quản lý gói tiệc")
@CrossOrigin(origins = "*")
public class GoiTiecController {

    @Autowired
    private GoiTiecRepository goiTiecRepository;

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả gói tiệc")
    public ResponseEntity<List<GoiTiecResponse>> getAllGoiTiec() {
        List<GoiTiec> list = goiTiecRepository.findAll();
        List<GoiTiecResponse> response = list.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết gói tiệc theo ID")
    public ResponseEntity<GoiTiecResponse> getGoiTiecById(@PathVariable Integer id) {
        return goiTiecRepository.findById(id)
                .map(this::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Tạo gói tiệc mới")
    public ResponseEntity<GoiTiecResponse> createGoiTiec(@RequestBody GoiTiecRequest request) {
        GoiTiec goiTiec = new GoiTiec();
        goiTiec.setTenGoi(request.getTenGoi());
        goiTiec.setGiaTronGoi(BigDecimal.valueOf(request.getGiaTronGoi()));
        
        GoiTiec saved = goiTiecRepository.save(goiTiec);
        return new ResponseEntity<>(convertToResponse(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật gói tiệc")
    public ResponseEntity<GoiTiecResponse> updateGoiTiec(
            @PathVariable Integer id,
            @RequestBody GoiTiecRequest request) {
        return goiTiecRepository.findById(id)
                .map(goiTiec -> {
                    goiTiec.setTenGoi(request.getTenGoi());
                    goiTiec.setGiaTronGoi(BigDecimal.valueOf(request.getGiaTronGoi()));
                    GoiTiec updated = goiTiecRepository.save(goiTiec);
                    return ResponseEntity.ok(convertToResponse(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa gói tiệc")
    public ResponseEntity<Void> deleteGoiTiec(@PathVariable Integer id) {
        if (goiTiecRepository.existsById(id)) {
            goiTiecRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private GoiTiecResponse convertToResponse(GoiTiec goiTiec) {
        return GoiTiecResponse.builder()
                .maGoi(goiTiec.getMaGoi())
                .tenGoi(goiTiec.getTenGoi())
                .giaTronGoi(goiTiec.getGiaTronGoi() != null ? goiTiec.getGiaTronGoi().longValue() : 0L)
                .build();
    }
}
