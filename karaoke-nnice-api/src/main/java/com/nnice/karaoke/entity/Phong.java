package com.nnice.karaoke.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

/**
 * Entity đại diện cho Phòng
 */
@Entity
@Table(name = "Phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhong")
    private Integer maPhong;
    
    @Column(name = "TenPhong", length = 50, nullable = false)
    private String tenPhong;
    
    @Column(name = "TrangThai", length = 50, nullable = false)
    private String trangThai; // "Trong", "Dang Su Dung", "Bao Tri"
    
    @Column(name = "MaCS")
    private Integer maCS;
    
    @Column(name = "MaLoai")
    private Integer maLoai;
    
    @Column(name = "Tang")
    private Integer tang; // Tầng (1, 2, 3, ...)
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaCS", referencedColumnName = "MaCS", insertable = false, updatable = false)
    @JsonIgnore
    private CoSo coSo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLoai", referencedColumnName = "MaLoai", insertable = false, updatable = false)
    @JsonIgnore
    private LoaiPhong loaiPhong;
    
    @OneToMany(mappedBy = "phong", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PhieuSuDung> phieuSuDungList;
}
