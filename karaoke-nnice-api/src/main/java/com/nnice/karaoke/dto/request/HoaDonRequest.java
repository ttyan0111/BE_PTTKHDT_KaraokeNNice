package com.nnice.karaoke.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonRequest {
    private Integer maHD;
    private Integer maPhieuSuDung;
    private Integer maKH;
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
}
