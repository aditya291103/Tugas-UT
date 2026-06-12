public class PesananItem {
     private MenuItem menuItem;
     private int jumlah;

     public PesananItem(MenuItem menuItem, int jumlah) {
        this.menuItem = menuItem;
        this.jumlah = jumlah;
     }

     public MenuItem getMenuItem() { return menuItem; }
     public int getJumlah() { return jumlah; }

     public double hitungSubTotal() {
        return menuItem.getHarga() * jumlah;
     }
}