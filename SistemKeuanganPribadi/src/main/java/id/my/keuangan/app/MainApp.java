package id.my.keuangan.app;

import id.my.keuangan.manager.LaporanManager;
import id.my.keuangan.manager.TransaksiManager;
import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;

import java.time.LocalDate;
import java.time.Month;


public class MainApp {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("   SISTEM KEUANGAN PRIBADI — Demo OOP M2-4  ");
        System.out.println("==============================================");
        System.out.println();


        TransaksiManager manager  = new TransaksiManager();
        LaporanManager   laporan  = new LaporanManager(manager);
        Pemasukan gaji = new Pemasukan(
                8_500_000,
                "Gaji Bulan Mei",
                Kategori.GAJI,
                LocalDate.of(2025, 5, 1),
                "PT. Maju Bersama"
        );

        Pemasukan freelance = new Pemasukan(
                2_000_000,
                "Desain Logo Client",
                Kategori.FREELANCE,
                LocalDate.of(2025, 5, 10),
                "Client Pribadi"
        );

        Pengeluaran makan = new Pengeluaran(
                450_000,
                "Makan siang seminggu",
                Kategori.MAKANAN,
                LocalDate.of(2025, 5, 7),
                "Tunai"
        );

        Pengeluaran transportasi = new Pengeluaran(
                300_000,
                "Bensin + Toll",
                Kategori.TRANSPORTASI,
                LocalDate.of(2025, 5, 8),
                "Dompet Digital"
        );

        Pengeluaran tagihan = new Pengeluaran(
                750_000,
                "Listrik + Internet",
                Kategori.TAGIHAN,
                LocalDate.of(2025, 5, 5),
                "Transfer Bank"
        );

        System.out.println("Setter & Getter");
        makan.setCatatan("Termasuk kopi pagi");
        System.out.println("Catatan makan: " + makan.getCatatan());
        System.out.println();
        System.out.println(" Menambahkan transaksi ");
        manager.tambahTransaksi(gaji);          // Pemasukan → Transaksi
        manager.tambahTransaksi(freelance);     // Pemasukan → Transaksi
        manager.tambahTransaksi(makan);         // Pengeluaran → Transaksi
        manager.tambahTransaksi(transportasi);  // Pengeluaran → Transaksi
        manager.tambahTransaksi(tagihan);       // Pengeluaran → Transaksi
        System.out.println();
        
        System.out.println("── Detail Transaksi (override tampilkanDetail()) ──");
        gaji.tampilkanDetail();
        tagihan.tampilkanDetail();
        
        System.out.println();
        laporan.tampilkanRingkasan();
        
        laporan.tampilkanLaporanBulanan(Month.MAY, 2025);
        laporan.tampilkanPengeluaranPerKategori();
        System.out.println();
        System.out.println("── Modul 4: getDampakSaldo() per transaksi ──");
        manager.getAllTransaksi().forEach(t ->
                System.out.printf("  [%s] %-25s dampak saldo: Rp %,.0f%n",
                        t.getTipe(),
                        t.getDeskripsi(),
                        t.getDampakSaldo())
        );

        System.out.println();
    }
}
