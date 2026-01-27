package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.*;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(User u) {
        Connection dbConn = null;
        try {
            dbConn = db.getConnection();
            String q = "INSERT INTO users(name, balance) VALUES(?, ?)";
            PreparedStatement st = dbConn.prepareStatement(q);
            st.setString(1, u.name);
            st.setBigDecimal(2, u.balance);
            int rows = st.executeUpdate();
            st.close();
            dbConn.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("user create err: " + e.getMessage());
            try { if (dbConn != null) dbConn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public User getById(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT id, name, balance FROM users WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                rs.close(); st.close(); conn.close();
                return null;
            }
            User u = new User(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("balance"));
            rs.close(); st.close(); conn.close();
            return u;
        } catch (Exception e) {
            System.out.println("user getById err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return null;
        }
    }
}