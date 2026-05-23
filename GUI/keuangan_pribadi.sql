-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2026 at 02:47 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `keuangan_pribadi`
--
CREATE DATABASE IF NOT EXISTS `keuangan_pribadi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `keuangan_pribadi`;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE `transaksi` (
  `id` varchar(8) NOT NULL,
  `jumlah` double NOT NULL,
  `deskripsi` varchar(255) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `waktu` datetime NOT NULL,
  `tipe` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `jumlah`, `deskripsi`, `kategori`, `waktu`, `tipe`) VALUES
('008C57A5', 5000000, 'Desain Logo', 'FREELANCE', '2026-05-21 12:02:58', 'PEMASUKAN'),
('4B956894', 50000, 'Makan Siang', 'MAKANAN', '2026-05-21 12:00:16', 'PENGELUARAN'),
('4EDEED69', 5000000, 'Gaji Bulanan', 'GAJI', '2026-05-21 12:00:16', 'PEMASUKAN'),
('502632E1', 2000000, 'Gaji sampingan', 'FREELANCE', '2026-05-23 12:29:44', 'PEMASUKAN'),
('613CD530', 10000000, 'Rakit PC', 'LAINNYA_KELUAR', '2026-05-21 12:03:17', 'PENGELUARAN'),
('7D1DA138', 20000, 'sendal', 'LAINNYA_KELUAR', '2026-05-23 12:37:19', 'PENGELUARAN'),
('7F3855D3', 3000000, 'Gaji buta', 'BONUS', '2026-05-23 12:38:01', 'PEMASUKAN'),
('A0D009E1', 2000000, 'baju', 'BELANJA', '2026-05-23 12:37:45', 'PENGELUARAN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
