package com.nnice.karaoke.service.impl;

import com.nnice.karaoke.dto.request.DatPhongRequest;
import com.nnice.karaoke.dto.response.DatPhongResponse;
import com.nnice.karaoke.entity.DatPhong;
import com.nnice.karaoke.entity.KhachHang;
import com.nnice.karaoke.entity.Phong;
import com.nnice.karaoke.exception.ResourceNotFoundException;
import com.nnice.karaoke.repository.DatPhongRepository;
import com.nnice.karaoke.repository.KhachHangRepository;
import com.nnice.karaoke.repository.PhongRepository;
import com.nnice.karaoke.service.IQuanLyDatPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation của Service
 * Xử lý logic business:
 * 1. Nhận Request DTO từ Controller
 * 2. Convert sang Entity
 * 3. Gọi Repository để lưu/lấy dữ liệu
 * 4. Xử lý business logic
 * 5. Convert Entity sang Response DTO
 * 6. Trả về Response DTO cho Controller
 */
@Service
public class QuanLyDatPhongServiceImpl implements IQuanLyDatPhongService {
    
    @Autowired
    private DatPhongRepository datPhongRepository;
    
    @Autowired
    private KhachHangRepository khachHangRepository;
    
    @Autowired
    private PhongRepository phongRepository;
    
    /**
     * FLOW: Request → Service → Repository → Database
     *       Response ← Service ← Repository ← Database
     */
    @Override
    public DatPhongResponse datPhongMoi(DatPhongRequest request) {
        // 1. Kiểm tra khách hàng có tồn tại không
        KhachHang khachHang = khachHangRepository.findById(request.getKhachHangId())
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));
        
        // 2. Kiểm tra phòng có tồn tại không
        Phong phong = phongRepository.findById(request.getPhongId())
                .orElseThrow(() -> new ResourceNotFoundException("Phòng không tồn tại"));
        
        // 3. Tạo entity DatPhong từ request
        DatPhong datPhong = new DatPhong();
        datPhong.setKhachHang(khachHang);
        datPhong.setPhong(phong);
        datPhong.setThoiGianVao(request.getThoiGianVao());
        datPhong.setThoiGianRaDuKien(request.getThoiGianRaDuKien());
        datPhong.setTrangThai("CHO_SU_DUNG"); // Trạng thái mặc định
        
        // 4. Lưu vào database thông qua Repository
        datPhong = datPhongRepository.save(datPhong);
        
        // 5. Convert entity thành response DTO
        return convertToResponse(datPhong);
    }
    
    @Override
    public DatPhongResponse layChiTietDatPhong(Long id) {
        // Repository tìm kiếm trong database
        DatPhong datPhong = datPhongRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đặt phòng"));
        
        // Convert entity sang response DTO
        return convertToResponse(datPhong);
    }
    
    @Override
    public List<DatPhongResponse> layTatCaDatPhong() {
        // Repository lấy tất cả từ database
        List<DatPhong> datPhongList = datPhongRepository.findAll();
        
        // Convert list entity sang list response DTO
        return datPhongList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public DatPhongResponse capNhatDatPhong(Long id, DatPhongRequest request) {
        // 1. Tìm đặt phòng cũ
        DatPhong datPhong = datPhongRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đặt phòng"));
        
        // 2. Cập nhật thông tin
        datPhong.setThoiGianRaDuKien(request.getThoiGianRaDuKien());
        
        // 3. Lưu lại
        datPhong = datPhongRepository.save(datPhong);
        
        // 4. Convert sang response
        return convertToResponse(datPhong);
    }
    
    @Override
    public void xoaDatPhong(Long id) {
        // Kiểm tra tồn tại
        if (!datPhongRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy đặt phòng");
        }
        // Xóa khỏi database
        datPhongRepository.deleteById(id);
    }
    
    @Override
    public DatPhongResponse checkIn(Long datPhongId) {
        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đặt phòng"));
        
        // Cập nhật trạng thái và thời gian
        datPhong.setTrangThai("DANG_SU_DUNG");
        datPhong.setThoiGianVao(LocalDateTime.now());
        
        datPhong = datPhongRepository.save(datPhong);
        return convertToResponse(datPhong);
    }
    
    @Override
    public DatPhongResponse checkOut(Long datPhongId) {
        DatPhong datPhong = datPhongRepository.findById(datPhongId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đặt phòng"));
        
        // Tính tiền
        LocalDateTime thoiGianRa = LocalDateTime.now();
        datPhong.setThoiGianRaThucTe(thoiGianRa);
        datPhong.setTrangThai("DA_THANH_TOAN");
        
        // Tính tổng tiền dựa trên giờ sử dụng
        long gioSuDung = java.time.temporal.ChronoUnit.HOURS
                .between(datPhong.getThoiGianVao(), thoiGianRa);
        double tongTien = gioSuDung * datPhong.getPhong().getGiaGio();
        datPhong.setTongTien(tongTien);
        
        datPhong = datPhongRepository.save(datPhong);
        return convertToResponse(datPhong);
    }
    
    /**
     * Hàm helper: Convert Entity → Response DTO
     * Lấy dữ liệu từ Entity và tạo Response DTO
     */
    private DatPhongResponse convertToResponse(DatPhong datPhong) {
        DatPhongResponse response = new DatPhongResponse();
        response.setId(datPhong.getId());
        response.setKhachHangId(datPhong.getKhachHang().getMaKH());
        response.setPhongId(datPhong.getPhong().getId());
        response.setThoiGianVao(datPhong.getThoiGianVao());
        response.setThoiGianRaDuKien(datPhong.getThoiGianRaDuKien());
        response.setThoiGianRaThucTe(datPhong.getThoiGianRaThucTe());
        response.setTongTien(datPhong.getTongTien());
        response.setTrangThai(datPhong.getTrangThai());
        return response;
    }
}
