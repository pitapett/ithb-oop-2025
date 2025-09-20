package pertemuan1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pertemuan1.database.Database;

import pertemuan1.App.MataKuliah;
public class MatakuliahRepository {
    static final Connection conn = Database.connect();
}
