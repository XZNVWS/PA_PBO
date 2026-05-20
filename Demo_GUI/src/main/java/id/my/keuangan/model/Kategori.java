package id.my.keuangan.model;


public enum Kategori {

    GAJI("Gaji", "PEMASUKAN"),
    FREELANCE("Freelance", "PEMASUKAN"),
    INVESTASI("Investasi", "PEMASUKAN"),
    BONUS("Bonus", "PEMASUKAN"),
    LAINNYA_MASUK("Lainnya (Pemasukan)", "PEMASUKAN"),

    MAKANAN("Makanan & Minuman", "PENGELUARAN"),
    TRANSPORTASI("Transportasi", "PENGELUARAN"),
    TAGIHAN("Tagihan & Utilitas", "PENGELUARAN"),
    KESEHATAN("Kesehatan", "PENGELUARAN"),
    PENDIDIKAN("Pendidikan", "PENGELUARAN"),
    HIBURAN("Hiburan", "PENGELUARAN"),
    BELANJA("Belanja", "PENGELUARAN"),
    LAINNYA_KELUAR("Lainnya (Pengeluaran)", "PENGELUARAN");

    private final String label;
    private final String tipeKategori;

    Kategori(String label, String tipeKategori) {
        this.label = label;
        this.tipeKategori = tipeKategori;
    }

    public String getLabel() {
        return label;
    }

    public String getTipeKategori() {
        return tipeKategori;
    }

    @Override
    public String toString() {
        return label;
    }
}
