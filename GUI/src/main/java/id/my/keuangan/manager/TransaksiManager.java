package id.my.keuangan.manager;

import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;
import id.my.keuangan.model.Transaksi;
import id.my.keuangan.util.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransaksiManager {

    private final List<Transaksi> listTransaksi;
    private final double saldoAwal;

    public TransaksiManager() {
        this.listTransaksi = new ArrayList<>();
        this.saldoAwal = 1000000;

        if (isDatabaseKosong()) {
            System.out.println("Database kosong, memasukkan 2 data demo awal...");
            this.tambahTransaksi(new Pemasukan(5000000, "Gaji Bulanan", Kategori.GAJI));
            this.tambahTransaksi(new Pengeluaran(50000, "Makan Siang", Kategori.MAKANAN));
        } else {
            System.out.println("Database terdeteksi berisi data, memuat data dari database...");
            loadDataDariDatabase();
        }
    }

    private boolean isDatabaseKosong() {
        String query = "SELECT COUNT(*) FROM transaksi";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengecek isi database: " + e.getMessage());
        }
        return false;
    }

    public void tambahTransaksi(Transaksi t) {
        if (t instanceof Pengeluaran) {
            if (hitungTotalSaldo() + t.getDampakSaldo() < 0) {
                throw new IllegalArgumentException("Transaksi Ditolak! Sisa saldo tidak mencukupi untuk melakukan pengeluaran ini.");
            }
        }

        listTransaksi.add(t);

        String sql = "INSERT INTO transaksi (id, jumlah, deskripsi, kategori, waktu, tipe) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getId());
            ps.setDouble(2, t.getJumlah());
            ps.setString(3, t.getDeskripsi());
            ps.setString(4, t.getKategori().name());
            ps.setTimestamp(5, Timestamp.valueOf(t.getWaktu()));
            ps.setString(6, t.getTipe());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Gagal menyimpan ke database: " + ex.getMessage());
        }
    }

    public void loadDataDariDatabase() {
        String query = "SELECT * FROM transaksi ORDER BY waktu ASC";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            listTransaksi.clear();
            while (rs.next()) {
                String id = rs.getString("id");
                double jumlah = rs.getDouble("jumlah");
                String deskripsi = rs.getString("deskripsi");
                Kategori kategori = Kategori.valueOf(rs.getString("kategori"));
                LocalDateTime waktu = rs.getTimestamp("waktu").toLocalDateTime();
                String tipe = rs.getString("tipe");

                if ("PEMASUKAN".equalsIgnoreCase(tipe)) {
                    listTransaksi.add(new Pemasukan(id, jumlah, deskripsi, kategori, waktu));
                } else {
                    listTransaksi.add(new Pengeluaran(id, jumlah, deskripsi, kategori, waktu));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal memuat data dari database: " + e.getMessage());
        }
    }

    public List<Transaksi> getAllTransaksi() {
        return listTransaksi;
    }

    public double hitungTotalSaldo() {
        double total = saldoAwal;
        for (Transaksi t : listTransaksi) {
            total += t.getDampakSaldo();
        }
        return total;
    }

    public double getTotalPemasukan() {
        return listTransaksi.stream().filter(t -> t instanceof Pemasukan).mapToDouble(Transaksi::getJumlah).sum();
    }

    public double getTotalPengeluaran() {
        return listTransaksi.stream().filter(t -> t instanceof Pengeluaran).mapToDouble(Transaksi::getJumlah).sum();
    }

    public int getJumlahTransaksi() {
        return listTransaksi.size();
    }

    public List<Transaksi> getTransaksiByBulan(int bulan, int tahun) {
        return listTransaksi.stream()
                .filter(t -> t.getWaktu().getMonthValue() == bulan && t.getWaktu().getYear() == tahun)
                .collect(Collectors.toList());
    }

    public List<Transaksi> getAllPengeluaran() {
        return listTransaksi.stream().filter(t -> t instanceof Pengeluaran).collect(Collectors.toList());
    }
}