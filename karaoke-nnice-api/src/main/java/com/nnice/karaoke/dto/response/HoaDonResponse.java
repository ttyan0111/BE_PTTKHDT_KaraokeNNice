package com.nnice.karaoke.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonResponse {
    private Integer maHD;
    private Integer maPhieuSuDung;
    private Integer maKH;
    private LocalDateTime ngayLap;
    private BigDecimal tienPhong;
    private BigDecimal tienDichVu;
    private BigDecimal tongTienChuaThue;
    private BigDecimal thueVAT;
    private BigDecimal giamGia;
    private BigDecimal tongTien;
    private BigDecimal tienCocDaTra;
    private BigDecimal conPhaiTra;
    private String hinhThucThanhToan;
    private String trangThai;
    private Integer maNVThanhToan;
}
