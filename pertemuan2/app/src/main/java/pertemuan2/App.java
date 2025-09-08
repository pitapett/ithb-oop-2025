package pertemuan2;

import java.util.List;
import java.util.Scanner;

import pertemuan2.models.Mahasiswa;
import pertemuan2.repository.MahasiswaRepository;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine();

        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        Mahasiswa mhs = new Mahasiswa(nim, nama);
        MahasiswaRepository.addMahasiswa(mhs);

        List<Mahasiswa> allMahasiswa = MahasiswaRepository.getAllMahasiswa();
        System.out.println("\n📋 Daftar Mahasiswa:");
        for (Mahasiswa m : allMahasiswa) {
            System.out.println(m.nim + " - " + m.nama);
        }

        scanner.close();
    }
}
