package id.my.keuangan.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Transaksi implements ITransaksiable {

    private final String id;
    private double jumlah;
    private String deskripsi;
    private Kategori kategori;
    private LocalDateTime waktu;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Transaksi(double jumlah, String deskripsi, Kategori kategori) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        setJumlah(jumlah);
        setDeskripsi(deskripsi);
        setKategori(kategori);
        this.waktu = LocalDateTime.now();
    }

    public Transaksi(String id, double jumlah, String deskripsi, Kategori kategori, LocalDateTime waktu) {
        this.id = id;
        setJumlah(jumlah);
        setDeskripsi(deskripsi);
        setKategori(kategori);
        this.waktu = (waktu != null) ? waktu : LocalDateTime.now();
    }

    public String getId() { return id; }
    @Override
    public double getJumlah() { return jumlah; }
    @Override
    public String getDeskripsi() { return deskripsi; }
    public Kategori getKategori() { return kategori; }
    public LocalDateTime getWaktu() { return waktu; }
    public String getWaktuFormatted() { return waktu.format(FORMATTER); }

    public void setJumlah(double jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah transaksi harus lebih dari 0.");
        this.jumlah = jumlah;
    }

    public void setDeskripsi(String deskripsi) {
        if (deskripsi == null || deskripsi.isBlank()) throw new IllegalArgumentException("Deskripsi tidak boleh kosong.");
        this.deskripsi = deskripsi.trim();
    }

    public void setKategori(Kategori kategori) {
        if (kategori == null) throw new IllegalArgumentException("Kategori tidak boleh null.");
        this.kategori = kategori;
    }

    public void setWaktu(LocalDateTime waktu) { this.waktu = waktu; }

    public abstract double getDampakSaldo();

    @Override
    public void tampilkanDetail() {
        System.out.println("==============================================");
        System.out.printf( " [%s] %s%n", getTipe(), getId());
        System.out.println("==============================================");
        System.out.printf( " Deskripsi : %-28s %n", deskripsi);
        System.out.printf( " Kategori  : %-28s %n", kategori.getLabel());
        System.out.printf( " Jumlah    : Rp %-25s %n", String.format("%,.0f", jumlah));
        System.out.printf( " Tanggal   : %-28s %n", getWaktuFormatted());
        System.out.println("==============================================");
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Rp %,.0f | %s", getTipe(), id, deskripsi, jumlah, getWaktuFormatted());
    }
}