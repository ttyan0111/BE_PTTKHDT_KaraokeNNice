package com.nnice.karaoke.dto.response;

import lombok.*;

/**
 * DTO Response cho Sảnh Tiệc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanhTiecResponse {
    
    private Integer maSanh;
    
    private String tenSanh;
    
    private Integer sucChua;
    
    private Double dienTich;
    
    private Long giaThue;
    
    private String trangThai;
    
    private String moTa;
}
