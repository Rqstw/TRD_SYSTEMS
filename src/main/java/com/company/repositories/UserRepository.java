package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Role;
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
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO users(name, balance, role, banned) VALUES(?, ?, ?, ?)"
            );
            st.setString(1, u.name);
            st.setBigDecimal(2, u.balance);
            st.setString(3, u.role);
            st.setBoolean(4, u.isBanned);
            int rows = st.executeUpdate();
            st.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("user create err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public User getById(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, balance, role, banned FROM users WHERE id=?"
            );
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { rs.close(); st.close(); conn.close(); return null; }
            User u = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("balance"),
                    rs.getString("role"),
                    rs.getBoolean("banned")
            );
            rs.close(); st.close(); conn.close();
            return u;
        } catch (Exception e) {
            System.out.println("user getById err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return null;
        }
    }

    @Override
    public User getByName(String name) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, balance, role, banned FROM users WHERE name=?"
            );
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { rs.close(); st.close(); conn.close(); return null; }
            User u = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("balance"),
                    rs.getString("role"),
                    rs.getBoolean("banned")
            );
            rs.close(); st.close(); conn.close();
            return u;
        } catch (Exception e) {
            System.out.println("user getByName err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return null;
        }
    }

    @Override
    public boolean updateRole(int userId, Role newRole) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE users SET role=? WHERE id=?");
            st.setString(1, newRole.name());
            st.setInt(2, userId);
            int rows = st.executeUpdate();
            st.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("updateRole err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return false;
        }
    }

    @Override
    public boolean setBanStatus(int userId, boolean banned) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE users SET banned=? WHERE id=?");
            st.setBoolean(1, banned);
            st.setInt(2, userId);
            int rows = st.executeUpdate();
            st.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("setBanStatus err: " + e.getMessage());
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            return false;
        }
    }
}