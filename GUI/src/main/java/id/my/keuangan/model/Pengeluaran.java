package id.my.keuangan.model;

import java.time.LocalDateTime;

public final class Pengeluaran extends Transaksi {

    private String metodePembayaran;
    private String catatan;

    // Untuk GUI baru
    public Pengeluaran(double jumlah, String deskripsi, Kategori kategori, String metodePembayaran) {
        super(jumlah, deskripsi, kategori);
        setMetodePembayaran(metodePembayaran);
        this.catatan = "";
    }

    // Untuk Load dari DB
    public Pengeluaran(String id, double jumlah, String deskripsi, Kategori kategori, LocalDateTime waktu, String metodePembayaran, String catatan) {
        super(id, jumlah, deskripsi, kategori, waktu);
        setMetodePembayaran(metodePembayaran);
        setCatatan(catatan);
    }

    public String getMetodePembayaran() { return metodePembayaran; }
    public String getCatatan() { return catatan; }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = (metodePembayaran == null || metodePembayaran.isBlank()) ? "Tunai" : metodePembayaran.trim();
    }

    public void setCatatan(String catatan) { this.catatan = (catatan != null) ? catatan.trim() : ""; }

    @Override public double getDampakSaldo() { return -getJumlah(); } // Penanda (-)
    @Override public String getTipe() { return "PENGELUARAN"; }
}