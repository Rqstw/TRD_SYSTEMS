package com.company.repositories.interfaces;

import com.company.models.User;

public interface IUserRepository {

    boolean create(User u);
    User getById(int id);
    User getByName(String name);
    boolean updateRole(int userId, String newRole);
    boolean setBanStatus(int userId, boolean banned);
}