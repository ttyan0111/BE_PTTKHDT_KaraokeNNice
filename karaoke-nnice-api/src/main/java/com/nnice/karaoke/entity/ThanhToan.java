package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

/**
 * Entity đại diện cho Thanh Toán (Giao dịch)
 */
@Entity
@Table(name = "ThanhToan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThanhToan")
    private Integer maThanhToan;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDonDatTiec")
    private DonDatTiec donDatTiec;
    
    @Column(name = "LoaiThanhToan", length = 50, nullable = false)
    private String loaiThanhToan; // DAT_COC, THANH_TOAN_DU, HOAN_COC
    
    @Column(name = "SoTien", nullable = false)
    private BigDecimal soTien;
    
    @Column(name = "HinhThucThanhToan", length = 50)
    private String hinhThucThanhToan; // TIEN_MAT, CHUYEN_KHOAN, THE
    
    @Column(name = "NgayThanhToan")
    private LocalDateTime ngayThanhToan;
    
    @Column(name = "GhiChu", columnDefinition = "TEXT")
    private String ghiChu;
    
    @Column(name = "TrangThai", length = 50)
    private String trangThai; // THANH_CONG, THAT_BAI, CHO_XU_LY
}
