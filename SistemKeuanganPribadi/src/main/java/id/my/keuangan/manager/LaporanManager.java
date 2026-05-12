package id.my.keuangan.manager;

import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Transaksi;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class LaporanManager {
    private final TransaksiManager transaksiManager;
    public LaporanManager(TransaksiManager transaksiManager) {
        if (transaksiManager == null) {
            throw new IllegalArgumentException("TransaksiManager tidak boleh null.");
        }
        this.transaksiManager = transaksiManager;
    }
    
    public void tampilkanRingkasan() {
        double totalMasuk  = transaksiManager.getTotalPemasukan();
        double totalKeluar = transaksiManager.getTotalPengeluaran();
        double saldo       = transaksiManager.getSaldoBersih();

        System.out.println("==============================================");
        System.out.println("       RINGKASAN KEUANGAN PRIBADI         ");
        System.out.println("==============================================");
        System.out.printf( "  Total Pemasukan  : Rp %-18s %n", String.format("%,.0f", totalMasuk));
        System.out.printf( "  Total Pengeluaran: Rp %-18s %n", String.format("%,.0f", totalKeluar));
        System.out.println("==============================================");
        String statusSaldo = saldo >= 0 ? "(+) SURPLUS" : "(-) DEFISIT";
        System.out.printf( "  Saldo Bersih     : Rp %-18s %n", String.format("%,.0f", Math.abs(saldo)));
        System.out.printf( "  Status           : %-21s %n", statusSaldo);
        System.out.println("==============================================");
        System.out.printf( "  Total Transaksi  : %-21d %n", transaksiManager.getJumlahTransaksi());
        System.out.println("==============================================");
    }


    public void tampilkanLaporanBulanan(Month bulan, int tahun) {
        List<Transaksi> transaksi = transaksiManager.getTransaksiByBulan(bulan, tahun);
        String namaBulan = bulan.getDisplayName(TextStyle.FULL, new Locale("id", "ID"));

        System.out.println();
        System.out.println("═".repeat(50));
        System.out.printf("  LAPORAN BULAN: %s %d%n", namaBulan.toUpperCase(), tahun);
        System.out.println("═".repeat(50));

        if (transaksi.isEmpty()) {
            System.out.println("  Tidak ada transaksi pada periode ini.");
            return;
        }

        double totalMasuk  = 0;
        double totalKeluar = 0;

        for (Transaksi t : transaksi) {
            System.out.printf("  [%s] %-20s Rp %,12.0f  (%s)%n",
                    t.getTanggalFormatted(),
                    t.getDeskripsi().length() > 20
                            ? t.getDeskripsi().substring(0, 17) + "..."
                            : t.getDeskripsi(),
                    t.getJumlah(),
                    t.getTipe());
            if (t.getTipe().equals("PEMASUKAN"))  totalMasuk  += t.getJumlah();
            else                                   totalKeluar += t.getJumlah();
        }

        System.out.println("─".repeat(50));
        System.out.printf("  Pemasukan   : Rp %,15.0f%n", totalMasuk);
        System.out.printf("  Pengeluaran : Rp %,15.0f%n", totalKeluar);
        System.out.printf("  Selisih     : Rp %,15.0f%n", (totalMasuk - totalKeluar));
        System.out.println("═".repeat(50));
    }


    public void tampilkanPengeluaranPerKategori() {
        List<Transaksi> pengeluaran = transaksiManager.getAllPengeluaran()
                .stream()
                .map(p -> (Transaksi) p)
                .collect(Collectors.toList());

        if (pengeluaran.isEmpty()) {
            System.out.println("Belum ada data pengeluaran.");
            return;
        }

        Map<Kategori, Double> perKategori = pengeluaran.stream()
                .collect(Collectors.groupingBy(
                        Transaksi::getKategori,
                        Collectors.summingDouble(Transaksi::getJumlah)
                ));

        double total = perKategori.values().stream().mapToDouble(Double::doubleValue).sum();

        System.out.println();
        System.out.println("==============================================");
        System.out.println("  PENGELUARAN PER KATEGORI");
        System.out.println("==============================================");

        perKategori.entrySet().stream()
                .sorted(Map.Entry.<Kategori, Double>comparingByValue().reversed())
                .forEach(entry -> {
                    double persen = (entry.getValue() / total) * 100;
                    System.out.printf("  %-25s Rp %,10.0f  (%.1f%%)%n",
                            entry.getKey().getLabel(),
                            entry.getValue(),
                            persen);
                });

        System.out.println("──────────────────────────────────────────");
        System.out.printf("  %-25s Rp %,10.0f%n", "TOTAL", total);
        System.out.println("==============================================");
    }

    public double getTotalPengeluaranBulan(Month bulan, int tahun) {
        return transaksiManager.getTransaksiByBulan(bulan, tahun)
                .stream()
                .filter(t -> t.getTipe().equals("PENGELUARAN"))
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }

    public double getTotalPemasukanBulan(Month bulan, int tahun) {
        return transaksiManager.getTransaksiByBulan(bulan, tahun)
                .stream()
                .filter(t -> t.getTipe().equals("PEMASUKAN"))
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }
}
