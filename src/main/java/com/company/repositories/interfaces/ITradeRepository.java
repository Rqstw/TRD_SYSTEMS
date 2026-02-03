package com.company.repositories.interfaces;

public interface ITradeRepository {
    boolean buy(int userId, int assetId, int qty);
    boolean sell(int userId, int assetId, int qty);
    String portfolio(int userId);

    String fullTradeHistory(int userId);
}