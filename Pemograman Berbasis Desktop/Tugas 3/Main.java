import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menuRestoran = new Menu();
        Pesanan pesananAktif = new Pesanan();

        menuRestoran.muatMenuDariFile();
        int pilihan = 0;

        while (pilihan != 6) {
            System.out.println("\n === APLIKASI MANAJEMEN RESTORAN ===");
            System.out.println("1. Tambah Item ke Menu");
            System.out.println("2. Tampilkan Menu Restoran");
            System.out.println("3. Terima Pesanan Pelanggan");
            System.out.println("4. Hitung Total Biaya Pesanan");
            System.out.println("5. Cetak dan Simpan Struk");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu (1-6): ");

            try {
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input salah! Masukkan angka 1 sampai 6.");
                continue;
            }

            switch (pilihan) {
                case 1:
                    System.out.println("\n=== TAMBAH ITEM MENU ===");
                    System.out.println("1. Makanan\n2. Minuman\n3. Diskon");
                    System.out.print("Pilih Jenis (1-3): ");
                    int jenis = Integer.parseInt(scanner.nextLine());

                    System.out.print("Masukkan  Nama: ");
                    String nama = scanner.nextLine();

                    if (jenis == 1) {
                        System.out.print("Masukkan Harga: ");
                        double harga = Double.parseDouble(scanner.nextLine());
                        menuRestoran.tambahItem(new Makanan(nama, harga));
                    } else if (jenis == 2) {
                        System.out.print("Masukkan Harga: ");
                        double harga = Double.parseDouble(scanner.nextLine());
                        menuRestoran.tambahItem(new Minuman(nama, harga));
                    } else if (jenis == 3) {
                        System.out.print("Masukkan Besar Diskon (%)");
                        double diskon = Double.parseDouble(scanner.nextLine());
                        menuRestoran.tambahItem(new Diskon(nama, diskon));
                    }
                    menuRestoran.simpanMenuKeFile();
                    System.out.println("Item berhasil ditambahkan!");
                    break;

                case 2:
                    menuRestoran.tampilkanMenuRestoran();
                    break;
                
                case 3:
                    if (menuRestoran.getDaftarMenu().isEmpty()) {
                        System.out.println("Menu restoran masih kosong");
                        break;
                    }

                    while (true) {
                        menuRestoran.tampilkanMenuRestoran();
                        System.out.println("0. Selesai / Kembali ke Menu Utama");
                        System.out.print("Pilih Nomor Menu untuk dipesan: ");

                        try {
                            int noMenu = Integer.parseInt(scanner.nextLine());

                            if (noMenu == 0) {
                            System.out.println("Selesai mencatat pesanan pelanggan.");
                            break;
                            }

                            int indeksMenu = noMenu - 1;
                            if (indeksMenu < 0 || indeksMenu >= menuRestoran.getDaftarMenu().size()) {
                                throw new IndexOutOfBoundsException("Nomor menu tersebut tidak ada dalam dafar");
                            }

                            MenuItem itemDipilih = menuRestoran.getDaftarMenu().get(indeksMenu);
                            int qty = 1;

                            if (!(itemDipilih instanceof Diskon)) {
                                System.out.print("Masukkan jumlah porsi/item: ");
                                qty = Integer.parseInt(scanner.nextLine());

                                if (qty <= 0) {
                                    System.out.println("Jumlah item harus lebih dari 0!");
                                    continue;
                                }
                            } else {
                                System.out.println("=> Sistem mendeteksi jenis Diskon. Jumlah diset otomatis = 1.");
                            }

                            pesananAktif.tambahPesanan(itemDipilih, qty);
                            System.out.println("=> BERHASIL: " + qty + " " + itemDipilih.getNama() + " ditambahkan ke daftar belanja.");
                            System.out.println("--------------------------------------------------");
                        } catch ( IndexOutOfBoundsException e) {
                            System.out.println("Error Transaksi: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Input harus berupa angka/number.");
                        }
                    }
                    break;
                
                case 4:
                    pesananAktif.tampilkanRincianSementara();
                    break;
                
                case 5:
                    pesananAktif.cetakStruk();
                    break;

                case 6:
                    System.out.println("Keluar program. Sampai jumpa");
                    break;

                default:
                    System.out.println("Pilihan diluar jangkauan menu utama.");
            }
        }
        scanner.close();
    }
}