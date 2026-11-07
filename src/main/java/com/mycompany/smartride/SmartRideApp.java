package com.mycompany.smartride;

import java.util.Scanner;

// Kriteria 2 & 3: Class, Object, dan Encapsulation
class Tiket {
    // Encapsulation: Atribut disembunyikan dengan modifier private
    private String namaPenumpang;
    private int jarakKm;
    private double tarifPerKm = 5000.0; // Tarif dasar Rp 5.000 per km

    public Tiket(String namaPenumpang, int jarakKm) {
        this.namaPenumpang = namaPenumpang;
        setJarakKm(jarakKm);
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }

    public int getJarakKm() {
        return jarakKm;
    }

    // Encapsulation: Setter dengan validasi Control Flow (if-else)
    public void setJarakKm(int jarakKm) {
        if (jarakKm <= 0) {
            System.out.println("Jarak tidak valid! Otomatis diubah menjadi 1 km.");
            this.jarakKm = 1;
        } else {
            this.jarakKm = jarakKm;
        }
    }

    public double hitungTarif() {
        return jarakKm * tarifPerKm;
    }

    public void tampilkanTiket() {
        System.out.println("Nama   : " + namaPenumpang);
        System.out.println("Jarak  : " + jarakKm + " km");
        System.out.println("Tarif  : Rp " + hitungTarif());
    }
}

// Kriteria 4: Inheritance (Pewarisan)
class TiketPelajar extends Tiket {
    private double diskon = 0.5; // Diskon 50% untuk pelajar

    public TiketPelajar(String namaPenumpang, int jarakKm) {
        super(namaPenumpang, jarakKm); // Memanggil konstruktor Superclass
    }

    // Method Overriding untuk memodifikasi cara hitung tarif
    @Override
    public double hitungTarif() {
        double tarifAwal = super.hitungTarif();
        return tarifAwal - (tarifAwal * diskon);
    }

    @Override
    public void tampilkanTiket() {
        System.out.println("[TIKET PELAJAR - DISKON 50%]");
        super.tampilkanTiket(); // Memanggil method tampilkanTiket milik induk
    }
}

public class SmartRideApp {
    public static void main(String[] args) {
        // Kriteria 1: Input Scanner
        Scanner scanner = new Scanner(System.in);
        Tiket[] daftarPesanan = new Tiket[10]; // Array untuk menyimpan objek
        int jumlahPesanan = 0;
        boolean isRunning = true;

        System.out.println("=== SISTEM TIKET SMART RIDE ===");

        // Kriteria 1: Control Flow (While loop & Switch-case)
        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Pesan Tiket");
            System.out.println("2. Lihat Daftar Pesanan");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    if (jumlahPesanan < daftarPesanan.length) {
                        System.out.print("Masukkan Nama Penumpang: ");
                        String nama = scanner.nextLine();
                        
                        System.out.print("Masukkan Jarak Tempuh (km): ");
                        int jarak = scanner.nextInt();
                        
                        System.out.println("Pilih Kategori Penumpang:");
                        System.out.println("1. Reguler");
                        System.out.println("2. Pelajar");
                        System.out.print("Pilihan (1/2): ");
                        int jenis = scanner.nextInt();
                        scanner.nextLine();

                        // Kriteria 2: Instansiasi Object (Polymorphism)
                        if (jenis == 2) {
                            daftarPesanan[jumlahPesanan] = new TiketPelajar(nama, jarak);
                        } else {
                            daftarPesanan[jumlahPesanan] = new Tiket(nama, jarak);
                        }
                        
                        jumlahPesanan++;
                        System.out.println("Sukses! Tiket berhasil dipesan.");
                    } else {
                        System.out.println("Maaf, kapasitas pesanan penuh!");
                    }
                    break;
                case 2:
                    System.out.println("\n--- DAFTAR PESANAN TIKET ---");
                    if (jumlahPesanan == 0) {
                        System.out.println("Belum ada tiket yang dipesan.");
                    } else {
                        // Kriteria 1: Control Flow (For loop)
                        for (int i = 0; i < jumlahPesanan; i++) {
                            System.out.println("Pesanan ke-" + (i + 1));
                            daftarPesanan[i].tampilkanTiket();
                            System.out.println("----------------------------");
                        }
                    }
                    break;
                case 3:
                    isRunning = false;
                    System.out.println("Terima kasih telah menggunakan SmartRide!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                    break;
            }
        }
        scanner.close();
    }
}