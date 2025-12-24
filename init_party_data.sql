-- Script khởi tạo dữ liệu mẫu cho quản lý đặt tiệc

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

-- Thêm cột mới vào bảng DonDatTiec (bỏ qua lỗi nếu cột đã tồn tại)
-- MySQL không hỗ trợ IF NOT EXISTS trong ALTER TABLE, nên dùng procedure
DELIMITER $$

CREATE PROCEDURE AddColumnIfNotExists()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    
    ALTER TABLE DonDatTiec ADD COLUMN MaSanh INT;
    ALTER TABLE DonDatTiec ADD COLUMN NgayDat DATETIME;
    ALTER TABLE DonDatTiec ADD COLUMN LyDoHuy TEXT;
    ALTER TABLE DonDatTiec ADD COLUMN TienHoanCoc DECIMAL(15,2);
    ALTER TABLE DonDatTiec ADD COLUMN GhiChu TEXT;
    ALTER TABLE DonDatTiec ADD FOREIGN KEY (MaSanh) REFERENCES SanhTiec(MaSanh) ON DELETE SET NULL;
END$$

DELIMITER ;

CALL AddColumnIfNotExists();
DROP PROCEDURE IF EXISTS AddColumnIfNotExists;

-- Xoa toan bo du lieu cu (tranh trung lap va loi encoding)
DELETE FROM GoiTiec;
DELETE FROM SanhTiec;

-- Reset AUTO_INCREMENT
ALTER TABLE GoiTiec AUTO_INCREMENT = 1;
ALTER TABLE SanhTiec AUTO_INCREMENT = 1;

-- Insert du lieu mau cho Goi Tiec (moi, khong dau)
INSERT INTO GoiTiec (TenGoi, GiaTronGoi) VALUES
('Goi Co Ban', 500000),
('Goi Tieu Chuan', 800000),
('Goi Premium', 1200000),
('Goi Vip', 2000000);

-- Insert du lieu mau cho Sanh Tiec (moi, khong dau)
INSERT INTO SanhTiec (TenSanh, SucChua, DienTich, GiaThue, TrangThai, MoTa) VALUES
('Sanh Hoa Hong', 50, 100, 2000000, 'TRONG', 'Sanh tiec nho, phu hop cho buoi tiec gia dinh'),
('Sanh Lan Huong', 100, 200, 3000000, 'TRONG', 'Sanh tiec trung binh, phu hop cho tiec cong ty'),
('Sanh Thuy Tinh', 150, 300, 4000000, 'TRONG', 'Sanh tiec lon voi am thanh chuyen nghiep'),
('Sanh Hoang Gia', 200, 400, 5000000, 'TRONG', 'Sanh VIP cao cap voi trang thiet bi hien dai'),
('Sanh Dai Duong', 120, 250, 4500000, 'TRONG', 'Sanh tang cao voi view thanh pho');

-- Cap nhat trang thai DonDatTiec neu co
UPDATE DonDatTiec SET TrangThai = 'CHO_XAC_NHAN' WHERE TrangThai = 'Cho xac nhan';
UPDATE DonDatTiec SET TrangThai = 'DA_COC' WHERE TrangThai = 'Da coc';
UPDATE DonDatTiec SET TrangThai = 'HUY' WHERE TrangThai = 'Huy';

SELECT 'Khoi tao du lieu thanh cong!' AS Status;
