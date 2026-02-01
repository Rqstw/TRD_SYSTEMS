package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.repositories.AssetRepository;
import com.company.repositories.TradeRepository;
import com.company.repositories.UserRepository;

public class RepoFactory {
    private final IDB db;

    public RepoFactory(IDB db) {
        this.db = db;
    }

    public AssetRepository assets() { return new AssetRepository(db); }
    public UserRepository users() { return new UserRepository(db); }
    public TradeRepository trades() { return new TradeRepository(db); }
}