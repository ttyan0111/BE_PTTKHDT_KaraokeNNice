-- Import schema từ ScriptPTTKHDT.sql
SOURCE /docker-entrypoint-initdb.d/ScriptPTTKHDT.sql;

-- Fix MatHang INSERT (remove TrangThai)
DELETE FROM MatHang;
INSERT INTO MatHang (TenHang, LoaiHang, SoLuongTon, DonViTinh, GiaNhap, GiaBan, MoTa) VALUES
('Bia Heineken', 'Đồ uống', 100, 'Hộp', 40000, 60000, 'Bia nhập khẩu'),
('Coca Cola', 'Đồ uống', 200, 'Chai', 8000, 15000, 'Nước ngọt'),
('Gà rán', 'Thức ăn', 50, 'Cái', 100000, 180000, 'Gà rán chiên vàng'),
('Mực nướng', 'Thức ăn', 30, 'Đĩa', 150000, 280000, 'Mực nướng thơm ngon'),
('Trái cây tươi', 'Thức ăn', 20, 'Đĩa', 80000, 150000, 'Trái cây tươi mát');

-- Khách hàng
INSERT INTO KhachHang (TenKH, SDT, Email, DiaChi, NgaySinh, GioiTinh, CMND) VALUES
('Nguyễn Văn A', '0901234567', 'nva@email.com', 'Hà Nội', '1990-01-15', 'Nam', '012345678'),
('Trần Thị B', '0912345678', 'ttb@email.com', 'TP HCM', '1992-03-20', 'Nữ', '987654321');

-- Thẻ thành viên
INSERT INTO TheThanhVien (MaKH, HangThe, DiemTichLuy) VALUES
(1, 'Đồng', 1000),
(2, 'Bạc', 5000);

-- Phòng
INSERT INTO Phong (TenPhong, TrangThai, MaCS, MaLoai) VALUES
('Phòng 101', 'Trong', 1, 1),
('Phòng 102', 'Trong', 1, 2);
