package com.nnice.karaoke.service;

import com.nnice.karaoke.dto.LoginRequest;
import com.nnice.karaoke.dto.LoginResponse;
import com.nnice.karaoke.dto.RegisterRequest;
import com.nnice.karaoke.dto.RegisterResponse;
import com.nnice.karaoke.entity.KhachHang;
import com.nnice.karaoke.entity.NhanVien;
import com.nnice.karaoke.entity.TaiKhoan;
import com.nnice.karaoke.repository.KhachHangRepository;
import com.nnice.karaoke.repository.NhanVienRepository;
import com.nnice.karaoke.repository.TaiKhoanRepository;
import com.nnice.karaoke.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Đăng nhập - login
     */
    public LoginResponse login(LoginRequest request) {
        // 1. Tìm tài khoản theo tên đăng nhập
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findByTenDangNhap(request.getTenDangNhap());
        
        if (!optionalTaiKhoan.isPresent()) {
            throw new RuntimeException("Tên đăng nhập không tồn tại");
        }

        TaiKhoan taiKhoan = optionalTaiKhoan.get();

        // 2. Kiểm tra trạng thái tài khoản
        if (!"Hoat dong".equals(taiKhoan.getTrangThai())) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        // 3. So sánh password
        if (!passwordEncoder.matches(request.getMatKhau(), taiKhoan.getMatKhauHash())) {
            throw new RuntimeException("Mật khẩu không chính xác");
        }

        // 4. Lấy thông tin người dùng
        String hoTen = "";
        if ("KHACH_HANG".equals(taiKhoan.getLoaiTaiKhoan()) && taiKhoan.getMaKhachHang() != null) {
            // Lấy tên từ KhachHang
            Optional<KhachHang> khachHang = khachHangRepository.findById(taiKhoan.getMaKhachHang());
            if (khachHang.isPresent()) {
                hoTen = khachHang.get().getTenKH();
            }
        } else if ("NHAN_VIEN".equals(taiKhoan.getLoaiTaiKhoan()) && taiKhoan.getMaNhanVien() != null) {
            // Lấy tên từ NhanVien
            Optional<NhanVien> nhanVien = nhanVienRepository.findById(taiKhoan.getMaNhanVien());
            if (nhanVien.isPresent()) {
                hoTen = nhanVien.get().getHoTen();
            }
        }

        // 5. Tạo JWT token
        String token = jwtTokenProvider.generateToken(
            taiKhoan.getMaTaiKhoan(),
            taiKhoan.getTenDangNhap(),
            taiKhoan.getLoaiTaiKhoan(),
            taiKhoan.getMaKhachHang(),
            hoTen
        );

        // 6. Trả về response
        LoginResponse response = new LoginResponse(
            taiKhoan.getMaTaiKhoan(),
            taiKhoan.getTenDangNhap(),
            taiKhoan.getLoaiTaiKhoan(),
            taiKhoan.getMaKhachHang(),
            hoTen,
            token
        );

        return response;
    }

    /**
     * Đăng ký - register (Tạo KhachHang + TaiKhoan)
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // 1. Validate dữ liệu
        if (request.getTenDangNhap() == null || request.getTenDangNhap().trim().isEmpty()) {
            throw new RuntimeException("Tên đăng nhập không được để trống");
        }

        if (request.getMatKhau() == null || request.getMatKhau().trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu không được để trống");
        }

        if (!request.getMatKhau().equals(request.getConfirmMatKhau())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp");
        }

        // 2. Kiểm tra tên đăng nhập đã tồn tại chưa
        if (taiKhoanRepository.existsByTenDangNhap(request.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }

        // 3. Kiểm tra số điện thoại đã tồn tại chưa (nếu có)
        if (request.getSoDienThoai() != null && !request.getSoDienThoai().trim().isEmpty()) {
            if (khachHangRepository.existsBySdt(request.getSoDienThoai())) {
                throw new RuntimeException("Số điện thoại đã tồn tại");
            }
        }

        // 4. Tạo KhachHang mới
        KhachHang khachHang = new KhachHang();
        khachHang.setTenKH(request.getHoTen());
        khachHang.setSdt(request.getSoDienThoai());
        khachHang.setEmail(request.getEmail());
        khachHang.setLoaiKhach("Thuong");
        khachHang = khachHangRepository.save(khachHang);

        // 5. Tạo TaiKhoan nối lại KhachHang
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(request.getTenDangNhap());
        taiKhoan.setMatKhauHash(passwordEncoder.encode(request.getMatKhau()));
        taiKhoan.setLoaiTaiKhoan("KHACH_HANG");
        taiKhoan.setMaKhachHang(khachHang.getMaKH());
        taiKhoan.setTrangThai("Hoat dong");
        taiKhoan = taiKhoanRepository.save(taiKhoan);

        // 6. Tạo JWT token
        String token = jwtTokenProvider.generateToken(
            taiKhoan.getMaTaiKhoan(),
            taiKhoan.getTenDangNhap(),
            taiKhoan.getLoaiTaiKhoan(),
            taiKhoan.getMaKhachHang(),
            khachHang.getTenKH()
        );

        // 7. Trả về response
        RegisterResponse response = new RegisterResponse(
            taiKhoan.getMaTaiKhoan(),
            khachHang.getMaKH(),
            taiKhoan.getTenDangNhap(),
            khachHang.getTenKH(),
            token
        );

        return response;
    }

    /**
     * Kiểm tra quyền hạn - Check authority
     */
    public boolean hasAuthority(String token, String requiredRole) {
        try {
            if (!jwtTokenProvider.validateToken(token)) {
                return false;
            }

            String userRole = jwtTokenProvider.getLoaiTaiKhoanFromToken(token);
            return requiredRole.equals(userRole);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lấy thông tin tài khoản từ token
     */
    public TaiKhoan getTaiKhoanFromToken(String token) {
        Integer maTaiKhoan = jwtTokenProvider.getMaTaiKhoanFromToken(token);
        if (maTaiKhoan != null) {
            Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findById(maTaiKhoan);
            return taiKhoan.orElse(null);
        }
        return null;
    }
}
