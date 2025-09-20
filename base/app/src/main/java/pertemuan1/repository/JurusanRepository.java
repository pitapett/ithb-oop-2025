package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pertemuan1.database.Database;

import pertemuan1.models.Jurusan;

public class JurusanRepository {

    public boolean insert(Jurusan jurusan) {
        String sql = "INSERT INTO jurusan (kode_jurusan, nama) VALUES (?, ?)";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.getKode());
            stmt.setString(2, jurusan.getNama());


            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Jurusan> findAll() {
        List<Jurusan> list = new ArrayList<>();
        String sql = "SELECT kode_jurusan, nama FROM jurusan";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Jurusan j = new Jurusan();
                j.setKode(rs.getString("kode_jurusan"));
                j.setNama(rs.getString("nama"));
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Jurusan findByKode(String kode) {
        String sql = "SELECT kode_jurusan, nama FROM jurusan WHERE kode_jurusan = ?";
        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jurusan j = new Jurusan();
                    j.setKode(rs.getString("kode_jurusan"));
                    j.setNama(rs.getString("nama"));
                    return j;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Jurusan jurusan) {
        if (findByKode(jurusan.getKode()) == null) {
            System.out.println("UPDATE FAILED: Jurusan dengan kode " + jurusan.getKode() + " not found.");
            return false;
        }
        String sql = "UPDATE jurusan SET nama = ? WHERE kode_jurusan = ?";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.getNama());
            stmt.setString(2, jurusan.getKode());
 
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String kode) {
        String sql = "DELETE FROM jurusan WHERE kode_jurusan = ?";
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
