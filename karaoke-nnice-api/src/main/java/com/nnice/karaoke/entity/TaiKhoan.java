package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTaiKhoan")
    private Integer maTaiKhoan;

    @Column(name = "TenDangNhap", unique = true, nullable = false, length = 50)
    private String tenDangNhap;

    @Column(name = "MatKhauHash", nullable = false, length = 255)
    private String matKhauHash;

    @Column(name = "LoaiTaiKhoan", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'KHACH_HANG'")
    private String loaiTaiKhoan;

    @Column(name = "MaKhachHang")
    private Integer maKhachHang;

    @Column(name = "MaNhanVien")
    private Integer maNhanVien;

    @Column(name = "TrangThai", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'Hoat dong'")
    private String trangThai;

    @Column(name = "NgayTao", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ngayTao;

    @Column(name = "NgayCapNhat", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime ngayCapNhat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKhachHang", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNhanVien", insertable = false, updatable = false)
    private NhanVien nhanVien;

    // Constructors
    public TaiKhoan() {}

    public TaiKhoan(String tenDangNhap, String matKhauHash, Integer maKhachHang) {
        this.tenDangNhap = tenDangNhap;
        this.matKhauHash = matKhauHash;
        this.maKhachHang = maKhachHang;
        this.loaiTaiKhoan = "KHACH_HANG";
        this.trangThai = "Hoat dong";
        this.ngayTao = LocalDateTime.now();
        this.ngayCapNhat = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(Integer maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhauHash() {
        return matKhauHash;
    }

    public void setMatKhauHash(String matKhauHash) {
        this.matKhauHash = matKhauHash;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public Integer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Integer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maTaiKhoan=" + maTaiKhoan +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", loaiTaiKhoan='" + loaiTaiKhoan + '\'' +
                ", maKhachHang=" + maKhachHang +
                ", maNhanVien=" + maNhanVien +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
