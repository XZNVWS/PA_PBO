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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private TransaksiManager manager;
    private ObservableList<Transaksi> dataTabel;
    private TableView<Transaksi> tabel;

    private Label lblSaldoTotal, lblLaporPemasukan, lblLaporPengeluaran;

    @Override
    public void init() {
        manager = new TransaksiManager();
    }

    @Override
    public void start(Stage primaryStage) {
        tabel = new TableView<>();

        TableColumn<Transaksi, String> colWaktu = new TableColumn<>("Tanggal/Waktu");
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktuFormatted"));
        colWaktu.setPrefWidth(140);

        TableColumn<Transaksi, String> colTipe = new TableColumn<>("Tipe");
        colTipe.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        colTipe.setPrefWidth(100);

        TableColumn<Transaksi, String> colDeskripsi = new TableColumn<>("Deskripsi");
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        colDeskripsi.setPrefWidth(160);

        TableColumn<Transaksi, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setCellValueFactory(cellData -> {
            if (cellData.getValue().getKategori() != null) {
                return javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getKategori().getLabel());
            }
            return javafx.beans.binding.Bindings.createStringBinding(() -> "");
        });
        colKategori.setPrefWidth(130);

        TableColumn<Transaksi, Double> colJumlah = new TableColumn<>("Jumlah (Rp)");
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colJumlah.setPrefWidth(140);
        colJumlah.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Transaksi t = getTableView().getItems().get(getIndex());
                    if (t instanceof Pemasukan) {
                        setText(String.format("Rp %,.0f", value));
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    } else {
                        setText(String.format("-Rp %,.0f", value));
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: CENTER-RIGHT;");
                    }
                }
            }
        });

        tabel.getColumns().addAll(colWaktu, colTipe, colDeskripsi, colKategori, colJumlah);

        dataTabel = FXCollections.observableArrayList(manager.getAllTransaksi());
        tabel.setItems(dataTabel);

        // --- PANEL LAPORAN ---
        GridPane paneLaporan = new GridPane();
        paneLaporan.setHgap(20);
        paneLaporan.setVgap(5);
        paneLaporan.setPadding(new Insets(10));
        paneLaporan.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dcdde1; -fx-border-radius: 5;");

        lblSaldoTotal = new Label();
        lblSaldoTotal.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        lblLaporPemasukan = new Label();
        lblLaporPemasukan.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
        lblLaporPengeluaran = new Label();
        lblLaporPengeluaran.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        paneLaporan.add(new Label("Total Saldo Akhir:"), 0, 0);  paneLaporan.add(lblSaldoTotal, 1, 0);
        paneLaporan.add(new Label("Total Pemasukan:"), 0, 1);   paneLaporan.add(lblLaporPemasukan, 1, 1);
        paneLaporan.add(new Label("Total Pengeluaran:"), 2, 1);  paneLaporan.add(lblLaporPengeluaran, 3, 1);

        segarkanLaporanDanFokus();

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(10, 0, 10, 0));

        TextField txtDeskripsi = new TextField();
        txtDeskripsi.setPromptText("Contoh: Beli Paket Data");
        TextField txtJumlah = new TextField();
        txtJumlah.setPromptText("Contoh: 100000");

        ComboBox<Kategori> comboKategori = new ComboBox<>();
        comboKategori.getItems().setAll(Kategori.values());
        comboKategori.setPromptText("-- Pilih Kategori --");

        gridForm.add(new Label("Deskripsi:"), 0, 0);   gridForm.add(txtDeskripsi, 1, 0);
        gridForm.add(new Label("Jumlah (Rp):"), 0, 1);  gridForm.add(txtJumlah, 1, 1);
        gridForm.add(new Label("Kategori:"), 2, 0);     gridForm.add(comboKategori, 3, 0);

        Button btnPemasukan = new Button("Tambah Pemasukan");
        Button btnPengeluaran = new Button("Tambah Pengeluaran");
        btnPemasukan.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnPengeluaran.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnPemasukan.setOnAction(e -> {
            try {
                if (comboKategori.getValue() == null) throw new IllegalArgumentException("Silakan pilih kategori.");
                double jumlah = Double.parseDouble(txtJumlah.getText());

                manager.tambahTransaksi(new Pemasukan(jumlah, txtDeskripsi.getText(), comboKategori.getValue()));

                refreshTabel();
                bersihkanForm(txtDeskripsi, txtJumlah, comboKategori);
            } catch (Exception ex) { tampilkanAlert("Gagal Input", ex.getMessage()); }
        });

        btnPengeluaran.setOnAction(e -> {
            try {
                if (comboKategori.getValue() == null) throw new IllegalArgumentException("Silakan pilih kategori.");
                double jumlah = Double.parseDouble(txtJumlah.getText());

                manager.tambahTransaksi(new Pengeluaran(jumlah, txtDeskripsi.getText(), comboKategori.getValue()));

                refreshTabel();
                bersihkanForm(txtDeskripsi, txtJumlah, comboKategori);
            } catch (Exception ex) { tampilkanAlert("Aksi Ditolak", ex.getMessage()); }
        });

        HBox areaTombol = new HBox(15, btnPemasukan, btnPengeluaran);
        VBox root = new VBox(10, paneLaporan, tabel, gridForm, areaTombol);
        root.setPadding(new Insets(15));

        primaryStage.setTitle("Sistem Manajemen Keuangan Pribadi");
        primaryStage.setScene(new Scene(root, 720, 580));
        primaryStage.show();
    }

    private void refreshTabel() {
        dataTabel.setAll(manager.getAllTransaksi());
        segarkanLaporanDanFokus();
    }

    private void segarkanLaporanDanFokus() {
        lblSaldoTotal.setText(String.format("Rp %,.0f", manager.hitungTotalSaldo()));
        lblLaporPemasukan.setText(String.format("Rp %,.0f", manager.getTotalPemasukan()));

        lblLaporPengeluaran.setText(String.format("Rp %,.0f", manager.getTotalPengeluaran()));

        if (!dataTabel.isEmpty()) {
            tabel.scrollTo(dataTabel.size() - 1);
        }
    }

    private void bersihkanForm(TextField d, TextField j, ComboBox<Kategori> k) {
        d.clear(); j.clear(); k.setValue(null);
    }

    private void tampilkanAlert(String header, String pesan) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Notifikasi Sistem");
        alert.setHeaderText(header);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}