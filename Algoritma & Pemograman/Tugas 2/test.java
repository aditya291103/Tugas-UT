import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int[] gaji = {5000000, 6500000, 9500000};

        int[] persenLembur = {30, 32, 34, 36, 38};

        int indexGaji = -1;
        String golongan;
        int jamLembur;
        int gajiPokok, persentase, gajiLembur, totalGaji;

        // double gajiPokok = 0, persentase = 0, hasilLembur, jumlahPenghasilan;
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));

        System.out.print("Masukkan golongan (A/B/C): ");
        golongan = input.next().toUpperCase();

        System.out.print("Masukkan jam lembur: ");
        jamLembur = input.nextInt();

        if (golongan.equals("A")) {
            indexGaji = 0;
        } else if (golongan.equals("B")) {
            indexGaji = 1;
        } else if (golongan.equals("C")) {
            indexGaji = 2;
        } else {
            System.out.println("Golongan tidak valid");
            input.close();
            return;
        }

        if (jamLembur == 1) {
            persentase = persenLembur[0];
        } else if (jamLembur == 2) {
            persentase = persenLembur[1];
        } else if (jamLembur == 3) {
            persentase = persenLembur[2];
        } else if (jamLembur == 4) {
            persentase = persenLembur[3];
        } else if (jamLembur >= 5) {
            persentase = persenLembur[4];
        } else {
            System.out.println("Jam lembur tidak valid");
            input.close();
            return;
        }
        gajiPokok = gaji[indexGaji];
        gajiLembur = gajiPokok * persentase;
        totalGaji = gajiPokok + gajiLembur;

jps
        System.out.println("Gaji Pokok: " + rupiah.format(gajiPokok));
        System.out.println("Persentase: " + rupiah.format(persentase));
        System.out.println("Jumlah Hasil Lembur: " + rupiah.format(gajiLembur));
        System.out.println("Jumlah Penghasilan: " + rupiah.format(totalGaji));
        input.close();
    }
}