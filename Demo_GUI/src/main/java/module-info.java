module com.example.demo_gui {
    requires javafx.controls;
    requires javafx.fxml;

    exports id.my.keuangan.app;
    opens id.my.keuangan.app to javafx.graphics, javafx.fxml;
    opens id.my.keuangan.model to javafx.base;
}