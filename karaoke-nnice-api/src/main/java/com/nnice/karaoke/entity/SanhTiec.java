package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity đại diện cho Sảnh Tiệc
 */
@Entity
@Table(name = "SanhTiec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanhTiec {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSanh")
    private Integer maSanh;
    
    @Column(name = "TenSanh", length = 100, nullable = false)
    private String tenSanh;
    
    @Column(name = "SucChua")
    private Integer sucChua; // Số người tối đa
    
    @Column(name = "DienTich")
    private Double dienTich; // m2
    
    @Column(name = "GiaThue")
    private Long giaThue; // Giá thuê cơ bản
    
    @Column(name = "TrangThai", length = 50)
    private String trangThai; // TRONG, DANG_SU_DUNG, BAO_TRI
    
    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;
}
