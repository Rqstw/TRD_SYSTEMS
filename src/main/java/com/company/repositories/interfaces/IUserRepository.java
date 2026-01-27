package com.company.repositories.interfaces;

import com.company.models.User;

public interface IUserRepository {
    boolean create(User u);
    User getById(int id);
}