import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static Menu[] daftarMenu = {
        new Menu("Ayam Goreng", 25000, "Makanan"),
        new Menu("Ayam Bakar", 30000, "Makanan"),
        new Menu("Nasi Goreng", 20000, "Makanan"),
        new Menu("Mie Goreng", 15000, "Makanan"),

        new Menu("Teh", 7000, "Minuman"),
        new Menu("Jus Jeruk", 10000, "Minuman"),
        new Menu("Kopi", 12000, "Minuman"),
        new Menu("Es Buah", 15000, "Minuman"),
    };

    static void tampilMenu() {
        System.out.println("========== MENU MAKANAN ==========");
        System.out.println("1. Ayam Goreng = Rp25000");
        System.out.println("2. Ayam Bakar = Rp30000");
        System.out.println("3. Nasi Goreng = Rp20000");
        System.out.println("4. Mie Goreng = Rp15000");

        System.out.println("\n========== MENU MINUMAN ==========");
        System.out.println("5. Teh = Rp7000");
        System.out.println("6. Jus Jeruk = Rp10000");
        System.out.println("7. Kopi = Rp12000");
        System.out.println("8. Es Buah = Rp15000");
    }

    static int hitungHarga(int pilihan, int jumlah) {
        int harga = 0;

        switch (pilihan) {
            case 1:
                harga = daftarMenu[0].harga * jumlah;
                break;
            case 2:
                harga = daftarMenu[1].harga * jumlah;
                break;
            case 3:
                harga = daftarMenu[2].harga * jumlah;
                break;
            case 4:
                harga = daftarMenu[3].harga * jumlah;
                break;
            case 5:
                harga = daftarMenu[4].harga * jumlah;
                break;
            case 6:
                harga = daftarMenu[5].harga * jumlah;
                break;
            case 7:
                harga = daftarMenu[6].harga * jumlah;
                break;
            case 8:
                harga = daftarMenu[7].harga * jumlah;
                break;
        
            default:
                harga = 0;
        }
        return harga;
    }

    static String getNamaMenu(int pilihan) {
        switch (pilihan) {
            case 1:
                return daftarMenu[0].nama;
            case 2:
                return daftarMenu[1].nama;
            case 3:
                return daftarMenu[2].nama;
            case 4:
                return daftarMenu[3].nama;
            case 5:
                return daftarMenu[4].nama;
            case 6:
                return daftarMenu[5].nama;
            case 7:
                return daftarMenu[6].nama;
            case 8:
                return daftarMenu[7].nama;
            default:
                return "-";
        }
    }

    public static void main(String[] args) {
        
        tampilMenu();

        System.out.println("\nInput Pesanan");

        System.out.print("Pesanan 1 (nomor menu): ");
        int p1 = input.nextInt();
        System.out.print("Jumlah: ");
        int j1 = input.nextInt();

        System.out.print("Pesanan 2 (nomor menu): ");
        int p2 = input.nextInt();
        System.out.print("Jumlah: ");
        int j2 = input.nextInt();

        System.out.print("Pesanan 3 (nomor menu): ");
        int p3 = input.nextInt();
        System.out.print("Jumlah: ");
        int j3 = input.nextInt();

        System.out.print("Pesanan 4 (nomor menu): ");
        int p4 = input.nextInt();
        System.out.print("Jumlah: ");
        int j4 = input.nextInt();

        int subTotal1 = hitungHarga(p1, j1);
        int subTotal2 = hitungHarga(p2, j2);
        int subTotal3 = hitungHarga(p3, j3);
        int subTotal4 = hitungHarga(p4, j4);

        int total = subTotal1 + subTotal2 + subTotal3 + subTotal4;

        int jumlahMinuman = 0;
        if (p1 >= 5 && p1 <= 8)
            jumlahMinuman += j1;
        if (p2 >= 5 && p2 <= 8)
            jumlahMinuman += j2;
        if (p3 >= 5 && p3 <= 8)
            jumlahMinuman += j3;
        if (p4 >= 5 && p4 <= 8)
            jumlahMinuman += j4;

        double diskon = 0;
        String bonus = "-";

        int bonus1 = 0;
        int bonus2 = 0;
        int bonus3 = 0;
        int bonus4 = 0;

        if (total > 100000) {
            diskon = total * 0.10;
        }


        if (total > 50000 && jumlahMinuman >= 1) {
            bonus = "Buy 1 Get 1 (Menu Minuman Termurah)";

            int hargaTermurah = 999999;
            int promoIndex = 0;

            if (p1 >= 5 && p1 <= 8 && daftarMenu[p1 - 1].harga < hargaTermurah) {
                hargaTermurah = daftarMenu[p1 -1].harga;
                promoIndex = 1;
            }

            if (p2 >= 5 && p2 <= 8 && daftarMenu[p2 - 1].harga < hargaTermurah) {
                hargaTermurah = daftarMenu[p2 -1].harga;
                promoIndex = 2;
            }

            if (p3 >= 5 && p3 <= 8 && daftarMenu[p3 - 1].harga < hargaTermurah) {
                hargaTermurah = daftarMenu[p3 -1].harga;
                promoIndex = 3;
            }

            if (p4 >= 5 && p4 <= 8 && daftarMenu[p4 - 1].harga < hargaTermurah) {
                hargaTermurah = daftarMenu[p4 -1].harga;
                promoIndex = 4;
            }

            if (promoIndex == 1) 
                bonus1 = j1;
            if (promoIndex == 2) 
                bonus2 = j2;
            if (promoIndex == 3) 
                bonus3 = j3;
            if (promoIndex == 4) 
                bonus4 = j4;
        }

        double setelahDiskon = total - diskon;
        double pajak = setelahDiskon * 0.10;
        int service = 20000;
        double grandTotal = setelahDiskon + pajak + service;

        System.out
                .println("\n========== STRUK PEMBAYARAN ==========");
        System.out
                .println(getNamaMenu(p1) + " x" + j1 + (bonus1 > 0 ? " + bonus " + bonus1 : "") + " - Rp" + subTotal1);
        System.out
                .println(getNamaMenu(p2) + " x" + j2 + (bonus2 > 0 ? " + bonus " + bonus2 : "") + " - Rp" + subTotal2);
        System.out
                .println(getNamaMenu(p3) + " x" + j3 + (bonus3 > 0 ? " + bonus " + bonus3 : "") + " - Rp" + subTotal3);
        System.out
                .println(getNamaMenu(p4) + " x" + j4 + (bonus4 > 0 ? " + bonus " + bonus4 : "") + " - Rp" + subTotal4);

        System.out.println("--------------------------------------");
        System.out.println("Subtotal        : Rp" + total);
        System.out.println("Diskon 10%      : Rp" + diskon);
        System.out.println("Bonus Promo     : " + bonus);
        System.out.println("Pajak 10%       : " + pajak);
        System.out.println("Biaya Pelayanan : Rp" + service);
        System.out.println("Grand Total     : Rp" + grandTotal);
    }
}
