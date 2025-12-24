package com.nnice.karaoke.service.impl;

import com.nnice.karaoke.dto.request.HoaDonRequest;
import com.nnice.karaoke.dto.response.HoaDonResponse;
import com.nnice.karaoke.entity.HoaDon;
import com.nnice.karaoke.repository.HoaDonRepository;
import com.nnice.karaoke.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HoaDonServiceImpl implements HoaDonService {
    
    private final HoaDonRepository hoaDonRepository;
    
    private HoaDonResponse convertToResponse(HoaDon entity) {
        if (entity == null) return null;
        return HoaDonResponse.builder()
                .maHD(entity.getMaHD())
                .maPhieuSuDung(entity.getPhieuSuDung() != null ? entity.getPhieuSuDung().getMaPhieuSuDung() : null)
                .maKH(entity.getKhachHang() != null ? entity.getKhachHang().getMaKH() : null)
                .ngayLap(entity.getNgayLap())
                .tienPhong(entity.getTienPhong())
                .tienDichVu(entity.getTienDichVu())
                .tongTienChuaThue(entity.getTongTienChuaThue())
                .thueVAT(entity.getThueVAT())
                .giamGia(entity.getGiamGia())
                .tongTien(entity.getTongTien())
                .tienCocDaTra(entity.getTienCocDaTra())
                .conPhaiTra(entity.getConPhaiTra())
                .hinhThucThanhToan(entity.getHinhThucThanhToan())
                .trangThai(entity.getTrangThai())
                .maNVThanhToan(entity.getNhanVienThanhToan() != null ? entity.getNhanVienThanhToan().getMaNV() : null)
                .build();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HoaDonResponse> getAll() {
        return hoaDonRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public HoaDonResponse getById(Integer maHD) {
        return hoaDonRepository.findById(maHD)
                .map(this::convertToResponse)
                .orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<HoaDonResponse> getByTrangThai(String trangThai) {
        return hoaDonRepository.findByTrangThai(trangThai).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public HoaDonResponse create(HoaDonRequest request) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayLap(java.time.LocalDateTime.now());
        hoaDon.setTienPhong(request.getTienPhong());
        hoaDon.setTienDichVu(request.getTienDichVu());
        hoaDon.setTongTienChuaThue(request.getTongTienChuaThue());
        hoaDon.setThueVAT(request.getThueVAT());
        hoaDon.setGiamGia(request.getGiamGia());
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setTienCocDaTra(request.getTienCocDaTra());
        hoaDon.setConPhaiTra(request.getConPhaiTra());
        hoaDon.setHinhThucThanhToan(request.getHinhThucThanhToan());
        hoaDon.setTrangThai(request.getTrangThai() != null ? request.getTrangThai() : "Chưa thanh toán");
        hoaDon.setCreatedAt(java.time.LocalDateTime.now());
        hoaDon.setUpdatedAt(java.time.LocalDateTime.now());
        
        HoaDon saved = hoaDonRepository.save(hoaDon);
        return convertToResponse(saved);
    }
    
    @Override
    public HoaDonResponse update(HoaDonRequest request) {
        if (request.getMaHD() == null || !hoaDonRepository.existsById(request.getMaHD())) {
            return null;
        }
        
        HoaDon hoaDon = hoaDonRepository.findById(request.getMaHD()).get();
        hoaDon.setTienPhong(request.getTienPhong());
        hoaDon.setTienDichVu(request.getTienDichVu());
        hoaDon.setTongTienChuaThue(request.getTongTienChuaThue());
        hoaDon.setThueVAT(request.getThueVAT());
        hoaDon.setGiamGia(request.getGiamGia());
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setTienCocDaTra(request.getTienCocDaTra());
        hoaDon.setConPhaiTra(request.getConPhaiTra());
        hoaDon.setHinhThucThanhToan(request.getHinhThucThanhToan());
        hoaDon.setTrangThai(request.getTrangThai());
        hoaDon.setUpdatedAt(java.time.LocalDateTime.now());
        
        HoaDon saved = hoaDonRepository.save(hoaDon);
        return convertToResponse(saved);
    }
    
    @Override
    public boolean delete(Integer maHD) {
        if (!hoaDonRepository.existsById(maHD)) {
            return false;
        }
        hoaDonRepository.deleteById(maHD);
        return true;
    }
}
