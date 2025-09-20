package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pertemuan1.database.Database;
import pertemuan1.models.Jurusan;
import pertemuan1.models.MataKuliah;

public class MatakuliahRepository {

    public boolean insert(MataKuliah matakuliah) {
        String sql = "INSERT INTO matakuliah (kode_matakuliah, nama, sks, kode_jurusan) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matakuliah.getKode());
            stmt.setString(2, matakuliah.getNama());
            stmt.setInt(3, matakuliah.getSks());
            stmt.setString(4, matakuliah.getJurusan().getKode());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MataKuliah> findAll() {
        List<MataKuliah> list = new ArrayList<>();
        String sql = "SELECT mk.kode_matakuliah, mk.nama, mk.sks, j.kode_jurusan, j.nama AS nama_jurusan FROM matakuliah mk JOIN jurusan j ON mk.kode_jurusan = j.kode_jurusan";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MataKuliah mk = new MataKuliah();
                Jurusan j = new Jurusan();
                j.setKode(rs.getString("kode_jurusan"));
                j.setNama(rs.getString("nama_jurusan"));

                mk.setKode(rs.getString("kode_matakuliah"));
                mk.setNama(rs.getString("nama"));
                mk.setSks(rs.getInt("sks"));
                mk.setJurusan(j);
                list.add(mk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public MataKuliah findByKode(String kode) {
        String sql = "SELECT mk.kode_matakuliah, mk.nama, mk.sks, j.kode_jurusan, j.nama AS nama_jurusan FROM matakuliah mk JOIN jurusan j ON mk.kode_jurusan = j.kode_jurusan WHERE mk.kode_matakuliah = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jurusan j = new Jurusan();
                    j.setKode(rs.getString("kode_jurusan"));
                    j.setNama(rs.getString("nama_jurusan"));

                    MataKuliah mk = new MataKuliah();
                    mk.setKode(rs.getString("kode_matakuliah"));
                    mk.setNama(rs.getString("nama"));
                    mk.setSks(rs.getInt("sks"));
                    mk.setJurusan(j);
                    return mk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(MataKuliah mataKuliah) {
        if (findByKode(mataKuliah.getKode()) == null) {
            System.out.println("UPDATE FAILED: Mata Kuliah dengan kode " + mataKuliah.getKode() + " not found.");
            return false;
        }
        String sql = "UPDATE matakuliah SET nama = ?, sks = ?, kode_jurusan = ? WHERE kode_matakuliah = ?";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mataKuliah.getNama());
            stmt.setInt(2, mataKuliah.getSks());
            stmt.setString(3, mataKuliah.getJurusan().getKode());
            stmt.setString(4, mataKuliah.getKode());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String kode) {
        String sql = "DELETE FROM matakuliah WHERE kode_matakuliah = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
