package id.my.keuangan.manager;

import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Transaksi;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaporanManager {

    private final TransaksiManager transaksiManager;

    public LaporanManager(TransaksiManager transaksiManager) {
        this.transaksiManager = transaksiManager;
    }

    public String generateRingkasanTeks() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RINGKASAN LAPORAN KEUANGAN ===\n");
        sb.append("Total Transaksi  : ").append(transaksiManager.getJumlahTransaksi()).append(" data\n");
        sb.append("Total Pemasukan  : Rp ").append(String.format("%,.0f", transaksiManager.getTotalPemasukan())).append("\n");
        sb.append("Total Pengeluaran: Rp ").append(String.format("%,.0f", transaksiManager.getTotalPengeluaran())).append("\n");
        sb.append("Saldo Bersih     : Rp ").append(String.format("%,.0f", transaksiManager.getSaldoBersih())).append("\n");
        sb.append("Total Saldo Akhir: Rp ").append(String.format("%,.0f", transaksiManager.hitungTotalSaldo())).append("\n");
        return sb.toString();
    }

    public String tampilkanRingkasan() {
        return generateRingkasanTeks();
    }

    public String tampilkanLaporanBulanan(Month bulan, int tahun) {
        List<Transaksi> listBulan = transaksiManager.getTransaksiByBulan(bulan.getValue(), tahun);

        double pemPulan = listBulan.stream().filter(t -> t instanceof Pemasukan).mapToDouble(Transaksi::getJumlah).sum();
        double pengBulan = listBulan.stream().filter(t -> t instanceof Pengeluaran).mapToDouble(Transaksi::getJumlah).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("=== LAPORAN BULANAN ===\n");
        sb.append("Periode          : ").append(bulan.name()).append(" ").append(tahun).append("\n");
        sb.append("Jumlah Transaksi : ").append(listBulan.size()).append(" data\n");
        sb.append("Total Pemasukan  : Rp ").append(String.format("%,.0f", pemPulan)).append("\n");
        sb.append("Total Pengeluaran: Rp ").append(String.format("%,.0f", pengBulan)).append("\n");
        sb.append("Selisih Bulanan  : Rp ").append(String.format("%,.0f", (pemPulan - pengBulan))).append("\n");
        return sb.toString();
    }

    public Map<Kategori, Double> hitungPengeluaranPerKategori() {
        Map<Kategori, Double> pemetaan = new HashMap<>();
        for (Transaksi t : transaksiManager.getAllPengeluaran()) {
            pemetaan.put(t.getKategori(), pemetaan.getOrDefault(t.getKategori(), 0.0) + t.getJumlah());
        }
        return pemetaan;
    }

    public String tampilkanPengeluaranPerKategori() {
        Map<Kategori, Double> data = hitungPengeluaranPerKategori();
        if (data.isEmpty()) {
            return "Belum ada data pengeluaran.\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== PENGELUARAN PER KATEGORI ===\n");
        for (Map.Entry<Kategori, Double> entry : data.entrySet()) {
            sb.append(entry.getKey().getLabel())
                    .append(" : Rp ")
                    .append(String.format("%,.0f", entry.getValue()))
                    .append("\n");
        }
        return sb.toString();
    }
}