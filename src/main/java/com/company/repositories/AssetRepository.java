package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Asset;
import com.company.repositories.interfaces.IAssetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetRepository implements IAssetRepository {

    private IDB db;

    public AssetRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Asset a) {
        Connection dbConn = null;
        PreparedStatement st = null;

        try {
            dbConn = db.getConnection();


            System.out.println("Trying to add asset...");

            String q = "INSERT INTO assets(symbol, price, category_id) VALUES(?, ?, ?)";
            st = dbConn.prepareStatement(q);

            st.setString(1, a.symbol);
            st.setBigDecimal(2, a.price);


            if (a.categoryId == null) {
                st.setNull(3, Types.INTEGER);
            } else {
                st.setInt(3, a.categoryId);
            }

            int rows = st.executeUpdate();


            if (rows == 1) System.out.println("Asset inserted");
            else System.out.println("Asset maybe not inserted нормально...");

            st.close();
            dbConn.close();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("asset create err: " + e.getMessage());

            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (dbConn != null) dbConn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public List<Asset> getAll() {
        List<Asset> list = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();


            st = conn.createStatement();
            rs = st.executeQuery("SELECT id, symbol, price, category_id FROM assets ORDER BY id");

            while (rs.next()) {
                Integer catId = (Integer) rs.getObject("category_id");


                Asset a = new Asset();
                a.id = rs.getInt("id");
                a.symbol = rs.getString("symbol");
                a.price = rs.getBigDecimal("price");
                a.categoryId = catId;

                list.add(a);
            }

            rs.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("asset getAll err: " + e.getMessage());

            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return list;
    }

    @Override
    public Asset getById(int id) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();


            st = conn.prepareStatement("SELECT id, symbol, price, category_id FROM assets WHERE id=?");
            st.setInt(1, id);

            rs = st.executeQuery();

            if (!rs.next()) {

                System.out.println("asset not found");
                rs.close();
                st.close();
                conn.close();
                return null;
            }

            Integer catId = (Integer) rs.getObject("category_id");

            Asset a = new Asset();
            a.id = rs.getInt("id");
            a.symbol = rs.getString("symbol");
            a.price = rs.getBigDecimal("price");
            a.categoryId = catId;

            rs.close();
            st.close();
            conn.close();

            return a;

        } catch (Exception e) {
            System.out.println("asset getById err: " + e.getMessage());
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return null;
        }
    }


    public List<String> listAssetsWithCategoryName() {
        List<String> res = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();

            String q =
                    "SELECT a.id, a.symbol, a.price, c.name as cat " +
                            "FROM assets a LEFT JOIN categories c ON c.id = a.category_id " +
                            "ORDER BY a.id";

            st = conn.createStatement();
            rs = st.executeQuery(q);

            while (rs.next()) {

                res.add(rs.getInt("id") + " " + rs.getString("symbol") + " " +
                        rs.getBigDecimal("price") + " cat=" + rs.getString("cat"));
            }

            rs.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("asset join err: " + e.getMessage());
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return res;
    }
}
