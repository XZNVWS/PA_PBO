package id.my.keuangan.model;

public interface ITransaksiable {
    double getJumlah();
    String getDeskripsi();
    String getTipe();
    void tampilkanDetail();
}