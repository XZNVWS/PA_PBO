package id.my.keuangan.model;

import java.time.LocalDateTime;

public final class Pemasukan extends Transaksi {

    private String sumberPemasukan;

    // Untuk GUI baru
    public Pemasukan(double jumlah, String deskripsi, Kategori kategori, String sumberPemasukan) {
        super(jumlah, deskripsi, kategori);
        setSumberPemasukan(sumberPemasukan);
    }

    // Untuk Load dari DB
    public Pemasukan(String id, double jumlah, String deskripsi, Kategori kategori, LocalDateTime waktu, String sumberPemasukan) {
        super(id, jumlah, deskripsi, kategori, waktu);
        setSumberPemasukan(sumberPemasukan);
    }

    public String getSumberPemasukan() { return sumberPemasukan; }

    public void setSumberPemasukan(String sumberPemasukan) {
        this.sumberPemasukan = (sumberPemasukan == null || sumberPemasukan.isBlank()) ? "-" : sumberPemasukan.trim();
    }

    @Override public double getDampakSaldo() { return +getJumlah(); } // Penanda (+)
    @Override public String getTipe() { return "PEMASUKAN"; }
}