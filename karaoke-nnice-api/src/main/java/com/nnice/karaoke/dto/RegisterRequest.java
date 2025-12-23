package com.nnice.karaoke.dto;

public class RegisterRequest {
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String tenDangNhap;
    private String matKhau;
    private String confirmMatKhau;

    public RegisterRequest() {}

    // Getters and Setters
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

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getConfirmMatKhau() {
        return confirmMatKhau;
    }

    public void setConfirmMatKhau(String confirmMatKhau) {
        this.confirmMatKhau = confirmMatKhau;
    }
}
