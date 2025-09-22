package pertemuan1.services;

import java.util.List;
import pertemuan1.models.Jurusan;
import pertemuan1.models.MataKuliah;
import pertemuan1.repository.JurusanRepository;

public class JurusanServices {
    public static void displayAllJurusan(JurusanRepository jurusanRepository) {
        List<Jurusan> listJurusan = jurusanRepository.findAll();
        int counter = 1;
        for (Jurusan jurusan : listJurusan) {
            System.out.println(counter + ". " + jurusan.getKode() + " - " + jurusan.getNama());
            counter++;
        }
    }

    public static void addJurusan(JurusanRepository jurusanRepository) {
        System.out.println("Masukkan kode jurusan: ");
        String kode = appUtils.readLine();
        System.out.println("Masukkan nama jurusan:");
        String nama = appUtils.readLine();

        Jurusan jurusan = new Jurusan();
        jurusan.setKode(kode);
        jurusan.setNama(nama);
        var konfirm = appUtils.konfirmasi("Konfirm tambah jurusan? " + jurusan.getNama());
        if (konfirm) {
            jurusanRepository.insert(jurusan);
            System.out.println("Jurusan berhasil ditambahkan");
        }
    }

    public static void editJurusan(JurusanRepository jurusanRepository) {
        System.out.println("Enter kode of the Jurusan you want to edit: ");
        String kode = appUtils.readLine();
        Jurusan tempJurusan = jurusanRepository.findByKode(kode);
        if (tempJurusan == null) {
            System.out.println("Jurusan with kode " + kode + "doesn't exist");
        }
        System.out.println("Enter nama jurusan baru: ");
        String nama = appUtils.readLine();
        tempJurusan = new Jurusan();
        tempJurusan.setKode(kode);
        tempJurusan.setNama(nama);
        var konfirm = appUtils.konfirmasi("Konfirm edit jurusan? " + tempJurusan.getKode());
        if (konfirm) {
            jurusanRepository.update(tempJurusan);
            System.out.println("Jurusan berhasil diedit");
        }
    }

    public static void deleteJurusan(JurusanRepository jurusanRepository) {
        System.out.println("Masukkan kode jurusan: ");
        String kode = appUtils.readLine();
        Jurusan tempJurusan = jurusanRepository.findByKode(kode);
        if (tempJurusan == null) {
            System.out.println("Jurusan tidak ditemukan");
            return;
        }
        boolean konfirm = appUtils.konfirmasi(
                "Konfirm hapus jurusan? kode: " + tempJurusan.getKode() + ", nama: " + tempJurusan.getNama());
        if (konfirm) {
            jurusanRepository.delete(kode);
            System.out.println("Jurusan berhasil dihapus");
        }
    }

    public static void printMenuJurusan() {
        System.out.println("");
        System.out.println("Menu jurusan:");
        System.out.println("1. Lihat daftar jurusan");
        System.out.println("2. Tambah jurusan");
        System.out.println("3. Edit jurusan");
        System.out.println("4. Hapus jurusan");
        System.out.println("0. Keluar");
    }

}
