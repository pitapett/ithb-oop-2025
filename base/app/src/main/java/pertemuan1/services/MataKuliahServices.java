package pertemuan1.services;

import java.util.List;
import pertemuan1.models.MataKuliah;
import pertemuan1.models.Jurusan;
import pertemuan1.repository.JurusanRepository;
import pertemuan1.repository.MatakuliahRepository;

public class MataKuliahServices {
    public static void listAllMataKuliah(MatakuliahRepository matakuliahRepository) {
        List<MataKuliah> listMataKuliah = matakuliahRepository.findAll();
        int counter = 1;
        for (MataKuliah mataKuliah : listMataKuliah) {
            System.out.println(counter + ". " + mataKuliah.getKode() + " - " + mataKuliah.getNama() + " - Jumlah SKS:"
                    + mataKuliah.getSks() + " - " + "Jurusan: " + mataKuliah.getJurusan().getNama());
            counter++;
        }
    }

    public static void listMataKuliahByJurusan(MatakuliahRepository matakuliahRepository) {
        System.out.println("Masukkan kode jurusan: ");
        String kodeJurusan = appUtils.readLine();
        List<MataKuliah> listMataKuliah = matakuliahRepository.findByJurusan(kodeJurusan);
        int counter = 1;
        for (MataKuliah mataKuliah : listMataKuliah) {
            System.out.println(counter + ". " + mataKuliah.getKode() + " - " + mataKuliah.getNama() + " - Jumlah SKS:"
                    + mataKuliah.getSks() + " - " + "Jurusan: " + mataKuliah.getJurusan().getNama());
            counter++;
        }
    }

    public static void addMataKuliah(MatakuliahRepository matakuliahRepository, JurusanRepository jurusanRepository) {
        System.out.println("ADD MATA KULIAH");
        MataKuliah mataKuliah = new MataKuliah();
        System.out.println("Masukkan Jurusan: ");
        String kodeJurusan = appUtils.readLine();
        Jurusan checkJurusan = jurusanRepository.findByKode(kodeJurusan);
        if (checkJurusan == null) {
            System.out.println("Jurusan " + kodeJurusan + " doesn't exist");
            return;
        }
        mataKuliah.setJurusan(checkJurusan);
        System.out.println("Masukkan kode mata kuliah: ");
        mataKuliah.setKode(appUtils.readLine());
        System.out.println("Masukkan nama mata kuliah: ");
        mataKuliah.setNama(appUtils.readLine());
        System.out.println("Masukkan jumlah sks: ");
        mataKuliah.setSks(appUtils.readInt());

        var konfirm = appUtils.konfirmasi("Konfirm tambah mata kuliah? " + mataKuliah);
        if (konfirm) {
            matakuliahRepository.insert(mataKuliah);
            System.out.println("Mata kuliah berhasil ditambahkan");
        }
    }

    public static void editMataKuliah(MatakuliahRepository matakuliahRepository, JurusanRepository jurusanRepository) {
        System.out.println("EDIT MATA KULIAH");
        System.out.println("Masukkan kode matakuliah: ");
        String kodeMataKuliah = appUtils.readLine();
        MataKuliah tempMataKuliah = matakuliahRepository.findByKode(kodeMataKuliah);
        if (tempMataKuliah == null) {
            System.out.println("Mata Kuliah " + kodeMataKuliah + " doesn't exist");
            return;
        }
        System.out.println("Update nama mata kuliah: ");
        tempMataKuliah.setNama(appUtils.readLine());
        System.out.println("Update jumlah sks: ");
        tempMataKuliah.setSks(appUtils.readInt());
        System.out.println("Masukkan Jurusan: ");
        String kodeJurusan = appUtils.readLine();
        Jurusan checkJurusan = jurusanRepository.findByKode(kodeJurusan);
        if (checkJurusan == null) {
            System.out.println("Jurusan " + kodeJurusan + " doesn't exist");
            return;
        }
        tempMataKuliah.setJurusan(checkJurusan);
        var konfirm = appUtils.konfirmasi("Konfirm edit mata kuliah? " + tempMataKuliah.getKode());
        if (konfirm) {
            matakuliahRepository.update(tempMataKuliah);
            System.out.println("Mata kuliah berhasil diedit");
        }

    }

    public static void deleteMataKuliah(MatakuliahRepository matakuliahRepository) {
        System.out.println("DELETE MATA KULIAH");
        System.out.println("Masukkan kode matakuliah: ");
        String kodeMataKuliah = appUtils.readLine();
        MataKuliah toBeDeletedMataKuliah = matakuliahRepository.findByKode(kodeMataKuliah);

        if (toBeDeletedMataKuliah == null) {
            System.out.println("Mata Kuliah " + kodeMataKuliah + " doesn't exist");
            return;
        }
        boolean konfirm = appUtils.konfirmasi("Konfirm hapus mata kuliah? " + toBeDeletedMataKuliah.getKode());
        if (konfirm) {
            matakuliahRepository.delete(toBeDeletedMataKuliah.getKode());
            System.out.println("Mata kuliah berhasil dihapus");
        }
    }

    public static void printMenuMataKuliah() {
        System.out.println("");
        System.out.println("Menu mata kuliah:");
        System.out.println("1. List mata kuliah");
        System.out.println("2. Buat mata kuliah baru");
        System.out.println("3. Edit mata kuliah");
        System.out.println("4. Hapus mata kuliah");
        System.out.println("0. Keluar");
    }
}
