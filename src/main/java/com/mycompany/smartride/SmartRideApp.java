package com.mycompany.smartride;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class Tiket {
    private String namaPenumpang;
    private int jarakKm;
    private double tarifPerKm = 5000.0;

    public Tiket(String namaPenumpang, int jarakKm) {
        this.namaPenumpang = namaPenumpang;
        this.jarakKm = jarakKm;
    }

    public double hitungTarif() {
        return jarakKm * tarifPerKm;
    }

    public String getInfo() {
        return namaPenumpang + " | " + jarakKm + " km | Rp " + String.format("%,.0f", hitungTarif());
    }
}

class TiketPelajar extends Tiket {
    private double diskon = 0.5;

    public TiketPelajar(String namaPenumpang, int jarakKm) {
        super(namaPenumpang, jarakKm);
    }

    @Override
    public double hitungTarif() {
        return super.hitungTarif() - (super.hitungTarif() * diskon);
    }

    @Override
    public String getInfo() {
        return "[PELAJAR] " + super.getInfo();
    }
}

public class SmartRideApp extends Application {

    @Override
    public void start(Stage stage) {

        Label lblJudul = new Label("🚌 SMART RIDE");
        lblJudul.setMaxWidth(Double.MAX_VALUE);
        lblJudul.setStyle("-fx-background-color: #3f3f3f; -fx-text-fill: white;"
                + "-fx-font-size: 22px; -fx-font-weight: bold; -fx-padding: 15 20;");


        TextField tfNama = new TextField();
        tfNama.setPromptText("Contoh: Budi");
        TextField tfJarak = new TextField();
        tfJarak.setPromptText("Contoh: 10");
        CheckBox cbPelajar = new CheckBox("Penumpang Pelajar (diskon 50%)");

        Button btnPesan = new Button("Pesan Tiket");
        btnPesan.setMaxWidth(Double.MAX_VALUE);
        btnPesan.setStyle("-fx-background-color: #437a5b; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 20; -fx-cursor: hand;");

        ListView<String> listPesanan = new ListView<>();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Nama Penumpang:"), 0, 0);
        form.add(tfNama, 1, 0);
        form.add(new Label("Jarak (km):"), 0, 1);
        form.add(tfJarak, 1, 1);

        VBox isi = new VBox(12, form, cbPelajar, btnPesan,
                new Label("Daftar Pesanan:"), listPesanan);
        isi.setPadding(new Insets(20));

        VBox root = new VBox(lblJudul, isi);
        root.setStyle("-fx-background-color: #f1f5f9;");

        btnPesan.setOnAction(e -> {
            String nama = tfNama.getText();
            String jarakText = tfJarak.getText();

            if (nama.isEmpty() || jarakText.isEmpty()) {
                tampilkanAlert(AlertType.WARNING, "Nama dan jarak wajib diisi!");
                return;
            }

            if (listPesanan.getItems().size() >= 10) {
                tampilkanAlert(AlertType.WARNING, "Kapasitas pesanan penuh!");
                return;
            }

            try {
                int jarak = Integer.parseInt(jarakText);

                if (jarak <= 0) {
                    tampilkanAlert(AlertType.WARNING, "Jarak harus lebih dari 0!");
                    return;
                }

                Tiket tiket;
                if (cbPelajar.isSelected()) {
                    tiket = new TiketPelajar(nama, jarak);
                } else {
                    tiket = new Tiket(nama, jarak);
                }

                listPesanan.getItems().add(tiket.getInfo());
                tampilkanAlert(AlertType.INFORMATION, "Tiket berhasil dipesan!");

                tfNama.clear();
                tfJarak.clear();
                cbPelajar.setSelected(false);

            } catch (NumberFormatException ex) {
                tampilkanAlert(AlertType.ERROR, "Jarak harus berupa angka!");
            }
        });

        stage.setTitle("Smart Ride");
        stage.setScene(new Scene(root, 420, 520));
        stage.show();
    }

    private void tampilkanAlert(AlertType tipe, String pesan) {
        Alert alert = new Alert(tipe);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}