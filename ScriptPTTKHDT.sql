
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

-- ============= CREATE INDEXES =============
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
CREATE INDEX idx_taikhoan_tendangnhap ON TaiKhoan(TenDangNhap);
CREATE INDEX idx_taikhoan_loai ON TaiKhoan(LoaiTaiKhoan);
CREATE INDEX idx_taikhoan_makhachhang ON TaiKhoan(MaKhachHang);
CREATE INDEX idx_taikhoan_manhanvien ON TaiKhoan(MaNhanVien);
CREATE INDEX idx_taikhoan_trangthai ON TaiKhoan(TrangThai);

-- ============= DELETE OLD DATA (Clean slate) =============
-- Disable safe update mode to allow DELETE without WHERE clause
SET SQL_SAFE_UPDATES = 0;

DELETE FROM ChiTietKiemKe;
DELETE FROM ChiTietPhieuXuat;
DELETE FROM ChiTietPhieuNhap;
DELETE FROM ChiTietTiec;
DELETE FROM ChiTietGoiMon;
DELETE FROM ChiTietGoiTiec;
DELETE FROM BangLuong;
DELETE FROM BangChamCong;
DELETE FROM PhieuKiemKe;
DELETE FROM PhieuXuat;
DELETE FROM PhieuNhap;
DELETE FROM HoaDon;
DELETE FROM DonGoiMon;
DELETE FROM PhieuSuDung;
DELETE FROM DonDatTiec;
DELETE FROM PhieuDatPhong;
DELETE FROM CauHinhGia;
DELETE FROM Phong;
DELETE FROM TaiKhoan;
DELETE FROM NhanVien;
DELETE FROM TheThanhVien;
DELETE FROM KhachHang;
DELETE FROM GoiTiec;
DELETE FROM DoiTac;
DELETE FROM MatHang;
DELETE FROM CaLamViec;
DELETE FROM NhaCungCap;
DELETE FROM LoaiPhong;
DELETE FROM CoSo;

-- Re-enable safe update mode
SET SQL_SAFE_UPDATES = 1;

-- Reset AUTO_INCREMENT
ALTER TABLE CoSo AUTO_INCREMENT = 1;
ALTER TABLE LoaiPhong AUTO_INCREMENT = 1;
ALTER TABLE CaLamViec AUTO_INCREMENT = 1;
ALTER TABLE NhaCungCap AUTO_INCREMENT = 1;
ALTER TABLE MatHang AUTO_INCREMENT = 1;
ALTER TABLE DoiTac AUTO_INCREMENT = 1;
ALTER TABLE GoiTiec AUTO_INCREMENT = 1;
ALTER TABLE KhachHang AUTO_INCREMENT = 1;
ALTER TABLE TheThanhVien AUTO_INCREMENT = 1;
ALTER TABLE NhanVien AUTO_INCREMENT = 1;
ALTER TABLE Phong AUTO_INCREMENT = 1;
ALTER TABLE CauHinhGia AUTO_INCREMENT = 1;
ALTER TABLE PhieuDatPhong AUTO_INCREMENT = 1;
ALTER TABLE DonDatTiec AUTO_INCREMENT = 1;
ALTER TABLE PhieuSuDung AUTO_INCREMENT = 1;
ALTER TABLE DonGoiMon AUTO_INCREMENT = 1;
ALTER TABLE HoaDon AUTO_INCREMENT = 1;
ALTER TABLE PhieuNhap AUTO_INCREMENT = 1;
ALTER TABLE PhieuXuat AUTO_INCREMENT = 1;
ALTER TABLE PhieuKiemKe AUTO_INCREMENT = 1;
ALTER TABLE BangChamCong AUTO_INCREMENT = 1;
ALTER TABLE BangLuong AUTO_INCREMENT = 1;
ALTER TABLE TaiKhoan AUTO_INCREMENT = 1;

-- ============= INSERT DEFAULT DATA =============
-- 1. Insert CoSo (Branch)
INSERT INTO CoSo (TenCS, DiaChi, SDT) VALUES
('Chi Nhánh Trung Tâm', '123 Đường Âm Nhạc, Quận 1, TP.HCM', '0123456789'),
('Chi Nhánh Phía Nam', '456 Đường Sơn Ca, Quận 3, TP.HCM', '0987654321');

-- 2. Insert LoaiPhong (Room Types)
INSERT INTO LoaiPhong (TenLoai, SucChua, GiaTheoGio, MoTa) VALUES
('VIP', 10, 300000, 'Phòng VIP cao cấp'),
('Thường', 8, 200000, 'Phòng thường'),
('Nhỏ', 4, 100000, 'Phòng nhỏ');

-- 3. Insert Phong (Rooms)
INSERT INTO Phong (TenPhong, TrangThai, MaCS, MaLoai, Tang) VALUES
('P101', 'Trong', 1, 1, 1),
('P102', 'Trong', 1, 2, 1),
('P103', 'Trong', 1, 3, 1),
('P104', 'Trong', 1, 1, 1),
('P105', 'Trong', 1, 2, 1),
('P106', 'Trong', 1, 3, 1),
('P201', 'Trong', 1, 1, 2),
('P202', 'Trong', 1, 2, 2),
('P203', 'Trong', 1, 3, 2),
('P204', 'Trong', 1, 1, 2),
('P205', 'Trong', 1, 2, 2),
('P206', 'Trong', 1, 3, 2),
('P301', 'Trong', 1, 1, 3),
('P302', 'Trong', 1, 2, 3),
('P303', 'Trong', 1, 3, 3),
('P304', 'Trong', 1, 1, 3),
('P305', 'Trong', 1, 2, 3),
('P306', 'Trong', 1, 3, 3);

-- 4. Insert CaLamViec (Work Shifts)
INSERT INTO CaLamViec (TenCa, GioBatDau, GioKetThuc) VALUES
('Sáng', '08:00:00', '16:00:00'),
('Chiều', '16:00:00', '00:00:00'),
('Đêm', '00:00:00', '08:00:00');

-- 5. Insert NhanVien (Staff)
INSERT INTO NhanVien (HoTen, ChucVu, SDT, Email, DiaChi, NgayVaoLam, HeSoLuong, TrangThai, MaCS) VALUES
('Quản Trị Viên', 'Quản Trị Hệ Thống', '0901111111', 'admin@karaoke.com', 'Hệ Thống', CURRENT_DATE, 1.5, 'Dang lam viec', 1),
('Trần Quốc A', 'Tiếp Tân', '0912345678', 'staff1@karaoke.com', 'TP.HCM', CURRENT_DATE, 1.0, 'Dang lam viec', 1),
('Phạm Thị B', 'Thu Ngân', '0912345679', 'staff2@karaoke.com', 'TP.HCM', CURRENT_DATE, 1.0, 'Dang lam viec', 1),
('Lê Văn C', 'Phục Vụ', '0912345680', 'staff3@karaoke.com', 'TP.HCM', CURRENT_DATE, 0.8, 'Dang lam viec', 1),
('Nguyễn Thị D', 'Phục Vụ', '0912345681', 'staff4@karaoke.com', 'TP.HCM', CURRENT_DATE, 0.8, 'Dang lam viec', 1);

-- 6. Insert KhachHang (Customers)
INSERT INTO KhachHang (TenKH, SDT, Email, CMND, LoaiKhach) VALUES
('Nguyễn Văn A', '0901234567', 'a@example.com', '123456789', 'VIP'),
('Trần Thị B', '0901234568', 'b@example.com', '987654321', 'Thuong'),
('Phạm Văn C', '0901234569', 'c@example.com', '555666777', 'Thuong'),
('Lê Thị D', '0901234570', 'd@example.com', '222333444', 'Thuong'),
('Võ Văn E', '0901234571', 'e@example.com', '777888999', 'Thuong');

-- 7. Insert TheThanhVien (Loyalty Cards)
INSERT INTO TheThanhVien (MaKH, HangThe, DiemTichLuy, NgayCap) VALUES
(1, 'VIP', 1000, CURRENT_DATE),
(2, 'Dong', 500, CURRENT_DATE),
(3, 'Dong', 300, CURRENT_DATE),
(4, 'Dong', 200, CURRENT_DATE),
(5, 'Dong', 100, CURRENT_DATE);

-- 8. Insert MatHang (Menu Items)
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa, TrangThai) VALUES
('Bia Heineken', 'Đồ Uống', 100, 'Lon', 15000, 35000, 'Bia Heineken 330ml', 'Con hang'),
('Nước Ngọt Coca', 'Đồ Uống', 50, 'Chai', 8000, 20000, 'Coca Cola 1.5L', 'Con hang'),
('Mực Chiên', 'Thức Ăn', 30, 'Suất', 40000, 80000, 'Mực chiên giòn', 'Con hang'),
('Gà Quay', 'Thức Ăn', 25, 'Suất', 60000, 120000, 'Gà quay vàng ươm', 'Con hang'),
('Bánh Mì', 'Thức Ăn', 40, 'Cái', 5000, 15000, 'Bánh mì nóng', 'Con hang'),
('Nước Cam', 'Đồ Uống', 20, 'Cốc', 5000, 25000, 'Nước cam ép tươi', 'Con hang');

-- 9. Insert GoiTiec (Party Packages)
INSERT INTO GoiTiec (TenGoi, MoTa, SoLuongBanToiThieu, GiaTronGoi) VALUES
('Gói Cơ Bản', 'Gồm 2 phòng, 20 người, 3 giờ, hợp đồng đơn giản', 20, 3000000),
('Gói Tiêu Chuẩn', 'Gồm 3 phòng VIP, 50 người, 4 giờ, DJ, ánh sáng', 50, 8000000),
('Gói Cao Cấp', 'Gồm 5 phòng VIP, 100 người, 5 giờ, DJ, ban nhạc sống', 100, 15000000);

-- 10. Insert DoiTac (Partners)
INSERT INTO DoiTac (TenDT, SDT, TyLeHoaHong) VALUES
('Công Ty A', '0111111111', 0.05),
('Công Ty B', '0222222222', 0.07),
('Công Ty C', '0333333333', 0.10);

-- 11. Insert TaiKhoan (Accounts) - Password: admin123 (BCrypt hash)
INSERT INTO TaiKhoan (TenDangNhap, MatKhauHash, LoaiTaiKhoan, MaNhanVien, MaKhachHang, TrangThai) VALUES
('admin', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 1, NULL, 'Hoat dong'),
('tieptan', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 2, NULL, 'Hoat dong'),
('staff2', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 3, NULL, 'Hoat dong'),
('khach1', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'KHACH_HANG', NULL, 4, 'Hoat dong'),
('khach2', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'KHACH_HANG', NULL, 5, 'Hoat dong');

-- 12. Insert PhieuDatPhong (Booking Records)
INSERT INTO PhieuDatPhong (MaKH, MaPhong, MaDT, NgayDat, GioDat, GioKetThuc, SoNguoi, TienCoc, TrangThai) VALUES
(1, 1, NULL, NOW(), '2025-12-01 10:00:00', '2025-12-01 12:00:00', 8, 100000, 'Da dat'),
(2, 2, NULL, NOW(), '2025-12-05 14:00:00', '2025-12-05 16:30:00', 6, 80000, 'Da dat'),
(3, 3, NULL, NOW(), '2025-12-10 18:00:00', '2025-12-10 21:00:00', 4, 50000, 'Da dat'),
(4, 4, NULL, NOW(), '2025-12-15 11:00:00', '2025-12-15 13:00:00', 10, 120000, 'Da dat'),
(5, 5, NULL, NOW(), '2025-12-20 19:00:00', '2025-12-20 22:00:00', 8, 100000, 'Da dat');

-- 13. Insert PhieuSuDung (Service Usage)
INSERT INTO PhieuSuDung (MaPhieuSuDung, MaPhong, MaPhieuDat, MaNV, GioBatDau, GioKetThuc, TrangThai) VALUES
(1, 1, 1, 2, '2025-12-01 10:00:00', '2025-12-01 12:00:00', 'Ket thuc'),
(2, 2, 2, 2, '2025-12-05 14:00:00', '2025-12-05 16:30:00', 'Ket thuc'),
(3, 3, 3, 3, '2025-12-10 18:00:00', '2025-12-10 21:00:00', 'Ket thuc'),
(4, 4, 4, 2, '2025-12-15 11:00:00', '2025-12-15 13:00:00', 'Ket thuc'),
(5, 5, 5, 3, '2025-12-20 19:00:00', '2025-12-20 22:00:00', 'Ket thuc'),
(6, 1, NULL, 2, '2025-11-01 15:00:00', '2025-11-01 17:00:00', 'Ket thuc'),
(7, 2, NULL, 3, '2025-11-10 20:00:00', '2025-11-10 23:00:00', 'Ket thuc'),
(8, 3, NULL, 2, '2025-10-05 09:00:00', '2025-10-05 11:00:00', 'Ket thuc'),
(9, 4, NULL, 3, '2025-10-15 16:00:00', '2025-10-15 18:30:00', 'Ket thuc');

-- 14. Insert HoaDon (Invoices) - Monthly/Yearly Revenue Data for Testing
INSERT INTO HoaDon (MaPhieuSuDung, MaKH, NgayLap, TienPhong, TienDichVu, TongTienChuaThue, ThueVAT, GiamGia, TongTien, TienCocDaTra, ConPhaiTra, HinhThucThanhToan, TrangThai, MaNVThanhToan) VALUES
-- December 2025
(1, 1, '2025-12-01 12:30:00', 600000, 200000, 800000, 80000, 0, 880000, 880000, 0, 'Tien mat', 'Hoai thanh toan', 2),
(2, 2, '2025-12-05 16:45:00', 500000, 150000, 650000, 65000, 0, 715000, 715000, 0, 'The', 'Hoai thanh toan', 2),
(3, 3, '2025-12-10 21:15:00', 800000, 300000, 1100000, 110000, 0, 1210000, 1210000, 0, 'Tien mat', 'Hoai thanh toan', 3),
(4, 4, '2025-12-15 13:20:00', 550000, 180000, 730000, 73000, 0, 803000, 803000, 0, 'Tien mat', 'Hoai thanh toan', 2),
(5, 5, '2025-12-20 22:30:00', 700000, 250000, 950000, 95000, 0, 1045000, 1045000, 0, 'The', 'Hoai thanh toan', 3),
-- November 2025
(6, 1, '2025-11-01 17:15:00', 500000, 160000, 660000, 66000, 0, 726000, 726000, 0, 'Tien mat', 'Hoai thanh toan', 2),
(7, 2, '2025-11-10 23:30:00', 750000, 280000, 1030000, 103000, 0, 1133000, 1133000, 0, 'The', 'Hoai thanh toan', 3),
-- October 2025
(8, 3, '2025-10-05 11:20:00', 600000, 200000, 800000, 80000, 0, 880000, 880000, 0, 'Tien mat', 'Hoai thanh toan', 2),
(9, 4, '2025-10-15 18:45:00', 650000, 150000, 800000, 80000, 0, 880000, 880000, 0, 'The', 'Hoai thanh toan', 3);
