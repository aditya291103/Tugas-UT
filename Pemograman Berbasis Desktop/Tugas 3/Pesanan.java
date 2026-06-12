import java.io.*;
import java.util.ArrayList;

public class Pesanan {
    private ArrayList<PesananItem> daftarPesanan = new ArrayList<>();
    private final String FILE_STRUK = "struk_pemesanan.txt";

    public void tambahPesanan(MenuItem item, int jumlah) {
        daftarPesanan.add(new PesananItem(item, jumlah));
    }

    public double hitungTotal() {
        double totalKotor = 0;
        double totalDiskonPersen = 0;

        for (PesananItem pItem : daftarPesanan) {
            MenuItem item = pItem.getMenuItem();
            if (item instanceof Diskon) {
                totalDiskonPersen += ((Diskon) item).getDiskon() * pItem.getJumlah();
            } else {
                totalKotor += pItem.hitungSubTotal();
            }
        }

        double potongan = totalKotor * (totalDiskonPersen / 100);
        return totalKotor - potongan;

    }

    public void tampilkanRincianSementara() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan yang dicatat");
            return;
        }

        System.out.println("\n--- RINCIAN BIAYA SEMENTARA ---");
        double totalKotor = 0;
        double totalDiskon = 0;

        for (PesananItem pItem : daftarPesanan) {
            MenuItem item = pItem.getMenuItem();
            if (item instanceof Diskon) {
                System.out.printf("%-20s x%-5d %-15s%n", item.getNama(), pItem.getJumlah(), "(Promo)");
                totalDiskon += ((Diskon) item).getDiskon() * pItem.getJumlah();
            } else {
                System.out.printf("%-20s x%-5d Rp%.2f%n", item.getNama(), pItem.getJumlah(),pItem.hitungSubTotal());
                totalKotor += pItem.hitungSubTotal();
            }

        }

        double nilaiPotongan =totalKotor * (totalDiskon / 100);
        double totalAkhir = totalKotor - nilaiPotongan;
        
        System.out.println("----------------------------------");
        System.out.printf("Subtotal:     Rp%.2f%n", totalKotor);
        System.out.printf("Total Diskon: %.0f%%%n", totalDiskon);
        System.out.printf("Total Bayar:  Rp%.2f%n", totalAkhir);
        System.out.println("----------------------------------");
    }

    public void cetakStruk() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan.");
            return;
        }

        StringBuilder struk = new StringBuilder();
        struk.append("\n=================================\n");
        struk.append("          STRUK PESANAN          \n");
        struk.append("=================================\n");

        double totalKotor = 0;
        double totalDiskon = 0;

        for (PesananItem pItem : daftarPesanan) {
            MenuItem item = pItem.getMenuItem();
            if (item instanceof Diskon) {
                struk.append(String.format("%-15s x%-5d %-15s%n", item.getNama(), pItem.getJumlah(), "(Promo)"));
                totalDiskon += ((Diskon) item).getDiskon() * pItem.getJumlah();
            } else {
                struk.append(String.format("%-15s x%-5d Rp%.2f%n", item.getNama(), pItem.getJumlah(), pItem.hitungSubTotal()));
                totalKotor += pItem.hitungSubTotal();
            }
        }

        double nilaiPotongan = totalKotor * (totalDiskon / 100);
        double totalAkhir = totalKotor - nilaiPotongan;

        struk.append("---------------------------------\n");
        struk.append(String.format("Subtotal:     Rp%.2f%n", totalKotor));
        struk.append(String.format("Total Diskon: %.0f%%%n", totalDiskon));
        struk.append(String.format("Total Bayar:  Rp%.2f%n", totalAkhir));
        struk.append("=================================\n");

        System.out.print(struk.toString());

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_STRUK))) {
            writer.print(struk.toString());
            System.out.println("Struk berhasil disimpan ke '" + FILE_STRUK + "'");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk ke file: " + e.getMessage());
        }
    }
}
