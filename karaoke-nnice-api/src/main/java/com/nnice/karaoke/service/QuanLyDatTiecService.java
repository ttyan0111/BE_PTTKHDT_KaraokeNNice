package com.nnice.karaoke.service;

import com.nnice.karaoke.dto.request.DatTiecRequest;
import com.nnice.karaoke.dto.response.DatTiecResponse;
import com.nnice.karaoke.dto.response.HoanCocResponse;
import com.nnice.karaoke.dto.response.SanhTiecResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface QuanLyDatTiecService {
    // Tạo đơn đặt tiệc mới
    DatTiecResponse taoDonDatTiec(DatTiecRequest donDatTiec);
    
    // Tính tổng chi phí dự kiến
    Long tinhChiPhiTiec(Integer maTiec);
    
    // Tính tiền đặt cọc (20%)
    Long tinhTienDatCoc(Long tongChiPhi);
    
    // Xử lý thanh toán cọc
    void xuLyThanhToanCoc(Integer maTiec, Long soTien, String hinhThuc);
    
    // Xem chi tiết đặt tiệc
    DatTiecResponse xemChiTiet(Integer maTiec);
    
    // Cập nhật đặt tiệc
    DatTiecResponse capNhatDatTiec(Integer maTiec, DatTiecRequest donDatTiec);
    
    // Hủy đặt tiệc (xử lý hoàn cọc theo quy định)
    void huyDatTiec(Integer maTiec, String lyDo);
    
    // Danh sách đặt tiệc
    List<DatTiecResponse> danhSachDatTiec(String trangThai);
    
    // Khóa phòng/sảnh tiệc
    void khoapHongTiec(Integer maTiec);
    
    // Gửi xác nhận
    void guiXacNhan(Integer maTiec);
    
    // Lấy danh sách sảnh trống
    List<SanhTiecResponse> layDanhSachSanhTrong(LocalDateTime tuNgay, LocalDateTime denNgay);
    
    // Tính tiền hoàn cọc
    HoanCocResponse tinhTienHoanCoc(Integer maTiec);
    
    // Kiểm tra sảnh có trống không
    boolean kiemTraSanhTrong(Integer maSanh, LocalDateTime ngayToChuc);
}
