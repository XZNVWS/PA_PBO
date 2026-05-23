package id.my.keuangan.model;

import java.time.LocalDateTime;

public final class Pengeluaran extends Transaksi {

    public Pengeluaran(double jumlah, String deskripsi, Kategori kategori) {
        super(jumlah, deskripsi, kategori);
    }

    public Pengeluaran(String id, double jumlah, String deskripsi, Kategori kategori, LocalDateTime waktu) {
        super(id, jumlah, deskripsi, kategori, waktu);
    }

    @Override
    public double getDampakSaldo() {
        return -getJumlah();
    }

    @Override
    public String getTipe() {
        return "PENGELUARAN";
    }
}