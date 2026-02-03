package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDB implements IDB {

    private static PostgresDB instance;

    private static final String URL = "jdbc:postgresql://localhost:5432/trading_db";
    private static final String USER = "postgres";
    private static final String PASS = "0000";

    public PostgresDB() { }

    public static PostgresDB getInstance() {
        if (instance == null) instance = new PostgresDB();
        return instance;
    }

    @Override
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}