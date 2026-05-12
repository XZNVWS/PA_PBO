package id.my.keuangan.manager;

import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;
import id.my.keuangan.model.Transaksi;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TransaksiManager {
    private final List<Transaksi> daftarTransaksi;
    public TransaksiManager() {
        this.daftarTransaksi = new ArrayList<>();
    }

    public void tambahTransaksi(Transaksi transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        daftarTransaksi.add(transaksi);
        System.out.println("✓ Transaksi berhasil ditambahkan: " + transaksi.getId());
    }


    public boolean hapusTransaksi(String id) {
        boolean dihapus = daftarTransaksi.removeIf(t -> t.getId().equalsIgnoreCase(id));
        if (dihapus) {
            System.out.println("✓ Transaksi " + id + " berhasil dihapus.");
        } else {
            System.out.println("✗ Transaksi dengan ID " + id + " tidak ditemukan.");
        }
        return dihapus;
    }

  
    public Optional<Transaksi> cariById(String id) {
        return daftarTransaksi.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Transaksi> getAllTransaksi() {
        return new ArrayList<>(daftarTransaksi);
    }

    public List<Pemasukan> getAllPemasukan() {
        return daftarTransaksi.stream()
                .filter(t -> t instanceof Pemasukan)
                .map(t -> (Pemasukan) t)
                .collect(Collectors.toList());
    }

    public List<Pengeluaran> getAllPengeluaran() {
        return daftarTransaksi.stream()
                .filter(t -> t instanceof Pengeluaran)
                .map(t -> (Pengeluaran) t)
                .collect(Collectors.toList());
    }


    public List<Transaksi> getTransaksiByBulan(Month bulan, int tahun) {
        return daftarTransaksi.stream()
                .filter(t -> t.getTanggal().getMonth() == bulan
                        && t.getTanggal().getYear() == tahun)
                .collect(Collectors.toList());
    }


    public List<Transaksi> getTransaksiByKategori(Kategori kategori) {
        return daftarTransaksi.stream()
                .filter(t -> t.getKategori() == kategori)
                .collect(Collectors.toList());
    }


    public List<Transaksi> getTransaksiByRentangTanggal(LocalDate dari, LocalDate sampai) {
        return daftarTransaksi.stream()
                .filter(t -> !t.getTanggal().isBefore(dari)
                        && !t.getTanggal().isAfter(sampai))
                .collect(Collectors.toList());
    }

    public double getTotalPemasukan() {
        return getAllPemasukan().stream()
                .mapToDouble(Pemasukan::getJumlah)
                .sum();
    }

    public double getTotalPengeluaran() {
        return getAllPengeluaran().stream()
                .mapToDouble(Pengeluaran::getJumlah)
                .sum();
    }

    
    public double getSaldoBersih() {
        return daftarTransaksi.stream()
                .mapToDouble(Transaksi::getDampakSaldo)
                .sum();
    }

    public int getJumlahTransaksi() {
        return daftarTransaksi.size();
    }
}
