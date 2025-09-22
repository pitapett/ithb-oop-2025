package pertemuan1.services;

import java.util.Scanner;

public class appUtils {
    public static final Scanner scanner = new Scanner(System.in);

    public static int readInt() {
        while (!appUtils.scanner.hasNextInt()) {
            System.out.println("Input tidak valid. Silakan masukkan angka.");
            appUtils.scanner.next();
        }
        int result = scanner.nextInt();
        appUtils.scanner.nextLine();
        return result;
    }

    public static String readLine() {
        String result = scanner.nextLine();
        return result;
    }

    public static boolean konfirmasi(String text) {
        System.out.println(text + " (Y/N)");
        String konfirmasi = readLine();
        return konfirmasi.toLowerCase().equals("y");
    }
}
