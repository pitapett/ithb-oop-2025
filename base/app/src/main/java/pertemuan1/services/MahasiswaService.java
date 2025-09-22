package pertemuan1.services;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pertemuan1.models.*;
import pertemuan1.repository.EnrollmentRepository;
import pertemuan1.repository.JurusanRepository;
import pertemuan1.repository.MatakuliahRepository;
import pertemuan1.repository.MahasiswaRepository;

public class MahasiswaService {

    public static void displayAll(MahasiswaRepository mahasiswaRepository) {
        System.out.println("\n--- List of All Students ---");
        List<Mahasiswa> listMahasiswa = mahasiswaRepository.findAll();
        if (listMahasiswa.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (int i = 0; i < listMahasiswa.size(); i++) {
            Mahasiswa mahasiswa = listMahasiswa.get(i);
            System.out.printf("%d. %s - %s (%s)%n", i + 1, mahasiswa.getNim(), mahasiswa.getNama(),
                    mahasiswa.getJurusan().getNama());
        }
    }

    public static void addNew(MahasiswaRepository mahasiswaRepository,
            JurusanRepository jurusanRepository) {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter NIM: ");
        String nim = appUtils.scanner.nextLine();
        System.out.print("Enter Name: ");
        String nama = appUtils.scanner.nextLine();
        System.out.print("Enter Major Code: ");
        String kodeJurusan = appUtils.scanner.nextLine();

        Jurusan jurusan = jurusanRepository.findByKode(kodeJurusan);
        if (jurusan == null) {
            System.out.println("ERROR: Major with code '" + kodeJurusan + "' not found.");
            return;
        }

        Mahasiswa newMahasiswa = new Mahasiswa();
        newMahasiswa.setNim(nim);
        newMahasiswa.setNama(nama);
        newMahasiswa.setJurusan(jurusan);

        if (mahasiswaRepository.insert(newMahasiswa)) {
            System.out.println("Student successfully added.");
        } else {

            System.out.println("Failed to add student.");
        }
    }

    public static void edit(MahasiswaRepository mahasiswaRepository,
            JurusanRepository jurusanRepository) {
        System.out.println("\n--- Edit Student ---");
        System.out.print("Enter NIM of student to edit: ");
        String nim = appUtils.scanner.nextLine();

        Mahasiswa mahasiswaToUpdate = mahasiswaRepository.findByNim(nim);
        if (mahasiswaToUpdate == null) {
            System.out.println("ERROR: Student with NIM '" + nim + "' not found.");
            return;
        }

        System.out.printf("Current Name: %s. Enter new name (or press Enter to keep): ", mahasiswaToUpdate.getNama());
        String newName = appUtils.scanner.nextLine();
        if (!newName.isBlank()) {
            mahasiswaToUpdate.setNama(newName);
        }

        System.out.printf("Current Major: %s. Enter new major code (or press Enter to keep): ",
                mahasiswaToUpdate.getJurusan().getKode());
        String newKodeJurusan = appUtils.scanner.nextLine();
        if (!newKodeJurusan.isBlank()) {
            Jurusan newJurusan = jurusanRepository.findByKode(newKodeJurusan);
            if (newJurusan == null) {
                System.out.println("ERROR: Major with code '" + newKodeJurusan + "' not found. Update cancelled.");
                return;
            }
            mahasiswaToUpdate.setJurusan(newJurusan);
        }

        if (mahasiswaRepository.update(mahasiswaToUpdate)) {
            System.out.println("Student successfully updated.");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    public static void delete(MahasiswaRepository mahasiswaRepository) {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter NIM of student to delete: ");
        String nim = appUtils.scanner.nextLine();

        if (mahasiswaRepository.delete(nim)) {
            System.out.println("Student successfully deleted.");
        } else {
            System.out.println("Failed to delete student.");
        }
    }

    public static void addGrade(MahasiswaRepository mahasiswaRepository, MatakuliahRepository matakuliahRepository,
            EnrollmentRepository enrollRepo, List<String> listIndexNilai) {
        System.out.println("\n--- Add Student Grade ---");
        System.out.print("Enter student NIM: ");
        String nim = appUtils.scanner.nextLine();
        Mahasiswa mahasiswa = mahasiswaRepository.findByNim(nim);
        if (mahasiswa == null) {
            System.out.println("ERROR: Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String kodeMk = appUtils.scanner.nextLine();
        MataKuliah mataKuliah = matakuliahRepository.findByKode(kodeMk);
        if (mataKuliah == null) {
            System.out.println("ERROR: Course not found.");
            return;
        }

        System.out.print("Enter grade (" + String.join(", ", listIndexNilai) + "): ");
        char nilai = appUtils.scanner.nextLine().toUpperCase().charAt(0);
        if (!listIndexNilai.contains(nilai)) {
            System.out.println("ERROR: Invalid grade.");
            return;
        }

        Enrollment newEnrollment = new Enrollment(mahasiswa, mataKuliah, nilai);
        if (enrollRepo.insert(newEnrollment)) {
            System.out.println("Grade successfully added.");
        } else {
            System.out.println("Failed to add grade. The student may already have a grade for this course.");
        }
    }

    public static void calculateIpk(EnrollmentRepository enrollRepo,
            Map<String, Double> indexNilaiValue) {
        System.out.println("\n--- Calculate GPA ---");
        System.out.print("Enter student NIM: ");
        String nim = appUtils.scanner.nextLine();

        List<Enrollment> enrollments = enrollRepo.findByNim(nim);

        if (enrollments.isEmpty()) {
            System.out.println("No grades found for this student.");
            return;
        }

        double totalNilai = 0;
        int totalSks = 0;

        System.out.println("\nTranscript for " + enrollments.get(0).getMahasiswa().getNama() + ":");
        for (Enrollment entry : enrollments) {
            MataKuliah mataKuliah = entry.getMataKuliah();
            char nilai = entry.getGrade();
            double nilaiValue = indexNilaiValue.get(nilai);

            System.out.printf("- %s (%d SKS): %s%n", mataKuliah.getNama(), mataKuliah.getSks(), nilai);

            totalSks += mataKuliah.getSks();
            totalNilai += nilaiValue * mataKuliah.getSks();
        }

        double ipk = (totalSks > 0) ? totalNilai / totalSks : 0.0;

        System.out.printf("%nTotal SKS: %d%n", totalSks);
        System.out.printf("GPA (IPK): %.2f%n", ipk);
    }

    public static void printMenuMahasiswa() {
        System.out.println("");
        System.out.println("Menu mahasiswa:");
        System.out.println("1. Lihat daftar mahasiswa");
        System.out.println("2. Tambah mahasiswa");
        System.out.println("3. Edit mahasiswa");
        System.out.println("4. Hapus mahasiswa");
        System.out.println("5. Beri nilai mahasiswa");
        System.out.println("6. Hitung IPK Mahasiswa");
        System.out.println("0. Keluar");
    }
}
