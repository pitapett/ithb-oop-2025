package pertemuan1.models;


public class Enrollment {
    private Mahasiswa mahasiswa;
    private MataKuliah mataKuliah;
    private char grade;

    public Mahasiswa getMahasiswa(){
        return this.mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(MataKuliah mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public char getGrade() {
        return grade;
    }
}