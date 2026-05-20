package id.my.keuangan.app;

import id.my.keuangan.manager.TransaksiManager;
import id.my.keuangan.model.Kategori;
import id.my.keuangan.model.Pemasukan;
import id.my.keuangan.model.Pengeluaran;
import id.my.keuangan.model.Transaksi;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainGUI extends Application {

    private TransaksiManager manager;
    private ObservableList<Transaksi> dataTabel;
    private TableView<Transaksi> tabel;
    private Label lblSaldo;

    @Override
    public void init() {
        manager = new TransaksiManager();
        manager.tambahTransaksi(new Pemasukan(5000000, "Gaji Bulanan", Kategori.GAJI, LocalDate.now(), "PT. Tekno"));
        manager.tambahTransaksi(new Pengeluaran(50000, "Makan Siang", Kategori.MAKANAN, LocalDate.now(), "Dompet Digital"));
    }

    @Override
    public void start(Stage primaryStage) {
        tabel = new TableView<>();

        TableColumn<Transaksi, String> colId = new TableColumn<>("ID Transaksi");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(100);

        TableColumn<Transaksi, String> colDeskripsi = new TableColumn<>("Deskripsi");
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        colDeskripsi.setPrefWidth(180);

        TableColumn<Transaksi, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setCellValueFactory(cellData -> {
            if (cellData.getValue().getKategori() != null) {
                return javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getKategori().getLabel());
            }
            return javafx.beans.binding.Bindings.createStringBinding(() -> "");
        });
        colKategori.setPrefWidth(120);

        TableColumn<Transaksi, Double> colJumlah = new TableColumn<>("Jumlah (Rp)");
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colJumlah.setPrefWidth(120);

        TableColumn<Transaksi, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalFormatted"));
        colTanggal.setPrefWidth(110);

        tabel.getColumns().addAll(colId, colDeskripsi, colKategori, colJumlah, colTanggal);

        dataTabel = FXCollections.observableArrayList(manager.getAllTransaksi());
        tabel.setItems(dataTabel);

        lblSaldo = new Label();
        lblSaldo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        updateSaldo();

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(10, 0, 10, 0));

        TextField txtDeskripsi = new TextField();
        txtDeskripsi.setPromptText("Contoh: Beli Token Listrik");

        TextField txtJumlah = new TextField();
        txtJumlah.setPromptText("Contoh: 150000");

        ComboBox<Kategori> comboKategori = new ComboBox<>();
        comboKategori.getItems().setAll(Kategori.values());
        comboKategori.setPromptText("-- Pilih Kategori --");

        TextField txtSpesifik = new TextField();
        txtSpesifik.setPromptText("Sumber Dana / Metode Bayar");

        gridForm.add(new Label("Deskripsi:"), 0, 0);
        gridForm.add(txtDeskripsi, 1, 0);
        gridForm.add(new Label("Jumlah (Rp):"), 0, 1);
        gridForm.add(txtJumlah, 1, 1);
        gridForm.add(new Label("Kategori:"), 2, 0);
        gridForm.add(comboKategori, 3, 0);
        gridForm.add(new Label("Keterangan Tambahan:"), 2, 1);
        gridForm.add(txtSpesifik, 3, 1);

        Button btnPemasukan = new Button("Tambah Pemasukan");
        Button btnPengeluaran = new Button("Tambah Pengeluaran");

        btnPemasukan.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnPengeluaran.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnPemasukan.setOnAction(e -> {
            try {
                if (comboKategori.getValue() == null) {
                    tampilkanAlert("Kategori Kosong", "Silakan pilih kategori terlebih dahulu.");
                    return;
                }

                double jumlah = Double.parseDouble(txtJumlah.getText());
                String deskripsi = txtDeskripsi.getText();
                Kategori kategori = comboKategori.getValue();
                String sumber = txtSpesifik.getText();

                Pemasukan baru = new Pemasukan(jumlah, deskripsi, kategori, LocalDate.now(), sumber);
                manager.tambahTransaksi(baru);

                refreshTabel();
                bersihkanForm(txtDeskripsi, txtJumlah, comboKategori, txtSpesifik);
            } catch (NumberFormatException ex) {
                tampilkanAlert("Format Jumlah Salah", "Jumlah transaksi wajib berupa nilai angka.");
            } catch (IllegalArgumentException ex) {
                tampilkanAlert("Aksi Ditolak", ex.getMessage());
            }
        });

        btnPengeluaran.setOnAction(e -> {
            try {
                if (comboKategori.getValue() == null) {
                    tampilkanAlert("Kategori Kosong", "Silakan pilih kategori terlebih dahulu.");
                    return;
                }

                double jumlah = Double.parseDouble(txtJumlah.getText());
                String deskripsi = txtDeskripsi.getText();
                Kategori kategori = comboKategori.getValue();
                String metode = txtSpesifik.getText();

                Pengeluaran baru = new Pengeluaran(jumlah, deskripsi, kategori, LocalDate.now(), metode);
                manager.tambahTransaksi(baru);

                refreshTabel();
                bersihkanForm(txtDeskripsi, txtJumlah, comboKategori, txtSpesifik);
            } catch (NumberFormatException ex) {
                tampilkanAlert("Format Jumlah Salah", "Jumlah transaksi wajib berupa nilai angka.");
            } catch (IllegalArgumentException ex) {
                tampilkanAlert("Aksi Ditolak", ex.getMessage());
            }
        });

        HBox areaTombol = new HBox(15, btnPemasukan, btnPengeluaran);
        areaTombol.setPadding(new Insets(10, 0, 0, 0));

        HBox areaSaldo = new HBox(lblSaldo);
        areaSaldo.setAlignment(Pos.CENTER_RIGHT);
        areaSaldo.setPadding(new Insets(0, 0, 10, 0));

        VBox root = new VBox(10, areaSaldo, tabel, gridForm, areaTombol);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 750, 550);
        primaryStage.setTitle("Sistem Manajemen Keuangan Pribadi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshTabel() {
        dataTabel.setAll(manager.getAllTransaksi());
        updateSaldo();
    }

    private void updateSaldo() {
        lblSaldo.setText(String.format("Total Saldo Saat Ini: Rp %,.0f", manager.hitungTotalSaldo()));
    }

    private void bersihkanForm(TextField deskripsi, TextField jumlah, ComboBox<Kategori> kategori, TextField spesifik) {
        deskripsi.clear();
        jumlah.clear();
        kategori.setValue(null);
        spesifik.clear();
    }

    private void tampilkanAlert(String header, String pesan) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan Sistem");
        alert.setHeaderText(header);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}