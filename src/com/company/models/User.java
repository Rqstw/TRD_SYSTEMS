package com.company.models;

import java.math.BigDecimal;

public class User {
    public int id;
    public String name;
    public BigDecimal balance;

    public User() {}

    public User(int id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public User(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return id + " :: " + name + " :: balance=" + balance;
    }
}
