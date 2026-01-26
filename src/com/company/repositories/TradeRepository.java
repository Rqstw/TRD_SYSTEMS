package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.repositories.interfaces.ITradeRepository;

import java.sql.*;

public class TradeRepository implements ITradeRepository {
    private final IDB db;

    public TradeRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean buy(int userId, int assetId, int qty) {
        if (qty <= 0) return false;

        Connection conn = null;
        try {
            conn = db.getConnection();


            PreparedStatement st1 = conn.prepareStatement("SELECT price FROM assets WHERE id=?");
            st1.setInt(1, assetId);
            ResultSet r1 = st1.executeQuery();
            if (!r1.next()) { r1.close(); st1.close(); conn.close(); return false; }
            double price = r1.getBigDecimal("price").doubleValue();
            r1.close(); st1.close();


            PreparedStatement st2 = conn.prepareStatement("SELECT balance FROM users WHERE id=?");
            st2.setInt(1, userId);
            ResultSet r2 = st2.executeQuery();
            if (!r2.next()) { r2.close(); st2.close(); conn.close(); return false; }
            double bal = r2.getBigDecimal("balance").doubleValue();
            r2.close(); st2.close();

            double cost = price * qty;
            if (bal < cost) {
                System.out.println("Not enough balance");
                conn.close();
                return false;
            }


            PreparedStatement st3 = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE id=?");
            st3.setDouble(1, cost);
            st3.setInt(2, userId);
            st3.executeUpdate();
            st3.close();


            PreparedStatement chk = conn.prepareStatement("SELECT qty FROM holdings WHERE user_id=? AND asset_id=?");
            chk.setInt(1, userId);
            chk.setInt(2, assetId);
            ResultSet cr = chk.executeQuery();
            if (cr.next()) {
                cr.close(); chk.close();
                PreparedStatement up = conn.prepareStatement(
                        "UPDATE holdings SET qty = qty + ? WHERE user_id=? AND asset_id=?");
                up.setInt(1, qty);
                up.setInt(2, userId);
                up.setInt(3, assetId);
                up.executeUpdate();
                up.close();
            } else {
                cr.close(); chk.close();
                PreparedStatement ins = conn.prepareStatement(
                        "INSERT INTO holdings(user_id, asset_id, qty) VALUES(?,?,?)");
                ins.setInt(1, userId);
                ins.setInt(2, assetId);
                ins.setInt(3, qty);
                ins.executeUpdate();
                ins.close();
            }

            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("buy err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public boolean sell(int userId, int assetId, int qty) {
        if (qty <= 0) return false;

        Connection conn = null;
        try {
            conn = db.getConnection();


            PreparedStatement st0 = conn.prepareStatement("SELECT qty FROM holdings WHERE user_id=? AND asset_id=?");
            st0.setInt(1, userId);
            st0.setInt(2, assetId);
            ResultSet r0 = st0.executeQuery();
            if (!r0.next()) { r0.close(); st0.close(); conn.close(); return false; }
            int have = r0.getInt("qty");
            r0.close(); st0.close();
            if (have < qty) { System.out.println("Not enough shares"); conn.close(); return false; }


            PreparedStatement st1 = conn.prepareStatement("SELECT price FROM assets WHERE id=?");
            st1.setInt(1, assetId);
            ResultSet r1 = st1.executeQuery();
            if (!r1.next()) { r1.close(); st1.close(); conn.close(); return false; }
            double price = r1.getBigDecimal("price").doubleValue();
            r1.close(); st1.close();

            double income = price * qty;


            PreparedStatement st2 = conn.prepareStatement(
                    "UPDATE holdings SET qty = qty - ? WHERE user_id=? AND asset_id=?");
            st2.setInt(1, qty);
            st2.setInt(2, userId);
            st2.setInt(3, assetId);
            st2.executeUpdate();
            st2.close();


            PreparedStatement del = conn.prepareStatement(
                    "DELETE FROM holdings WHERE user_id=? AND asset_id=? AND qty <= 0");
            del.setInt(1, userId);
            del.setInt(2, assetId);
            del.executeUpdate();
            del.close();


            PreparedStatement st3 = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE id=?");
            st3.setDouble(1, income);
            st3.setInt(2, userId);
            st3.executeUpdate();
            st3.close();

            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println("sell err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public String portfolio(int userId) {
        Connection conn = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = db.getConnection();


            PreparedStatement u = conn.prepareStatement("SELECT id, name, balance FROM users WHERE id=?");
            u.setInt(1, userId);
            ResultSet ur = u.executeQuery();
            if (!ur.next()) { ur.close(); u.close(); conn.close(); return "User not found"; }
            sb.append("User: ").append(ur.getString("name"))
                    .append(" balance=").append(ur.getBigDecimal("balance")).append("\n");
            ur.close(); u.close();


            String q = "SELECT a.symbol, a.price, h.qty " +
                    "FROM holdings h JOIN assets a ON a.id = h.asset_id " +
                    "WHERE h.user_id = ? ORDER BY a.symbol";
            PreparedStatement st = conn.prepareStatement(q);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            double total = 0;
            while (rs.next()) {
                String sym = rs.getString("symbol");
                double pr = rs.getBigDecimal("price").doubleValue();
                int qty = rs.getInt("qty");
                double value = pr * qty;
                total += value;
                sb.append(sym).append(" qty=").append(qty).append(" value=").append(value).append("\n");
            }
            sb.append("Portfolio value: ").append(total).append("\n");

            rs.close(); st.close(); conn.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.println("portfolio err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return "portfolio error";
        }
    }
}
