package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pertemuan1.database.Database;

import pertemuan1.models.Mahasiswa;
import pertemuan1.models.Jurusan;

public class MahasiswaRepository {
    private JurusanRepository jurusanRepository = new JurusanRepository();

    public boolean insert(Mahasiswa mahasiswa) {
        Jurusan existingJurusan = jurusanRepository.findByKode(mahasiswa.getJurusan().getKode());
        if (existingJurusan == null) {
            System.out.println("Jurusan " + mahasiswa.getJurusan().getKode() + "doesn't exist in the database");
            return false;
        }

        String sql = "INSERT INTO mahasiswa (nim, nama, kode_jurusan) VALUES (?,?,?)";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mahasiswa.getNim());
            stmt.setString(2, mahasiswa.getNama());
            stmt.setString(3, mahasiswa.getJurusan().getKode());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Mahasiswa> findAll() {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT mhs.nim, mhs.nama, j.kode_jurusan, j.nama AS nama_jurusan FROM mahasiswa mhs JOIN jurusan j ON mhs.kode_jurusan = j.kode_jurusan";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa();
                Jurusan j = new Jurusan();
                j.setKode(rs.getString("kode_jurusan"));
                j.setNama(rs.getString("nama_jurusan"));

                mhs.setNim((rs.getString("nim")));
                mhs.setNama(rs.getString("nama"));
                mhs.setJurusan(j);
                list.add(mhs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // handling list nilai ada di app
    public Mahasiswa findByNim(String nim) {
        String sql = "SELECT m.nim, m.nama, j.kode_jurusan, j.nama AS nama_jurusan FROM mahasiswa m JOIN jurusan j ON m.kode_jurusan = j.kode_jurusan WHERE m.nim = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jurusan j = new Jurusan();
                    j.setKode(rs.getString("kode_jurusan"));
                    j.setNama(rs.getString("nama_jurusan"));
                    Mahasiswa m = new Mahasiswa();
                    m.setNim(rs.getString("nim"));
                    m.setNama(rs.getString("nama"));
                    m.setJurusan(j);
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Mahasiswa mahasiswa) {
        if (findByNim(mahasiswa.getNim()) == null) {
            System.out.println("UPDATE FAILED: Mahasiswa dengan NIM " + mahasiswa.getNim() + " not found.");
            return false;
        }
        String sql = "UPDATE mahasiswa SET nama = ?, kode_jurusan = ? WHERE nim = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mahasiswa.getNama());
            stmt.setString(2, mahasiswa.getJurusan().getKode());
            stmt.setString(3, mahasiswa.getNim());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
