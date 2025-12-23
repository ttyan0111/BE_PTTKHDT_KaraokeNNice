
CREATE DATABASE IF NOT EXISTS KaraokeNiceDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE KaraokeNiceDB;


CREATE TABLE CoSo (
    MaCS INT AUTO_INCREMENT PRIMARY KEY,
    TenCS VARCHAR(100) NOT NULL,
    DiaChi VARCHAR(255),
    SDT VARCHAR(15)
);

CREATE TABLE LoaiPhong (
    MaLoai INT AUTO_INCREMENT PRIMARY KEY,
    TenLoai VARCHAR(50),
    SucChua INT,
    GiaTheoGio DECIMAL(18,0) NOT NULL,
    MoTa VARCHAR(255),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE CaLamViec (
    MaCa INT AUTO_INCREMENT PRIMARY KEY,
    TenCa VARCHAR(50),
    GioBatDau TIME,
    GioKetThuc TIME
);

CREATE TABLE NhaCungCap (
    MaNCC INT AUTO_INCREMENT PRIMARY KEY,
    TenNCC VARCHAR(100),
    DiaChi VARCHAR(255),
    SDT VARCHAR(15)
);

CREATE TABLE MatHang (
    MaHang INT AUTO_INCREMENT PRIMARY KEY,
    TenHang VARCHAR(100),
    LoaiHang VARCHAR(50),
    SoLuongTon INT DEFAULT 0,
    DonViTinh VARCHAR(20),
    GiaNhap DECIMAL(18,0),
    GiaBan DECIMAL(18,0),
    MoTa TEXT,
    HinhAnh VARCHAR(255),
    TrangThai VARCHAR(50) DEFAULT 'Con hang',
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE DoiTac (
    MaDT INT AUTO_INCREMENT PRIMARY KEY,
    TenDT VARCHAR(100),
    SDT VARCHAR(15),
    TyLeHoaHong FLOAT
);

CREATE TABLE GoiTiec (
    MaGoi INT AUTO_INCREMENT PRIMARY KEY,
    TenGoi VARCHAR(100),
    MoTa TEXT,
    SoLuongBanToiThieu INT DEFAULT 10,
    GiaTronGoi DECIMAL(18,0),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE KhachHang (
    MaKH INT AUTO_INCREMENT PRIMARY KEY,
    TenKH VARCHAR(100) NOT NULL,
    SDT VARCHAR(15) UNIQUE, 
    Email VARCHAR(100),
    DiaChi VARCHAR(255),
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    CMND VARCHAR(20),
    LoaiKhach VARCHAR(50) DEFAULT 'Thuong',
    NgayDangKy DATE DEFAULT (CURRENT_DATE),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE TheThanhVien (
    MaThe INT AUTO_INCREMENT PRIMARY KEY,
    MaKH INT NOT NULL UNIQUE, 
    HangThe VARCHAR(50), 
    DiemTichLuy INT DEFAULT 0,
    NgayCap DATE DEFAULT (CURRENT_DATE), 
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);

CREATE TABLE NhanVien (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,
    HoTen VARCHAR(100),
    ChucVu VARCHAR(50),
    SDT VARCHAR(15),
    Email VARCHAR(100),
    DiaChi VARCHAR(255),
    NgaySinh DATE,
    CMND VARCHAR(20),
    NgayVaoLam DATE DEFAULT (CURRENT_DATE),
    HeSoLuong FLOAT,
    TyLeThuongDoanhThu FLOAT,
    TrangThai VARCHAR(50) DEFAULT 'Dang lam viec',
    MaCS INT,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaCS) REFERENCES CoSo(MaCS)
);

CREATE TABLE Phong (
    MaPhong INT AUTO_INCREMENT PRIMARY KEY,
    TenPhong VARCHAR(50),
    TrangThai VARCHAR(50),
    MaCS INT,
    MaLoai INT,
    Tang INT DEFAULT 1,
    FOREIGN KEY (MaCS) REFERENCES CoSo(MaCS),
    FOREIGN KEY (MaLoai) REFERENCES LoaiPhong(MaLoai)
);

CREATE TABLE CauHinhGia (
    MaCauHinh INT AUTO_INCREMENT PRIMARY KEY,
    MaLoai INT,
    KhungGio VARCHAR(50), 
    LoaiNgay VARCHAR(50),
    DonGia DECIMAL(18,0),
    ApDungTuNgay DATE,
    ApDungDenNgay DATE,
    TrangThai VARCHAR(50) DEFAULT 'Dang ap dung',
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaLoai) REFERENCES LoaiPhong(MaLoai)
);



CREATE TABLE PhieuDatPhong (
    MaPhieuDat INT AUTO_INCREMENT PRIMARY KEY,
    MaKH INT,
    MaPhong INT,
    MaDT INT NULL,
    NgayDat DATETIME DEFAULT CURRENT_TIMESTAMP,
    GioDat DATETIME,
    GioKetThuc DATETIME,
    SoNguoi INT NOT NULL,
    TienCoc DECIMAL(18,0) DEFAULT 0,
    TrangThai VARCHAR(50) DEFAULT 'Da dat',
    GhiChu VARCHAR(500),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong),
    FOREIGN KEY (MaDT) REFERENCES DoiTac(MaDT)
);

CREATE TABLE DonDatTiec (
    MaDonDatTiec INT AUTO_INCREMENT PRIMARY KEY,
    MaKH INT,
    MaGoi INT NULL,
    NgayToChuc DATETIME,
    SoLuongNguoi INT,
    TongTien DECIMAL(18,0),
    TienCoc DECIMAL(18,0),
    TrangThai VARCHAR(50),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaGoi) REFERENCES GoiTiec(MaGoi)
);

CREATE TABLE PhieuSuDung (
    MaPhieuSuDung INT AUTO_INCREMENT PRIMARY KEY,
    MaPhong INT,
    MaPhieuDat INT NULL, 
    MaNV INT, 
    GioBatDau DATETIME DEFAULT CURRENT_TIMESTAMP,
    GioKetThuc DATETIME,
    TongThoiGian FLOAT,
    TrangThai VARCHAR(50),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong),
    FOREIGN KEY (MaPhieuDat) REFERENCES PhieuDatPhong(MaPhieuDat),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE DonGoiMon (
    MaOrder INT AUTO_INCREMENT PRIMARY KEY,
    MaPhieuSuDung INT,
    ThoiGianGoi DATETIME DEFAULT CURRENT_TIMESTAMP,
    TrangThai VARCHAR(50),
    FOREIGN KEY (MaPhieuSuDung) REFERENCES PhieuSuDung(MaPhieuSuDung)
);

CREATE TABLE HoaDon (
    MaHD INT AUTO_INCREMENT PRIMARY KEY,
    MaPhieuSuDung INT NOT NULL UNIQUE,
    MaKH INT,
    NgayLap DATETIME DEFAULT CURRENT_TIMESTAMP,
    TienPhong DECIMAL(18,0) DEFAULT 0,
    TienDichVu DECIMAL(18,0) DEFAULT 0,
    TongTienChuaThue DECIMAL(18,0) DEFAULT 0,
    ThueVAT DECIMAL(18,0) DEFAULT 0,
    GiamGia DECIMAL(18,0) DEFAULT 0,
    TongTien DECIMAL(18,0) DEFAULT 0,
    TienCocDaTra DECIMAL(18,0) DEFAULT 0,
    ConPhaiTra DECIMAL(18,0) DEFAULT 0,
    HinhThucThanhToan VARCHAR(50),
    TrangThai VARCHAR(50) DEFAULT 'Chua thanh toan',
    MaNVThanhToan INT,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaPhieuSuDung) REFERENCES PhieuSuDung(MaPhieuSuDung),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNVThanhToan) REFERENCES NhanVien(MaNV)
);


CREATE TABLE PhieuNhap (
    MaPhieuNhap INT AUTO_INCREMENT PRIMARY KEY,
    MaNCC INT,
    MaNV INT,
    NgayNhap DATETIME DEFAULT CURRENT_TIMESTAMP,
    TongTienNhap DECIMAL(18,0),
    NguoiGiaoHang VARCHAR(100),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE PhieuXuat (
    MaPhieuXuat INT AUTO_INCREMENT PRIMARY KEY,
    MaNV INT,
    NgayXuat DATETIME DEFAULT CURRENT_TIMESTAMP,
    LyDoXuat VARCHAR(255),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE PhieuKiemKe (
    MaKiemKe INT AUTO_INCREMENT PRIMARY KEY,
    MaNV INT,
    NgayKiem DATETIME DEFAULT CURRENT_TIMESTAMP,
    KyKiem VARCHAR(50),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE BangChamCong (
    MaChamCong INT AUTO_INCREMENT PRIMARY KEY,
    MaNV INT,
    MaCa INT,
    NgayLam DATE,
    GioCheckIn DATETIME,
    GioCheckOut DATETIME,
    TrangThai VARCHAR(50),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaCa) REFERENCES CaLamViec(MaCa)
);

CREATE TABLE BangLuong (
    MaLuong INT AUTO_INCREMENT PRIMARY KEY,
    MaNV INT,
    Thang INT,
    Nam INT,
    TongLuongNhan DECIMAL(18,0),
    ChiTietCacKhoan TEXT, -- MySQL dùng TEXT thay cho NVARCHAR(MAX)
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);



CREATE TABLE ChiTietGoiMon (
    MaOrder INT,
    MaHang INT,
    SoLuong INT,
    DonGia DECIMAL(18,0),
    GhiChu VARCHAR(255),
    PRIMARY KEY (MaOrder, MaHang),
    FOREIGN KEY (MaOrder) REFERENCES DonGoiMon(MaOrder),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

CREATE TABLE ChiTietTiec (
    MaDonDatTiec INT,
    MaHang INT,
    SoLuong INT,
    GhiChu VARCHAR(255),
    PRIMARY KEY (MaDonDatTiec, MaHang),
    FOREIGN KEY (MaDonDatTiec) REFERENCES DonDatTiec(MaDonDatTiec),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

CREATE TABLE ChiTietPhieuNhap (
    MaPhieuNhap INT,
    MaHang INT,
    SoLuongNhap INT,
    DonGiaNhap DECIMAL(18,0),
    HanSuDung DATE,
    PRIMARY KEY (MaPhieuNhap, MaHang),
    FOREIGN KEY (MaPhieuNhap) REFERENCES PhieuNhap(MaPhieuNhap),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

CREATE TABLE ChiTietPhieuXuat (
    MaPhieuXuat INT,
    MaHang INT,
    SoLuongXuat INT,
    PRIMARY KEY (MaPhieuXuat, MaHang),
    FOREIGN KEY (MaPhieuXuat) REFERENCES PhieuXuat(MaPhieuXuat),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

CREATE TABLE ChiTietKiemKe (
    MaKiemKe INT,
    MaHang INT,
    SoLuongSoSach INT,
    SoLuongThucTe INT,
    LyDoChenhLech VARCHAR(255),
    PRIMARY KEY (MaKiemKe, MaHang),
    FOREIGN KEY (MaKiemKe) REFERENCES PhieuKiemKe(MaKiemKe),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

-- Thêm bảng ChiTietGoiTiec (đang thiếu)
CREATE TABLE ChiTietGoiTiec (
    MaGoi INT,
    MaHang INT,
    SoLuong INT NOT NULL,
    GhiChu VARCHAR(255),
    PRIMARY KEY (MaGoi, MaHang),
    FOREIGN KEY (MaGoi) REFERENCES GoiTiec(MaGoi),
    FOREIGN KEY (MaHang) REFERENCES MatHang(MaHang)
);

-- Indexes để tăng performance
CREATE INDEX idx_phong_trangthai ON Phong(TrangThai);
CREATE INDEX idx_phong_coso ON Phong(MaCS);
CREATE INDEX idx_phong_loai ON Phong(MaLoai);
CREATE INDEX idx_nhanvien_coso ON NhanVien(MaCS);
CREATE INDEX idx_nhanvien_trangthai ON NhanVien(TrangThai);
CREATE INDEX idx_phieudatphong_trangthai ON PhieuDatPhong(TrangThai);
CREATE INDEX idx_phieudatphong_ngaydat ON PhieuDatPhong(NgayDat);
CREATE INDEX idx_phieudatphong_phong ON PhieuDatPhong(MaPhong);
CREATE INDEX idx_khachhang_sdt ON KhachHang(SDT);
CREATE INDEX idx_phieusudung_trangthai ON PhieuSuDung(TrangThai);
CREATE INDEX idx_phieusudung_phong ON PhieuSuDung(MaPhong);
CREATE INDEX idx_dongoimon_phieusudung ON DonGoiMon(MaPhieuSuDung);
CREATE INDEX idx_hoadon_trangthai ON HoaDon(TrangThai);
CREATE INDEX idx_mathang_loai ON MatHang(LoaiHang);
CREATE INDEX idx_mathang_trangthai ON MatHang(TrangThai);
CREATE INDEX idx_bangchamcong_nhanvien ON BangChamCong(MaNV);
CREATE INDEX idx_bangchamcong_ngaylam ON BangChamCong(NgayLam);
CREATE INDEX idx_bangluong_nhanvien ON BangLuong(MaNV);
CREATE INDEX idx_bangluong_thangnam ON BangLuong(Thang, Nam);

ALTER TABLE KhachHang DROP COLUMN DiaChi, DROP COLUMN NgaySinh, DROP COLUMN GioiTinh;

-- ============= TÀI KHOẢN (CREDENTIALS) =============
-- Bảng lưu thông tin đăng nhập của KhachHang và NhanVien
-- Flow: Tạo KhachHang -> Tạo TaiKhoan (MaKhachHang liên kết)
CREATE TABLE TaiKhoan (
    MaTaiKhoan INT AUTO_INCREMENT PRIMARY KEY,
    TenDangNhap VARCHAR(50) UNIQUE NOT NULL,
    MatKhauHash VARCHAR(255) NOT NULL,                -- Password đã hash (BCrypt)
    LoaiTaiKhoan VARCHAR(20) DEFAULT 'KHACH_HANG',   -- Mặc định KHACH_HANG, admin update sau nếu cần
    MaKhachHang INT NULL,                            -- NULL nếu là NHAN_VIEN
    MaNhanVien INT NULL,                             -- NULL nếu là KHACH_HANG
    TrangThai VARCHAR(50) DEFAULT 'Hoat dong',       -- Hoat dong / Bi khoa
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP,
    NgayCapNhat DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKH) ON DELETE CASCADE,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNV) ON DELETE CASCADE,
    CONSTRAINT chk_loai CHECK (LoaiTaiKhoan IN ('KHACH_HANG', 'NHAN_VIEN')),
    CONSTRAINT chk_taikhoan_link CHECK (
        (LoaiTaiKhoan = 'KHACH_HANG' AND MaKhachHang IS NOT NULL AND MaNhanVien IS NULL) OR
        (LoaiTaiKhoan = 'NHAN_VIEN' AND MaNhanVien IS NOT NULL AND MaKhachHang IS NULL)
    )
);

-- Index để tăng tốc độ tìm kiếm
CREATE INDEX idx_taikhoan_tendangnhap ON TaiKhoan(TenDangNhap);
CREATE INDEX idx_taikhoan_loai ON TaiKhoan(LoaiTaiKhoan);
CREATE INDEX idx_taikhoan_makhachhang ON TaiKhoan(MaKhachHang);
CREATE INDEX idx_taikhoan_manhanvien ON TaiKhoan(MaNhanVien);
CREATE INDEX idx_taikhoan_trangthai ON TaiKhoan(TrangThai);

-- ============= INSERT DEFAULT DATA =============
-- Insert default CoSo (Branch)
INSERT INTO CoSo (TenCS, DiaChi, SDT) VALUES
('Chi Nhánh Trung Tâm', '123 Đường Âm Nhạc, Quận 1, TP.HCM', '0123456789');

-- Insert default NhanVien (Admin)
INSERT INTO NhanVien (HoTen, ChucVu, SDT, Email, DiaChi, NgayVaoLam, HeSoLuong, TrangThai, MaCS) VALUES
('Quản Trị Viên', 'Quản Trị Hệ Thống', '0123456789', 'admin@karaoke.com', 'Hệ Thống', CURRENT_DATE, 1.5, 'Dang lam viec', 1);

-- Insert default TaiKhoan for Admin (Password: admin123 - BCrypt hash)
-- Hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2
INSERT INTO TaiKhoan (TenDangNhap, MatKhauHash, LoaiTaiKhoan, MaNhanVien, TrangThai) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2', 'NHAN_VIEN', 1, 'Hoat dong');


-- ============= MIGRATION: Add Tang & ViTri columns if they don't exist =============
ALTER TABLE Phong ADD COLUMN Tang INT DEFAULT 1 AFTER MaLoai;


