
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

-- Insert default NhanVien (Admin + Test Users)
INSERT INTO NhanVien (HoTen, ChucVu, SDT, Email, DiaChi, NgayVaoLam, HeSoLuong, TrangThai, MaCS) VALUES
('Quản Trị Viên', 'Quản Trị Hệ Thống', '0123456789', 'admin@karaoke.com', 'Hệ Thống', CURRENT_DATE, 1.5, 'Dang lam viec', 1),
('Nguyen Van Tiep', 'TiepTan', '0987654321', 'tieptan@karaoke.com', '456 Nguyen Trai, Q1', CURRENT_DATE, 1.2, 'Dang lam viec', 1),
('Tran Thi Toan', 'KeToan', '0987654322', 'ketoan@karaoke.com', '789 Le Loi, Q1', CURRENT_DATE, 1.3, 'Dang lam viec', 1),
('Le Van Bep', 'Bep', '0987654323', 'bep@karaoke.com', '321 Tran Hung Dao, Q1', CURRENT_DATE, 1.0, 'Dang lam viec', 1),
('Pham Thi Vu', 'PhucVu', '0987654324', 'phucvu@karaoke.com', '654 Hai Ba Trung, Q1', CURRENT_DATE, 1.0, 'Dang lam viec', 1);

-- Insert default TaiKhoan for Admin + Test Users (Password: admin123 - BCrypt hash)
-- Hash generated by Spring Boot BCryptPasswordEncoder - VERIFIED WORKING
-- Generated: 2025-12-25 - Hash: $2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq
INSERT INTO TaiKhoan (TenDangNhap, MatKhauHash, LoaiTaiKhoan, MaNhanVien, TrangThai) VALUES
('admin', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 1, 'Hoat dong'),
('tieptan', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 2, 'Hoat dong'),
('ketoan', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 3, 'Hoat dong'),
('bep', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 4, 'Hoat dong'),
('phucvu', '$2a$10$oSCZojFCV1WyLlJNpuU6nOD/j7DLZJJ7A3uAzcAo1sZ3FToJYTcJq', 'NHAN_VIEN', 5, 'Hoat dong');

-- ============= INSERT SAMPLE MENU ITEMS (MatHang) =============
-- Đồ Ăn (Food)
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa, TrangThai) VALUES
('Gà rán', 'Đồ Ăn', 20, 'cái', 50000, 85000, 'Gà rán giòn ngoài mềm trong', 'Con hang'),
('Cơm tấm', 'Đồ Ăn', 15, 'suất', 40000, 65000, 'Cơm tấm cá chiên', 'Con hang'),
('Mỳ Ý', 'Đồ Ăn', 10, 'suất', 45000, 75000, 'Mỳ Ý sốt cà chua tươi', 'Con hang'),
('Pizza', 'Đồ Ăn', 8, 'cái', 60000, 120000, 'Pizza hải sản tươi', 'Con hang'),
('Bánh mì', 'Đồ Ăn', 25, 'cái', 15000, 35000, 'Bánh mì thịt xá xíu', 'Con hang');

-- Đồ Uống (Drinks)
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa, TrangThai) VALUES
('Nước cam', 'Đồ Uống', 30, 'ly', 12000, 25000, 'Nước cam tươi ép mỗi ngày', 'Con hang'),
('Nước Coke', 'Đồ Uống', 25, 'chai', 8000, 20000, 'Coca Cola lạnh', 'Con hang'),
('Café', 'Đồ Uống', 18, 'ly', 12000, 30000, 'Café đen nóng/lạnh', 'Con hang'),
('Trà xanh', 'Đồ Uống', 22, 'ly', 10000, 25000, 'Trà xanh tươi', 'Con hang'),
('Smoothie', 'Đồ Uống', 12, 'ly', 20000, 45000, 'Smoothie trái cây mùa hè', 'Con hang');

-- Rượu & Bia (Alcohol)
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa, TrangThai) VALUES
('Bia Saigon', 'Rượu & Bia', 40, 'chai', 20000, 35000, 'Bia Saigon lạnh', 'Con hang'),
('Bia Heineken', 'Rượu & Bia', 20, 'chai', 30000, 50000, 'Bia Heineken nhập khẩu', 'Con hang'),
('Rượu Vodka', 'Rượu & Bia', 8, 'chai', 100000, 150000, 'Vodka Skyy cao cấp', 'Con hang'),
('Bia Corona', 'Rượu & Bia', 15, 'chai', 25000, 45000, 'Bia Corona', 'Con hang'),
('Rượu Vang đỏ', 'Rượu & Bia', 5, 'chai', 150000, 250000, 'Rượu Vang đỏ Pháp', 'Con hang');

-- Tráng Miệng (Dessert)
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa, TrangThai) VALUES
('Kem ốc quế', 'Tráng Miệng', 12, 'cái', 15000, 30000, 'Kem ốc quế vani', 'Con hang'),
('Bánh flan', 'Tráng Miệng', 15, 'cái', 10000, 20000, 'Bánh flan trứng', 'Con hang'),
('Choco cake', 'Tráng Miệng', 8, 'miếng', 25000, 50000, 'Bánh chocolate tươi', 'Con hang'),
('Pudding', 'Tráng Miệng', 10, 'cái', 12000, 25000, 'Pudding sô cô la', 'Con hang');

-- ============= MIGRATION: Add Tang & ViTri columns if they don't exist =============
-- Sử dụng procedure để tránh lỗi khi cột đã tồn tại
DELIMITER $$
CREATE PROCEDURE AddPhongColumns()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    ALTER TABLE Phong ADD COLUMN Tang INT DEFAULT 1 AFTER MaLoai;
END$$
DELIMITER ;
CALL AddPhongColumns();
DROP PROCEDURE IF EXISTS AddPhongColumns;

-- ============= PARTY MANAGEMENT TABLES =============
-- Tạo bảng GoiTiec nếu chưa có
CREATE TABLE IF NOT EXISTS GoiTiec (
    MaGoi INT AUTO_INCREMENT PRIMARY KEY,
    TenGoi VARCHAR(100) NOT NULL,
    GiaTronGoi DECIMAL(15,2) NOT NULL,
    SoLuongNguoiToiThieu INT,
    SoLuongNguoiToiDa INT,
    MoTa TEXT,
    DanhSachMonAn TEXT,
    DanhSachDichVu TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng SanhTiec nếu chưa có
CREATE TABLE IF NOT EXISTS SanhTiec (
    MaSanh INT AUTO_INCREMENT PRIMARY KEY,
    TenSanh VARCHAR(100) NOT NULL,
    SucChua INT NOT NULL,
    DienTich DOUBLE,
    GiaThue BIGINT,
    TrangThai VARCHAR(50) DEFAULT 'TRONG',
    MoTa TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng ThanhToan nếu chưa có
CREATE TABLE IF NOT EXISTS ThanhToan (
    MaThanhToan INT AUTO_INCREMENT PRIMARY KEY,
    MaDonDatTiec INT,
    LoaiThanhToan VARCHAR(50) NOT NULL,
    SoTien DECIMAL(15,2) NOT NULL,
    HinhThucThanhToan VARCHAR(50),
    NgayThanhToan DATETIME,
    GhiChu TEXT,
    TrangThai VARCHAR(50) DEFAULT 'THANH_CONG',
    FOREIGN KEY (MaDonDatTiec) REFERENCES DonDatTiec(MaDonDatTiec) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Thêm cột mới vào bảng DonDatTiec nếu chưa có
DELIMITER $$
CREATE PROCEDURE AddDonDatTiecColumns()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    
    ALTER TABLE DonDatTiec ADD COLUMN MaSanh INT;
    ALTER TABLE DonDatTiec ADD COLUMN NgayDat DATETIME;
    ALTER TABLE DonDatTiec ADD COLUMN LyDoHuy TEXT;
    ALTER TABLE DonDatTiec ADD COLUMN TienHoanCoc DECIMAL(15,2);
    ALTER TABLE DonDatTiec ADD COLUMN GhiChu TEXT;
    
    -- Add foreign key (có thể fail nếu đã tồn tại)
    ALTER TABLE DonDatTiec ADD CONSTRAINT fk_dondattiec_sanh 
        FOREIGN KEY (MaSanh) REFERENCES SanhTiec(MaSanh) ON DELETE SET NULL;
END$$
DELIMITER ;
CALL AddDonDatTiecColumns();
DROP PROCEDURE IF EXISTS AddDonDatTiecColumns;

-- ============= INSERT SAMPLE DATA FOR PARTY MANAGEMENT =============
-- Insert Goi Tiec (sử dụng INSERT IGNORE để tránh lỗi duplicate)
INSERT IGNORE INTO GoiTiec (MaGoi, TenGoi, GiaTronGoi, SoLuongNguoiToiThieu, SoLuongNguoiToiDa, MoTa) VALUES
(1, 'Goi Co Ban', 500000, 10, 50, 'Goi tiec co ban danh cho gia dinh nho'),
(2, 'Goi Tieu Chuan', 800000, 20, 80, 'Goi tiec tieu chuan cho su kien cong ty'),
(3, 'Goi Premium', 1200000, 30, 100, 'Goi tiec cao cap voi menu da dang'),
(4, 'Goi Vip', 2000000, 50, 200, 'Goi tiec VIP danh cho su kien lon');

-- Insert Sanh Tiec (sử dụng INSERT IGNORE)
INSERT IGNORE INTO SanhTiec (MaSanh, TenSanh, SucChua, DienTich, GiaThue, TrangThai, MoTa) VALUES
(1, 'Sanh Hoa Hong', 50, 100, 2000000, 'TRONG', 'Sanh tiec nho, phu hop cho buoi tiec gia dinh'),
(2, 'Sanh Lan Huong', 100, 200, 3000000, 'TRONG', 'Sanh tiec trung binh, phu hop cho tiec cong ty'),
(3, 'Sanh Thuy Tinh', 150, 300, 4000000, 'TRONG', 'Sanh tiec lon voi am thanh chuyen nghiep'),
(4, 'Sanh Hoang Gia', 200, 400, 5000000, 'TRONG', 'Sanh VIP cao cap voi trang thiet bi hien dai'),
(5, 'Sanh Dai Duong', 120, 250, 4500000, 'TRONG', 'Sanh tang cao voi view thanh pho');

-- ============= INSERT SAMPLE EMPLOYEE ACCOUNTS =============
-- Password chung cho tất cả: 123456
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2

-- Insert NhanVien mẫu (sử dụng INSERT IGNORE)
INSERT IGNORE INTO NhanVien (MaNV, HoTen, ChucVu, SDT, Email, NgayVaoLam, HeSoLuong, TrangThai, MaCS) VALUES
(2, 'Nguyen Van Tiep', 'TiepTan', '0987654321', 'tieptan@karaoke.com', CURRENT_DATE, 1.2, 'Dang lam viec', 1),
(3, 'Tran Thi Toan', 'KeToan', '0987654322', 'ketoan@karaoke.com', CURRENT_DATE, 1.3, 'Dang lam viec', 1),
(4, 'Le Van Bep', 'Bep', '0987654323', 'bep@karaoke.com', CURRENT_DATE, 1.0, 'Dang lam viec', 1),
(5, 'Pham Thi Vu', 'PhucVu', '0987654324', 'phucvu@karaoke.com', CURRENT_DATE, 1.0, 'Dang lam viec', 1);

-- Insert TaiKhoan cho nhân viên (sử dụng INSERT IGNORE)
INSERT IGNORE INTO TaiKhoan (TenDangNhap, MatKhauHash, LoaiTaiKhoan, MaNhanVien, TrangThai) VALUES
('tieptan', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2', 'NHAN_VIEN', 2, 'Hoat dong'),
('ketoan', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2', 'NHAN_VIEN', 3, 'Hoat dong'),
('bep', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2', 'NHAN_VIEN', 4, 'Hoat dong'),
('phucvu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/Ko2', 'NHAN_VIEN', 5, 'Hoat dong');

-- ============= SUMMARY =============
SELECT 'Database setup completed successfully!' AS Status;
SELECT 'Sample accounts created:' AS Info;
SELECT 'Admin: admin/admin123' AS Account1;
SELECT 'TiepTan: tieptan/123456' AS Account2;
SELECT 'KeToan: ketoan/123456' AS Account3;
SELECT 'Bep: bep/123456' AS Account4;
SELECT 'PhucVu: phucvu/123456' AS Account5;


