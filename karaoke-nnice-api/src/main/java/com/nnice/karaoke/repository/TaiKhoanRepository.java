package com.nnice.karaoke.repository;

import com.nnice.karaoke.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    
    /**
     * Tìm tài khoản theo tên đăng nhập
     */
    Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);
    
    /**
     * Tìm tài khoản theo mã khách hàng
     */
    Optional<TaiKhoan> findByMaKhachHang(Integer maKhachHang);
    
    /**
     * Tìm tài khoản theo mã nhân viên
     */
    Optional<TaiKhoan> findByMaNhanVien(Integer maNhanVien);
    
    /**
     * Kiểm tra tên đăng nhập đã tồn tại chưa
     */
    boolean existsByTenDangNhap(String tenDangNhap);
}
