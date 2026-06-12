public class Minuman extends MenuItem {

    public Minuman(String nama, double harga) {
        super(nama, harga, "Minuman");
    }

    @Override
    public void tampilMenu() {
        System.out.printf("[%s] %s - Rp%.2f%n", getKategori(), getNama(), getHarga());
    }
}
