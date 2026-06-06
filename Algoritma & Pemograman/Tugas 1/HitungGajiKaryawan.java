import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class HitungGajiKaryawan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String golongan;
        int jamLembur;
        double gajiPokok = 0, persentase = 0, gajiLembur, jumlahPenghasilan;

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));

        // MULAI

        // Input data
        System.out.print("Masukkan Golongan (A/B/C): ");
        golongan = input.next().toUpperCase();

        System.out.print("Masukkan Jam Lembur: ");
        jamLembur = input.nextInt();

        // Cek golongan
        if (golongan.equals("A")) {
            gajiPokok = 5000000;
        } else if (golongan.equals("B")) {
            gajiPokok = 6500000;
        } else if (golongan.equals("C")) {
            gajiPokok = 9500000;
        } else {
            System.out.println("Golongan tidak valid!");
            input.close();
            return; // STOP program
        }

        // Cek jam lembur
        if (jamLembur == 1) {
            persentase = 0.30;
        } else if (jamLembur == 2) {
            persentase = 0.32;
        } else if (jamLembur == 3) {
            persentase = 0.34;
        } else if (jamLembur == 4) {
            persentase = 0.36;
        } else if (jamLembur >= 5) {
            persentase = 0.38;
        } else {
            System.out.println("Jam lembur tidak valid!");
            input.close();
            return; // STOP program
        }

        // Perhitungan
        gajiLembur = gajiPokok * persentase;
        jumlahPenghasilan = gajiPokok + gajiLembur;

        // Output
        System.out.println("Gaji Pokok: " + rupiah.format(gajiPokok));
        System.out.println("Persentase Lembur: " + rupiah.format(persentase));
        System.out.println("Jumlah Gaji Lembur: " + rupiah.format(gajiLembur));
        System.out.println("Jumlah Penghasilan: " + rupiah.format(jumlahPenghasilan));

        input.close();

        // SELESAI
    }
}