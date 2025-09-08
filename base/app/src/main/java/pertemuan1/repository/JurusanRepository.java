package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pertemuan1.App.Jurusan;

public class JurusanRepository {
    private final Connection conn;

    public JurusanRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Jurusan> findAll() {
        List<Jurusan> list = new ArrayList<>();
        String sql = "SELECT kode, nama FROM jurusan";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Jurusan j = new Jurusan();
                j.kode = rs.getString("kode");
                j.nama = rs.getString("nama");
                j.listMataKuliah = new ArrayList<>(); // load later if needed
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Jurusan findByKode(String kode) {
        String sql = "SELECT kode, nama FROM jurusan WHERE kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jurusan j = new Jurusan();
                    j.kode = rs.getString("kode");
                    j.nama = rs.getString("nama");
                    j.listMataKuliah = new ArrayList<>();
                    return j;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Jurusan jurusan) {
        String sql = "INSERT INTO jurusan (kode, nama) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.kode);
            stmt.setString(2, jurusan.nama);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Jurusan jurusan) {
        String sql = "UPDATE jurusan SET nama = ? WHERE kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.nama);
            stmt.setString(2, jurusan.kode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String kode) {
        String sql = "DELETE FROM jurusan WHERE kode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
