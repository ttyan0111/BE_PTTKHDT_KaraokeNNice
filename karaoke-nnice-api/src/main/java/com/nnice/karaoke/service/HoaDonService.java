package com.nnice.karaoke.service;

import com.nnice.karaoke.dto.request.HoaDonRequest;
import com.nnice.karaoke.dto.response.HoaDonResponse;
import java.util.List;

public interface HoaDonService {
    /**
     * Lấy tất cả hóa đơn
     */
    List<HoaDonResponse> getAll();
    
    /**
     * Lấy hóa đơn theo ID
     */
    HoaDonResponse getById(Integer maHD);
    
    /**
     * Lấy hóa đơn theo trạng thái thanh toán
     */
    List<HoaDonResponse> getByTrangThai(String trangThai);
    
    /**
     * Tạo mới hóa đơn
     */
    HoaDonResponse create(HoaDonRequest request);
    
    /**
     * Cập nhật hóa đơn
     */
    HoaDonResponse update(HoaDonRequest request);
    
    /**
     * Xóa hóa đơn
     */
    boolean delete(Integer maHD);
}
