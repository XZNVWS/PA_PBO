package id.my.keuangan.app;

import id.my.keuangan.manager.LaporanManager;
import id.my.keuangan.manager.TransaksiManager;
import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;

import java.time.LocalDateTime;


public class MainApp {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("   SISTEM KEUANGAN PRIBADI — DEMO OOP M2-4  ");
        System.out.println("==============================================");
        System.out.println();

        TransaksiManager manager  = new TransaksiManager();
        LaporanManager   laporan  = new LaporanManager(manager);

        Pemasukan gaji = new Pemasukan(
                "TX001",
                8500000,
                "Gaji Bulan Mei",
                Kategori.GAJI,
                LocalDateTime.of(2025, 5, 1, 0, 0)
        );

        Pemasukan freelance = new Pemasukan(
                "TX002",
                2000000,
                "Desain Logo Client",
                Kategori.FREELANCE,
                LocalDateTime.of(2025, 5, 10, 10, 30)
        );

        Pengeluaran makan = new Pengeluaran(
                "TX003",
                45000,
                "Makan Siang Padang",
                Kategori.MAKANAN,
                LocalDateTime.of(2025, 5, 12, 12, 15)
        );

        Pengeluaran transportasi = new Pengeluaran(
                "TX004",
                120000,
                "Bensin & Tol",
                Kategori.TRANSPORTASI,
                LocalDateTime.of(2025, 5, 15, 8, 0)
        );

        Pengeluaran tagihan = new Pengeluaran(
                "TX005",
                350000,
                "Tagihan Listrik Rumah",
                Kategori.TAGIHAN,
                LocalDateTime.of(2025, 5, 5, 19, 45)
        );

        System.out.println(" Menambahkan transaksi ");
        manager.tambahTransaksi(gaji);
        manager.tambahTransaksi(freelance);
        manager.tambahTransaksi(makan);
        manager.tambahTransaksi(transportasi);
        manager.tambahTransaksi(tagihan);
        System.out.println();

        System.out.println("── Detail Transaksi  ──");
        gaji.tampilkanDetail();
        tagihan.tampilkanDetail();

        System.out.println();
        System.out.println(laporan.generateRingkasanTeks());

        System.out.println(laporan.tampilkanPengeluaranPerKategori());
        System.out.println();

        System.out.println("── per transaksi ──");
        manager.getAllTransaksi().forEach(t ->
                System.out.printf("  [%s] %-25s dampak saldo: Rp %,.0f%n",
                        t.getTipe(), t.getDeskripsi(), t.getDampakSaldo())
        );
    }
}