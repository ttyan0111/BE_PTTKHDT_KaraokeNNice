package com.nnice.karaoke.dto;

public class RegisterResponse {
    private Integer maTaiKhoan;
    private Integer maKhachHang;
    private String tenDangNhap;
    private String hoTen;
    private String token;

    public RegisterResponse() {}

    public RegisterResponse(Integer maTaiKhoan, Integer maKhachHang, String tenDangNhap, 
                          String hoTen, String token) {
        this.maTaiKhoan = maTaiKhoan;
        this.maKhachHang = maKhachHang;
        this.tenDangNhap = tenDangNhap;
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

    public Integer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Integer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
