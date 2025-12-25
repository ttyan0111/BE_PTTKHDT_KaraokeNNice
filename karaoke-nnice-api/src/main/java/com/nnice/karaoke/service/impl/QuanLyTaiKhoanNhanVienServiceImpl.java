package com.nnice.karaoke.service.impl;

import com.nnice.karaoke.entity.NhanVien;
import com.nnice.karaoke.entity.TaiKhoan;
import com.nnice.karaoke.repository.NhanVienRepository;
import com.nnice.karaoke.repository.TaiKhoanRepository;
import com.nnice.karaoke.service.QuanLyTaiKhoanNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuanLyTaiKhoanNhanVienServiceImpl implements QuanLyTaiKhoanNhanVienService {
    
    @Autowired
    private NhanVienRepository nhanVienRepository;
    
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public NhanVien taoTaiKhoan(NhanVien nhanVien, String username, String password) {
        // Kiểm tra username đã tồn tại chưa
        if (taiKhoanRepository.existsByTenDangNhap(username)) {
            throw new RuntimeException("Tên đăng nhập (số điện thoại) đã tồn tại!");
        }
        
        // Lưu nhân viên trước
        NhanVien savedNhanVien = nhanVienRepository.save(nhanVien);
        
        // Tạo tài khoản cho nhân viên
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(username);
        taiKhoan.setMatKhauHash(passwordEncoder.encode(password));
        taiKhoan.setLoaiTaiKhoan("NHAN_VIEN");
        taiKhoan.setMaNhanVien(savedNhanVien.getMaNV());
        taiKhoan.setMaKhachHang(null);  // Nhân viên không có mã khách hàng
        taiKhoan.setTrangThai("Hoat dong");
        taiKhoan.setNgayTao(LocalDateTime.now());
        taiKhoan.setNgayCapNhat(LocalDateTime.now());
        
        taiKhoanRepository.save(taiKhoan);
        
        System.out.println("✅ Đã tạo nhân viên " + savedNhanVien.getHoTen() + " với tài khoản: " + username);
        
        return savedNhanVien;
    }
    
    @Override
    public Optional<NhanVien> xemThongTin(Integer maNhanVien) {
        return nhanVienRepository.findById(maNhanVien);
    }
    
    @Override
    public NhanVien capNhatThongTin(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }
    
    @Override
    public void voHieuHoaTaiKhoan(Integer maNhanVien) {
        Optional<NhanVien> nhanVien = nhanVienRepository.findById(maNhanVien);
        if (nhanVien.isPresent()) {
            NhanVien nv = nhanVien.get();
            nhanVienRepository.delete(nv);
        }
    }
    
    @Override
    public void thietLapQuyenTruCap(Integer maNhanVien, String vaiTro) {
        // TODO: Implement
    }
    
    @Override
    public void datLaiMatKhau(Integer maNhanVien, String matKhauMacDinh) {
        // TODO: Implement
    }
    
    @Override
    public List<NhanVien> danhSachTaiKhoan() {
        List<NhanVien> result = nhanVienRepository.findAll();
        System.out.println("=== danhSachTaiKhoan() ===");
        System.out.println("Total: " + result.size());
        result.forEach(nv -> System.out.println("- " + nv.getMaNV() + ": " + nv.getHoTen()));
        return result;
    }
    
    @Override
    public List<NhanVien> danhSachTheoVaiTro(String vaiTro) {
        return nhanVienRepository.findAll();
    }
}
