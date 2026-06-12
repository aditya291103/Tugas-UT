public class Diskon extends MenuItem {

    private double diskon;
    
    public Diskon(String nama, double diskon) {
        super(nama, 0, "Diskon");
        this.diskon = diskon;
    }

    public double getDiskon() { return diskon; }

    @Override
    public void tampilMenu() {
        System.out.printf("[%s]  %s - Potongan: %.0f%%%n", getKategori(), getNama(), diskon);
    }
}
