package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity đại diện cho Khách Hàng
 */
@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;
    
    @Column(name = "TenKH", nullable = false, length = 100)
    private String tenKH;
    
    @Column(name = "SDT", unique = true, length = 15)
    private String sdt;
    
    @Column(name = "Email", length = 100)
    private String email;
    
    @Column(name = "CMND", length = 20)
    private String cmnd;
    
    @Column(name = "LoaiKhach", length = 50)
    private String loaiKhach;
    
    @Column(name = "NgayDangKy")
    private LocalDate ngayDangKy;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
    
    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToOne(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TheThanhVien theThanhVien;
    
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhieuDatPhong> phieuDatPhongs;
    
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DonDatTiec> donDatTiecs;
    
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;
}
