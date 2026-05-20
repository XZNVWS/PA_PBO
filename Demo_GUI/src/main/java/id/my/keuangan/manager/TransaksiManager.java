package id.my.keuangan.manager;

import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;
import id.my.keuangan.model.Transaksi;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransaksiManager {

    private final List<Transaksi> listTransaksi;
    private final double saldoAwal;

    public TransaksiManager() {
        this.listTransaksi = new ArrayList<>();
        this.saldoAwal = 1000000;
    }

    public void tambahTransaksi(Transaksi t) {
        listTransaksi.add(t);
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
        return listTransaksi.stream()
                .filter(t -> t instanceof Pemasukan)
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }

    public double getTotalPengeluaran() {
        return listTransaksi.stream()
                .filter(t -> t instanceof Pengeluaran)
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }

    public double getSaldoBersih() {
        return getTotalPemasukan() - getTotalPengeluaran();
    }

    public int getJumlahTransaksi() {
        return listTransaksi.size();
    }

    public List<Transaksi> getTransaksiByBulan(int bulan, int tahun) {
        return listTransaksi.stream()
                .filter(t -> t.getTanggal().getMonthValue() == bulan && t.getTanggal().getYear() == tahun)
                .collect(Collectors.toList());
    }

    public List<Transaksi> getAllPengeluaran() {
        return listTransaksi.stream()
                .filter(t -> t instanceof Pengeluaran)
                .collect(Collectors.toList());
    }
}