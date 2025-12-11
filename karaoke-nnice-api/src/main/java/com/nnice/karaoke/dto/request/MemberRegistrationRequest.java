package com.nnice.karaoke.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegistrationRequest {
    private String tenKH;
    private String sdt;
    private String email;
    private String diaChi;
    private String cmnd;
    private String ngaySinh; // Format: YYYY-MM-DD
    private String gioiTinh;
}
