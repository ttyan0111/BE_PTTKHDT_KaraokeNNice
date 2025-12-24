package com.nnice.karaoke.dto.request;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO Request cho tạo/cập nhật Gói Tiệc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiTiecRequest {
    
    private String tenGoi;
    
    private Long giaTronGoi;
    
    private Integer soLuongNguoiToiThieu;
    
    private Integer soLuongNguoiToiDa;
    
    private String moTa;
    
    private String danhSachMonAn; // JSON string hoặc comma-separated
    
    private String danhSachDichVu; // JSON string hoặc comma-separated
}
