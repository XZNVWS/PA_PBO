package id.my.keuangan.model;

import java.time.LocalDate;


public final class Pemasukan extends Transaksi {


    private String sumberPemasukan;
    private boolean sudahDiterima;

 
    public Pemasukan(double jumlah, String deskripsi, Kategori kategori,
                     LocalDate tanggal, String sumberPemasukan) {
        super(jumlah, deskripsi, kategori, tanggal); // memanggil constructor Transaksi
        setSumberPemasukan(sumberPemasukan);
        this.sudahDiterima = true; // default sudah diterima
    }

    public Pemasukan(double jumlah, String deskripsi, Kategori kategori, String sumberPemasukan) {
        this(jumlah, deskripsi, kategori, LocalDate.now(), sumberPemasukan);
    }

   
    public Pemasukan(double jumlah, String deskripsi, Kategori kategori) {
        this(jumlah, deskripsi, kategori, LocalDate.now(), "-");
    }

   
    public String getSumberPemasukan() {
        return sumberPemasukan;
    }

    public void setSumberPemasukan(String sumberPemasukan) {
        if (sumberPemasukan == null || sumberPemasukan.isBlank()) {
            this.sumberPemasukan = "-";
        } else {
            this.sumberPemasukan = sumberPemasukan.trim();
        }
    }

    public boolean isSudahDiterima() {
        return sudahDiterima;
    }

    public void setSudahDiterima(boolean sudahDiterima) {
        this.sudahDiterima = sudahDiterima;
    }

  
    @Override
    public double getDampakSaldo() {
        return +getJumlah();
    }

   
    @Override
    public String getTipe() {
        return "PEMASUKAN";
    }

  
    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail(); // panggil tampilkan dari parent
        System.out.printf("  Sumber     : %s%n", sumberPemasukan);
        System.out.printf("  Status     : %s%n", sudahDiterima ? "✓ Sudah Diterima" : "⏳ Pending");
    }

    @Override
    public String toString() {
        return String.format("(+) %s | Sumber: %s", super.toString(), sumberPemasukan);
    }
}
