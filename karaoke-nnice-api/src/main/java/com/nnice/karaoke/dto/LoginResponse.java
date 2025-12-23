package com.nnice.karaoke.dto;

public class LoginResponse {
    private Integer maTaiKhoan;
    private String tenDangNhap;
    private String loaiTaiKhoan;
    private Integer maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String token;

    public LoginResponse() {}

    public LoginResponse(Integer maTaiKhoan, String tenDangNhap, String loaiTaiKhoan, 
                        Integer maKhachHang, String hoTen, String token) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.token = token;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
