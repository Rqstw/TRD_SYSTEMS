package com.company.data;

import com.company.data.interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDB implements IDB {
    // поменяй под себя
    private static final String URL = "jdbc:postgresql://localhost:5432/trading_db";
    private static final String USER = "postgres";
    private static final String PASS = "0000";

    @Override
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
