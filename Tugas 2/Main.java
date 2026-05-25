import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static Menu[] daftarMenu = new Menu[100];

    static int jumlahMenu = 8;

    static int[] menuPesanan = new int[100];
    static int[] jumlahPesanan = new int[100];
    static int totalPesanan = 0;

    public static void main(String[] args) {
        
        daftarMenu[0] = new Menu("Ayam Goreng", 25000, "Makanan");
        daftarMenu[1] = new Menu("Ayam Bakar", 30000, "Makanan");
        daftarMenu[2] = new Menu("Nasi Goreng", 20000, "Makanan");
        daftarMenu[3] = new Menu("Mie Goreng", 15000, "Makanan");

        daftarMenu[4] = new Menu("Teh", 7000, "Minuman");
        daftarMenu[5] = new Menu("Jus Jeruk", 10000, "Minuman");
        daftarMenu[6] = new Menu("Kopi", 12000, "Minuman");
        daftarMenu[7] = new Menu("Es Buah", 15000, "Minuman");

        menuUtama();
    }

    static void menuUtama() {
        int pilih;

        do {
            System.out.println("\n============ MENU UTAMA ==============");
            System.out.println("1. Pesan Menu");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");

            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();

            switch (pilih) {

                case 1:
                    pesanMenu();
                    break;
                
                case 2:
                    manajemenMenu();
                    break;

                case 3:
                    System.out.println("Terima kasih.");
                    break;
            
                default:
                    System.out.println("Pilihan tidak tersedia");
            }
        } while (pilih != 3);
    }

    static void tampilMenu() {

        System.out.println("\n ============ MENU MAKANAN ============");

        for (int i = 0; i < jumlahMenu; i++) {

            if (daftarMenu[i].kategori.equalsIgnoreCase("Makanan")) {

                System.out.println((i + 1) + ". " + daftarMenu[i].nama + " = Rp" + daftarMenu[i].harga);
            }
        }

        System.out.println("\n ============ MENU MINUMAN ============");

        for (int i = 0; i < jumlahMenu; i++) {

            if (daftarMenu[i].kategori.equalsIgnoreCase("Minuman")) {

                System.out.println((i + 1) + ". " + daftarMenu[i].nama + " = Rp" + daftarMenu[i].harga);
            }
        }
    }

    static void pesanMenu() {

        totalPesanan = 0;

        tampilMenu();

        while (true) {

            System.out.print("\nMasukkan nomor menu (0 untuk selesai): ");
            int pilih = input.nextInt();

            if (pilih == 0) {
                break;
            }

            if (pilih < 1 || pilih > jumlahMenu) {

                System.out.println("Menu tidak tersedia!");
                continue;
            }

            System.out.print("Jumlah pesanan: ");
            int jumlah = input.nextInt();

            menuPesanan[totalPesanan] = pilih;
            jumlahPesanan[totalPesanan] = jumlah;

            totalPesanan++;

            System.out.println("Pesanan berhasil ditambahkan.");
        }

        hitungTotal();
    }

    static int hitungHarga(int pilihan, int jumlah) {

        return daftarMenu[pilihan - 1].harga * jumlah;
    }

    static void hitungTotal() {

        int total = 0;

        int bonusIndex = -1;
        int hargaTermurah = 999999;

        for (int i = 0; i < totalPesanan; i++) {

            int indexMenu = menuPesanan[i] - 1;

            if (daftarMenu[indexMenu].kategori.equalsIgnoreCase("Minuman")) {
                
                if (daftarMenu[indexMenu].harga < hargaTermurah) {
                    
                    hargaTermurah = daftarMenu[indexMenu].harga;

                    bonusIndex = i;
                }
            }
        }

        for (int i = 0; i <totalPesanan; i++) {

            total += hitungHarga(
                menuPesanan[i],
                jumlahPesanan[i]);
        }

        double diskon = 0;

        if (total > 100000) {

            diskon = total * 0.10;
        }

        String bonusPromo = "-";

        int[] bonusJumlah = new int[100];

        if (total > 50000 && bonusIndex != -1) {
            
            bonusPromo = "Buy 1 Get 1 (Menu Minuman Termurah)";

            bonusJumlah[bonusIndex] = jumlahPesanan[bonusIndex];
        }

        double setelahDiskon = total - diskon;

        double pajak = setelahDiskon * 0.10;

        int service = 20000;

        double grandTotal = setelahDiskon + pajak + service;

        System.out.println("\n========= STRUK PEMBAYARAN =========");

        for (int i = 0; i <totalPesanan; i++) {

            int subTotal = hitungHarga(
                menuPesanan[i],
                jumlahPesanan[i]);

                System.out.print(daftarMenu[menuPesanan[i] - 1].nama +  " x" + jumlahPesanan[i]);

                if (bonusJumlah[i] > 0) {
                    
                    System.out.print(" + bonus " + bonusJumlah[i]);
                }

                System.out.println(" - Rp" + subTotal);
        }

        System.out.println("---------------------------------------------------");

        System.out.println("Subtotal         : Rp" + total);
        System.out.println("Diskon 10%       : Rp" + diskon);
        System.out.println("Bonus Promo      : " + bonusPromo);
        System.out.println("Pajak 10%        : Rp" + pajak);
        System.out.println("Biaya Pelayanan  : Rp" + service);
        System.out.println("Grand Total      : Rp" + grandTotal);
    }

    static void manajemenMenu() {
        int pilih;

        do {
            System.out.println("\n============ MANAJEMEN MENU ============");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");

            System.out.print("Pilih menu: ");
            pilih = input.nextInt();

            switch (pilih) {
                case 1:
                    tambahMenu();
                    break;

                case 2:
                    ubahHarga();
                    break;

                case 3:
                    hapusMenu();
                    break;

                case 4:
                    break;
            
                default:
                    System.out.println("Pilihan tidak tersedia!");
            }
        } while (pilih != 4); 
    }

    static void tambahMenu() {
        input.nextLine();

        System.out.print("Nama menu: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        int harga = input.nextInt();
        input.nextLine();

        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();

        daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);

        jumlahMenu++;

        System.out.println("Menu berhasil ditambahkan.");
    }

    static void ubahHarga() {

        tampilSemuaMenu();

        int nomor;

        while (true) {
            
            System.out.print("Pilih nomor menu: ");
            nomor = input.nextInt();

            if (nomor >= 1 && nomor <=jumlahMenu) {
                break;
            }

            System.out.println("Nomor menu tidak valid!");
        }

        input.nextLine();

        System.out.print("Yakin ingin mengubah? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            
            System.out.print("Harga baru: ");
            int hargaBaru = input.nextInt();

            daftarMenu[nomor - 1].harga = hargaBaru;

            System.out.println("Harga berhasil diubah.");

        }

        else {

            System.out.println("Perubahan dibatalkan.");
        }
    }

    static void hapusMenu() {

        tampilSemuaMenu();

        int nomor;

        while (true) {
            System.out.print("Pilih nomor menu: ");
            nomor = input.nextInt();

            if (nomor >= 1 && nomor <=jumlahMenu) {
                break;
            }

            System.out.println("Nomor menu tidak valid!");
        }

        input.nextLine();

        System.out.print("Yakin ingin menghapus menu? (Ya/Tidak)");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            
            for (int i = nomor -1; i < jumlahMenu -1; i++) {

                daftarMenu[i] = daftarMenu[i + 1];
            }

            daftarMenu[jumlahMenu - 1] = null;

            jumlahMenu--;

            System.out.println("Menu berhasil dihapus.");
        }

        else {

            System.out.println("Penghapusan dibatalkan.");
        }
    }

    static void tampilSemuaMenu() {
        System.out.println("\n============ DAFTAR MENU =========");

        for (int i = 0; i < jumlahMenu; i++) {

            System.out.println((i + 1) + ". " + daftarMenu[i].nama + " | " + daftarMenu[i].kategori + " | Rp" + daftarMenu[i].harga);
        }
    }
}
        
        
