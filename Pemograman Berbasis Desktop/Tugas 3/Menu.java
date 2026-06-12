import java.io.*;
import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();
    private final String FILE_MENU = "daftar_menu.txt";

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public ArrayList<MenuItem> getDaftarMenu() { return daftarMenu; }

    public void tampilkanMenuRestoran() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu restoran masih kosong.");
            return;
        }
        System.out.println("\n--- DAFTAR MENU RESTORAN ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu();
        }
    }

    public void simpanMenuKeFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_MENU))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan || item instanceof Minuman) {
                    writer.println(item.getKategori() + ";" + item.getNama() + ";" + item.getHarga());
                } else if (item instanceof Diskon) {
                    writer.println("Diskon;" + item.getNama() + ";" + ((Diskon) item).getDiskon());
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    public void muatMenuDariFile() {
        File file = new File(FILE_MENU);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String baris;
            daftarMenu.clear();
            while ((baris = reader.readLine()) != null) {
                String[] data = baris.split(";");
                String kategori = data [0];
                String nama = data[1];
                double nilai = Double.parseDouble(data[2]);

                if (kategori.equals("Makanan")) {
                    daftarMenu.add(new Makanan(nama, nilai));
                } else if (kategori.equals("Minuman")) {
                    daftarMenu.add(new Minuman(nama, nilai));
                } else if (kategori.equals("Diskon")) {
                    daftarMenu.add(new Diskon(nama, nilai));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Gagal memuat menu" + e.getMessage());
        }
    }
}
