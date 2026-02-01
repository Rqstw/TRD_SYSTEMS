package com.company.models;

import java.math.BigDecimal;

public class User {

    public int id;
    public String name;
    public BigDecimal balance;

    public String role;        // "admin" или "user"
    public boolean isBanned;   // для бана

    public User() {}

    public User(int id, String name, BigDecimal balance, String role, boolean isBanned) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.role = role;
        this.isBanned = isBanned;
    }

    public User(String name, BigDecimal balance, String role) {
        this.name = name;
        this.balance = balance;
        this.role = role;
        this.isBanned = false; // по умолчанию не забанен
    }

    @Override
    public String toString() {
        return id + " -- " + name +
                " :: balance=" + balance +
                " :: role=" + role +
                " :: banned=" + isBanned;
    }
}
