package id.my.keuangan.model;

import java.time.LocalDate;


public final class Pengeluaran extends Transaksi {

    private String metodePembayaran;
    private boolean sudahDibayar;
    private String catatan;

    
    public Pengeluaran(double jumlah, String deskripsi, Kategori kategori,
                       LocalDate tanggal, String metodePembayaran) {
        super(jumlah, deskripsi, kategori, tanggal); // memanggil constructor Transaksi
        setMetodePembayaran(metodePembayaran);
        this.sudahDibayar = true;  // default sudah dibayar
        this.catatan      = "";
    }

   
    public Pengeluaran(double jumlah, String deskripsi, Kategori kategori, String metodePembayaran) {
        this(jumlah, deskripsi, kategori, LocalDate.now(), metodePembayaran);
    }

   
    public Pengeluaran(double jumlah, String deskripsi, Kategori kategori) {
        this(jumlah, deskripsi, kategori, LocalDate.now(), "Tunai");
    }

   
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        if (metodePembayaran == null || metodePembayaran.isBlank()) {
            this.metodePembayaran = "Tunai";
        } else {
            this.metodePembayaran = metodePembayaran.trim();
        }
    }

    public boolean isSudahDibayar() {
        return sudahDibayar;
    }

    public void setSudahDibayar(boolean sudahDibayar) {
        this.sudahDibayar = sudahDibayar;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = (catatan != null) ? catatan.trim() : "";
    }


    @Override
    public double getDampakSaldo() {
        return -getJumlah();
    }


    @Override
    public String getTipe() {
        return "PENGELUARAN";
    }

    
    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail();
        System.out.printf("  Metode     : %s%n", metodePembayaran);
        System.out.printf("  Status     : %s%n", sudahDibayar ? "✓ Sudah Dibayar" : "⏳ Belum Dibayar");
        if (!catatan.isBlank()) {
            System.out.printf("  Catatan    : %s%n", catatan);
        }
    }

    @Override
    public String toString() {
        return String.format("(-) %s | Metode: %s", super.toString(), metodePembayaran);
    }
}
