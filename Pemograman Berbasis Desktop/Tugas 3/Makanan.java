public class Makanan extends MenuItem {

    public Makanan(String nama, double harga) {
        super(nama, harga, "Makanan");
    }

    @Override
    public void tampilMenu() {
        System.out.printf("[%s] %s - Rp%.2f%n", getKategori(), getNama(), getHarga());
    }
}
