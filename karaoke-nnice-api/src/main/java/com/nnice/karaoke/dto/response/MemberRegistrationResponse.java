package com.nnice.karaoke.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationResponse {
    private Integer maKH;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String cmndCccd;
    private String ngaySinh;
    private String gioiTinh;
}
