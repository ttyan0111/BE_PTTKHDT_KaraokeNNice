package com.nnice.karaoke.dto.response;

import lombok.*;

/**
 * DTO Response cho Gói Tiệc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoiTiecResponse {
    
    private Integer maGoi;
    
    private String tenGoi;
    
    private Long giaTronGoi;
    
    private Integer soLuongNguoiToiThieu;
    
    private Integer soLuongNguoiToiDa;
    
    private String moTa;
    
    private String danhSachMonAn;
    
    private String danhSachDichVu;
}
