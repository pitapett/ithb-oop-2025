package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pertemuan1.models.*;
import pertemuan1.database.Database;

public class EnrollmentRepository {

   
    public boolean insert(Enrollment enrollment) {
        String sql = "INSERT INTO matakuliah_mahasiswa (nim, kode_matakuliah, indeks_nilai) VALUES (?, ?, ?)";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enrollment.getMahasiswa().getNim());
            stmt.setString(2, enrollment.getMataKuliah().getKode());
            stmt.setString(3, Character.toString(enrollment.getGrade()));
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
        }
    }


    public List<Enrollment> findByNim(String nim) {
        List<Enrollment> enrollments = new ArrayList<>();
     
        String sql = "SELECT m.nim, m.nama AS nama_mahasiswa, " +
                "mk.kode_matakuliah, mk.nama AS nama_matakuliah, mk.sks, " +
                "j.kode_jurusan, j.nama AS nama_jurusan, " +
                "mm.indeks_nilai " +
                "FROM matakuliah_mahasiswa mm " +
                "JOIN mahasiswa m ON mm.nim = m.nim " +
                "JOIN matakuliah mk ON mm.kode_matakuliah = mk.kode_matakuliah " +
                "JOIN jurusan j ON m.kode_jurusan = j.kode_jurusan " +
                "WHERE mm.nim = ?";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                  
                    Jurusan j = new Jurusan();
                    j.setKode(rs.getString("kode_jurusan"));
                    j.setNama(rs.getString("nama_jurusan"));

                    Mahasiswa m = new Mahasiswa();
                    m.setNim(rs.getString("nim"));
                    m.setNama(rs.getString("nama_mahasiswa"));
                    m.setJurusan(j);

                    MataKuliah mk = new MataKuliah();
                    mk.setKode(rs.getString("kode_matakuliah"));
                    mk.setNama(rs.getString("nama_matakuliah"));
                    mk.setSks(rs.getInt("sks"));
                    mk.setJurusan(j);

                    char grade = rs.getString("indeks_nilai").charAt(0);

                    enrollments.add(new Enrollment(m, mk, grade));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }


    public boolean update(Enrollment enrollment) {
        String sql = "UPDATE matakuliah_mahasiswa SET indeks_nilai = ? WHERE nim = ? AND kode_matakuliah = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Character.toString(enrollment.getGrade()));
            stmt.setString(2, enrollment.getMahasiswa().getNim());
            stmt.setString(3, enrollment.getMataKuliah().getKode());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public boolean delete(String nim, String kodeMataKuliah) {
        String sql = "DELETE FROM matakuliah_mahasiswa WHERE nim = ? AND kode_matakuliah = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            stmt.setString(2, kodeMataKuliah);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
