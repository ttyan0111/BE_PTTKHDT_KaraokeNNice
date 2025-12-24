package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

/**
 * Entity đại diện cho Đơn Đặt Tiệc
 */
@Entity
@Table(name = "DonDatTiec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonDatTiec {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonDatTiec")
    private Integer maDonDatTiec;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH", nullable = false)
    private KhachHang khachHang;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaGoi")
    private GoiTiec goiTiec;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSanh")
    private SanhTiec sanhTiec;
    
    @Column(name = "NgayToChuc")
    private LocalDateTime ngayToChuc;
    
    @Column(name = "SoLuongNguoi")
    private Integer soLuongNguoi;
    
    @Column(name = "TongTien")
    private BigDecimal tongTien;
    
    @Column(name = "TienCoc")
    private BigDecimal tienCoc;
    
    @Column(name = "TrangThai", length = 50)
    private String trangThai; // CHO_XAC_NHAN, DA_COC, DA_THANH_TOAN, HUY
    
    @Column(name = "NgayDat")
    private LocalDateTime ngayDat;
    
    @Column(name = "LyDoHuy", columnDefinition = "TEXT")
    private String lyDoHuy;
    
    @Column(name = "TienHoanCoc")
    private BigDecimal tienHoanCoc;
    
    @Column(name = "GhiChu", columnDefinition = "TEXT")
    private String ghiChu;
}
