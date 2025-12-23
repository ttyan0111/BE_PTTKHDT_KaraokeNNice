package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "NhanVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer maNV;
    
    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;
    
    @Column(name = "ChucVu", length = 50)
    private String chucVu;
    
    @Column(name = "SDT", length = 15)
    private String sdt;
    
    @Column(name = "Email", length = 100)
    private String email;
    
    @Column(name = "DiaChi", length = 255)
    private String diaChi;
    
    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;
    
    @Column(name = "CMND", length = 20)
    private String cmnd;
    
    @Column(name = "NgayVaoLam")
    private LocalDate ngayVaoLam;
    
    @Column(name = "HeSoLuong")
    private Float heSoLuong;
    
    @Column(name = "TyLeThuongDoanhThu")
    private Float tyLeThuongDoanhThu;
    
    @Column(name = "TrangThai", length = 50)
    private String trangThai;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
    
    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaCS")
    @JsonIgnore
    private CoSo coSo;
    
    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BangChamCong> bangChamCongs;
    
    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BangLuong> bangLuongs;
    
    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PhieuSuDung> phieuSuDungs;
}
