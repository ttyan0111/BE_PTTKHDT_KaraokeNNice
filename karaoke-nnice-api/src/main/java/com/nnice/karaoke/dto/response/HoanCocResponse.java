package com.nnice.karaoke.dto.response;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO Response cho tính toán hoàn cọc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoanCocResponse {
    
    private Integer maDonDatTiec;
    
    private BigDecimal tienCocDaThanhToan;
    
    private Long soNgayConLai; // Số ngày từ hiện tại đến ngày tổ chức
    
    private Double tyLeHoan; // % hoàn lại
    
    private BigDecimal tienDuocHoan;
    
    private String chiTietChinhSach;
}
