package com.nnice.karaoke.service.impl;

import com.nnice.karaoke.dto.request.DatTiecRequest;
import com.nnice.karaoke.dto.response.DatTiecResponse;
import com.nnice.karaoke.dto.response.HoanCocResponse;
import com.nnice.karaoke.dto.response.SanhTiecResponse;
import com.nnice.karaoke.entity.*;
import com.nnice.karaoke.repository.*;
import com.nnice.karaoke.service.QuanLyDatTiecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuanLyDatTiecServiceImpl implements QuanLyDatTiecService {
    
    @Autowired
    private DonDatTiecRepository donDatTiecRepository;
    
    @Autowired
    private KhachHangRepository khachHangRepository;
    
    @Autowired
    private GoiTiecRepository goiTiecRepository;
    
    @Autowired
    private SanhTiecRepository sanhTiecRepository;
    
    @Autowired
    private ThanhToanRepository thanhToanRepository;
    
    private static final double TY_LE_COC = 0.20; // 20%
    
    @Override
    public DatTiecResponse taoDonDatTiec(DatTiecRequest request) {
        if (request == null || request.getMaKH() == null || request.getMaGoi() == null) {
            throw new RuntimeException("DatTiecRequest, MaKH and MaGoi cannot be null");
        }
        
        DonDatTiec don = new DonDatTiec();
        
        KhachHang khachHang = khachHangRepository.findById(request.getMaKH())
                .orElseThrow(() -> new RuntimeException("KhachHang not found with id: " + request.getMaKH()));
        GoiTiec goiTiec = goiTiecRepository.findById(request.getMaGoi())
                .orElseThrow(() -> new RuntimeException("GoiTiec not found with id: " + request.getMaGoi()));
        
        // Kiểm tra sảnh có trống không (nếu có chọn sảnh)
        if (request.getMaSanh() != null) {
            SanhTiec sanhTiec = sanhTiecRepository.findById(request.getMaSanh())
                    .orElseThrow(() -> new RuntimeException("SanhTiec not found with id: " + request.getMaSanh()));
            
            if (!kiemTraSanhTrong(request.getMaSanh(), request.getNgayToChuc())) {
                throw new RuntimeException("Sảnh tiệc không còn trống trong thời gian này");
            }
            don.setSanhTiec(sanhTiec);
        }
        
        don.setKhachHang(khachHang);
        don.setGoiTiec(goiTiec);
        don.setNgayToChuc(request.getNgayToChuc());
        don.setSoLuongNguoi(request.getSoLuongNguoi());
        don.setNgayDat(LocalDateTime.now());
        don.setTrangThai("CHO_XAC_NHAN");
        
        // Tính tổng tiền
        BigDecimal tongTien = goiTiec.getGiaTronGoi();
        if (don.getSanhTiec() != null && don.getSanhTiec().getGiaThue() != null) {
            tongTien = tongTien.add(BigDecimal.valueOf(don.getSanhTiec().getGiaThue()));
        }
        don.setTongTien(tongTien);
        
        // Tính tiền cọc (20%)
        BigDecimal tienCoc = tongTien.multiply(BigDecimal.valueOf(TY_LE_COC));
        don.setTienCoc(tienCoc);
        
        DonDatTiec saved = donDatTiecRepository.save(don);
        return convertToResponse(saved);
    }
    
    @Override
    public Long tinhChiPhiTiec(Integer maTiec) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        return don.getTongTien() != null ? don.getTongTien().longValue() : 0L;
    }
    
    @Override
    public Long tinhTienDatCoc(Long tongChiPhi) {
        return (long) (tongChiPhi * TY_LE_COC);
    }
    
    @Override
    public void xuLyThanhToanCoc(Integer maTiec, Long soTien, String hinhThuc) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        // Tạo giao dịch thanh toán
        ThanhToan thanhToan = ThanhToan.builder()
                .donDatTiec(don)
                .loaiThanhToan("DAT_COC")
                .soTien(BigDecimal.valueOf(soTien))
                .hinhThucThanhToan(hinhThuc)
                .ngayThanhToan(LocalDateTime.now())
                .trangThai("THANH_CONG")
                .build();
        
        thanhToanRepository.save(thanhToan);
        
        // Cập nhật trạng thái đơn đặt tiệc
        don.setTrangThai("DA_COC");
        donDatTiecRepository.save(don);
    }
    
    @Override
    public DatTiecResponse xemChiTiet(Integer maTiec) {
        return donDatTiecRepository.findById(maTiec)
                .map(this::convertToResponse)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
    }
    
    @Override
    public DatTiecResponse capNhatDatTiec(Integer maTiec, DatTiecRequest request) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        // Cập nhật thông tin
        if (request.getMaKH() != null) {
            KhachHang khachHang = khachHangRepository.findById(request.getMaKH())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found"));
            don.setKhachHang(khachHang);
        }
        
        if (request.getMaGoi() != null) {
            GoiTiec goiTiec = goiTiecRepository.findById(request.getMaGoi())
                    .orElseThrow(() -> new RuntimeException("GoiTiec not found"));
            don.setGoiTiec(goiTiec);
        }
        
        if (request.getMaSanh() != null) {
            SanhTiec sanhTiec = sanhTiecRepository.findById(request.getMaSanh())
                    .orElseThrow(() -> new RuntimeException("SanhTiec not found"));
            don.setSanhTiec(sanhTiec);
        }
        
        if (request.getNgayToChuc() != null) {
            don.setNgayToChuc(request.getNgayToChuc());
        }
        
        if (request.getSoLuongNguoi() != null) {
            don.setSoLuongNguoi(request.getSoLuongNguoi());
        }
        
        // Tính lại chi phí
        BigDecimal tongTien = don.getGoiTiec().getGiaTronGoi();
        if (don.getSanhTiec() != null && don.getSanhTiec().getGiaThue() != null) {
            tongTien = tongTien.add(BigDecimal.valueOf(don.getSanhTiec().getGiaThue()));
        }
        don.setTongTien(tongTien);
        don.setTienCoc(tongTien.multiply(BigDecimal.valueOf(TY_LE_COC)));
        
        DonDatTiec updated = donDatTiecRepository.save(don);
        return convertToResponse(updated);
    }
    
    @Override
    public void huyDatTiec(Integer maTiec, String lyDo) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        // Tính tiền hoàn cọc
        HoanCocResponse hoanCoc = tinhTienHoanCoc(maTiec);
        
        // Nếu có tiền hoàn, tạo giao dịch hoàn cọc
        if (hoanCoc.getTienDuocHoan().compareTo(BigDecimal.ZERO) > 0) {
            ThanhToan thanhToan = ThanhToan.builder()
                    .donDatTiec(don)
                    .loaiThanhToan("HOAN_COC")
                    .soTien(hoanCoc.getTienDuocHoan())
                    .ngayThanhToan(LocalDateTime.now())
                    .trangThai("THANH_CONG")
                    .ghiChu("Hoàn cọc: " + hoanCoc.getChiTietChinhSach())
                    .build();
            
            thanhToanRepository.save(thanhToan);
        }
        
        // Cập nhật đơn đặt tiệc
        don.setTrangThai("HUY");
        don.setLyDoHuy(lyDo);
        don.setTienHoanCoc(hoanCoc.getTienDuocHoan());
        donDatTiecRepository.save(don);
        
        // Giải phóng sảnh tiệc
        if (don.getSanhTiec() != null) {
            don.getSanhTiec().setTrangThai("TRONG");
            sanhTiecRepository.save(don.getSanhTiec());
        }
    }
    
    @Override
    public List<DatTiecResponse> danhSachDatTiec(String trangThai) {
        if (trangThai == null || trangThai.isEmpty()) {
            return donDatTiecRepository.findAll().stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        }
        
        return donDatTiecRepository.findAll().stream()
                .filter(d -> d.getTrangThai().equals(trangThai))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void khoapHongTiec(Integer maTiec) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        if (don.getSanhTiec() != null) {
            SanhTiec sanh = don.getSanhTiec();
            // Toggle trạng thái
            if ("TRONG".equals(sanh.getTrangThai())) {
                sanh.setTrangThai("DANG_SU_DUNG");
            } else {
                sanh.setTrangThai("TRONG");
            }
            sanhTiecRepository.save(sanh);
        }
    }
    
    @Override
    public void guiXacNhan(Integer maTiec) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        // TODO: Tích hợp SMS/Email service
        System.out.println("Gửi xác nhận đến khách hàng: " + don.getKhachHang().getTenKH());
        System.out.println("Email: " + don.getKhachHang().getEmail());
        System.out.println("Điện thoại: " + don.getKhachHang().getSdt());
    }
    
    @Override
    public List<SanhTiecResponse> layDanhSachSanhTrong(LocalDateTime tuNgay, LocalDateTime denNgay) {
        List<SanhTiec> danhSach = sanhTiecRepository.findSanhTrong(tuNgay, denNgay);
        return danhSach.stream()
                .map(this::convertToSanhTiecResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public HoanCocResponse tinhTienHoanCoc(Integer maTiec) {
        DonDatTiec don = donDatTiecRepository.findById(maTiec)
                .orElseThrow(() -> new RuntimeException("DonDatTiec not found"));
        
        // Kiểm tra đã thanh toán cọc chưa
        boolean daThanhToanCoc = thanhToanRepository.isDaThanhToanCoc(maTiec);
        if (!daThanhToanCoc) {
            return HoanCocResponse.builder()
                    .maDonDatTiec(maTiec)
                    .tienCocDaThanhToan(BigDecimal.ZERO)
                    .soNgayConLai(0L)
                    .tyLeHoan(0.0)
                    .tienDuocHoan(BigDecimal.ZERO)
                    .chiTietChinhSach("Chưa thanh toán cọc")
                    .build();
        }
        
        LocalDateTime now = LocalDateTime.now();
        long soNgayConLai = ChronoUnit.DAYS.between(now, don.getNgayToChuc());
        
        double tyLeHoan;
        String chiTiet;
        
        // Chính sách hoàn cọc
        if (soNgayConLai >= 30) {
            tyLeHoan = 0.9; // Hoàn 90%
            chiTiet = "Hủy trước 30 ngày: Hoàn 90%";
        } else if (soNgayConLai >= 14) {
            tyLeHoan = 0.7; // Hoàn 70%
            chiTiet = "Hủy trước 14-29 ngày: Hoàn 70%";
        } else if (soNgayConLai >= 7) {
            tyLeHoan = 0.5; // Hoàn 50%
            chiTiet = "Hủy trước 7-13 ngày: Hoàn 50%";
        } else if (soNgayConLai >= 3) {
            tyLeHoan = 0.3; // Hoàn 30%
            chiTiet = "Hủy trước 3-6 ngày: Hoàn 30%";
        } else {
            tyLeHoan = 0.0; // Không hoàn
            chiTiet = "Hủy trong vòng 3 ngày: Không hoàn cọc";
        }
        
        BigDecimal tienCoc = don.getTienCoc() != null ? don.getTienCoc() : BigDecimal.ZERO;
        BigDecimal tienDuocHoan = tienCoc.multiply(BigDecimal.valueOf(tyLeHoan));
        
        return HoanCocResponse.builder()
                .maDonDatTiec(maTiec)
                .tienCocDaThanhToan(tienCoc)
                .soNgayConLai(soNgayConLai)
                .tyLeHoan(tyLeHoan)
                .tienDuocHoan(tienDuocHoan)
                .chiTietChinhSach(chiTiet)
                .build();
    }
    
    @Override
    public boolean kiemTraSanhTrong(Integer maSanh, LocalDateTime ngayToChuc) {
        // Kiểm tra trong khoảng ±4 giờ
        LocalDateTime tuNgay = ngayToChuc.minusHours(4);
        LocalDateTime denNgay = ngayToChuc.plusHours(4);
        
        List<SanhTiec> danhSachTrong = sanhTiecRepository.findSanhTrong(tuNgay, denNgay);
        return danhSachTrong.stream().anyMatch(s -> s.getMaSanh().equals(maSanh));
    }
    
    /**
     * Convert DonDatTiec entity to DatTiecResponse DTO
     */
    private DatTiecResponse convertToResponse(DonDatTiec don) {
        return DatTiecResponse.builder()
                .maDonDatTiec(don.getMaDonDatTiec())
                .maKH(don.getKhachHang() != null ? don.getKhachHang().getMaKH() : null)
                .tenKH(don.getKhachHang() != null ? don.getKhachHang().getTenKH() : null)
                .maGoi(don.getGoiTiec() != null ? don.getGoiTiec().getMaGoi() : null)
                .tenGoi(don.getGoiTiec() != null ? don.getGoiTiec().getTenGoi() : null)
                .ngayToChuc(don.getNgayToChuc())
                .soLuongNguoi(don.getSoLuongNguoi())
                .tongTien(don.getTongTien())
                .tienCoc(don.getTienCoc())
                .trangThai(don.getTrangThai())
                .build();
    }
    
    /**
     * Convert SanhTiec entity to SanhTiecResponse DTO
     */
    private SanhTiecResponse convertToSanhTiecResponse(SanhTiec sanh) {
        return SanhTiecResponse.builder()
                .maSanh(sanh.getMaSanh())
                .tenSanh(sanh.getTenSanh())
                .sucChua(sanh.getSucChua())
                .dienTich(sanh.getDienTich())
                .giaThue(sanh.getGiaThue())
                .trangThai(sanh.getTrangThai())
                .moTa(sanh.getMoTa())
                .build();
    }
}
