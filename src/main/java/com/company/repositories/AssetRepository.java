package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Asset;
import com.company.repositories.interfaces.IAssetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetRepository implements IAssetRepository {
    private final IDB db;

    public AssetRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Asset a) {
        Connection dbConn = null;
        try {
            dbConn = db.getConnection();
            String q = "INSERT INTO assets(symbol, price) VALUES(?, ?)";
            PreparedStatement st = dbConn.prepareStatement(q);
            st.setString(1, a.symbol);
            st.setBigDecimal(2, a.price);
            int rows = st.executeUpdate();
            st.close();
            dbConn.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("asset create err: " + e.getMessage());
            try { if (dbConn != null) dbConn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public List<Asset> getAll() {
        List<Asset> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = db.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, symbol, price FROM assets ORDER BY id");
            while (rs.next()) {
                list.add(new Asset(rs.getInt("id"), rs.getString("symbol"), rs.getBigDecimal("price")));
            }
            rs.close(); st.close(); conn.close();
        } catch (Exception e) {
            System.out.println("asset getAll err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
        return list;
    }

    @Override
    public Asset getById(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT id, symbol, price FROM assets WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                rs.close(); st.close(); conn.close();
                return null;
            }
            Asset a = new Asset(rs.getInt("id"), rs.getString("symbol"), rs.getBigDecimal("price"));
            rs.close(); st.close(); conn.close();
            return a;
        } catch (Exception e) {
            System.out.println("asset getById err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return null;
        }
    }
}