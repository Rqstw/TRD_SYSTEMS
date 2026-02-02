package com.company.models;
public enum Role {
    USER, ADMIN;
    public static Role from(String s) {
        try { return Role.valueOf(s.toUpperCase()); }
        catch (Exception e) { return USER; }
    }
}
