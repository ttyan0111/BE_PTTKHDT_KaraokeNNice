package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity đại diện cho Loại Phòng
 */
@Entity
@Table(name = "LoaiPhong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiPhong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLoai")
    private Integer maLoai;
    
    @Column(name = "TenLoai", length = 50)
    private String tenLoai;
    
    @Column(name = "SucChua")
    private Integer sucChua;
    
    @Column(name = "GiaTheoGio")
    private Long giaTheoGio;
    
    @Column(name = "MoTa", length = 255)
    private String moTa;
}
