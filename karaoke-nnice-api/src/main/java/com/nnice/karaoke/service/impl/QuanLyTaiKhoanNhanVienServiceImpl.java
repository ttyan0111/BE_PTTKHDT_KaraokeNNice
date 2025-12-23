package com.nnice.karaoke.service.impl;

import com.nnice.karaoke.entity.NhanVien;
import com.nnice.karaoke.repository.NhanVienRepository;
import com.nnice.karaoke.service.QuanLyTaiKhoanNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuanLyTaiKhoanNhanVienServiceImpl implements QuanLyTaiKhoanNhanVienService {
    
    @Autowired
    private NhanVienRepository nhanVienRepository;
    
    @Override
    public NhanVien taoTaiKhoan(NhanVien nhanVien, String username, String password) {
        return nhanVienRepository.save(nhanVien);
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
