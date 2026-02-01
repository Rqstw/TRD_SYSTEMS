package com.company.security;

import com.company.models.Role;

public class AccessManager {
    public static boolean isAdmin(Role r) {
        return r == Role.ADMIN;
    }
}
