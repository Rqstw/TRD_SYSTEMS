package com.company.controllers;

import com.company.controllers.interfaces.ITradeController;
import com.company.models.Asset;
import com.company.models.User;
import com.company.repositories.interfaces.IAssetRepository;
import com.company.repositories.interfaces.ITradeRepository;
import com.company.repositories.interfaces.IUserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class TradeController implements ITradeController {

    private final IAssetRepository assets;
    private final IUserRepository users;
    private final ITradeRepository trades;
    private final Scanner scanner;

    public TradeController(IAssetRepository assets, IUserRepository users, ITradeRepository trades, Scanner scanner) {
        this.assets = assets;
        this.users = users;
        this.trades = trades;
        this.scanner = scanner;
    }

    @Override
    public void listAssets() {
        List<Asset> list = assets.getAll();
        System.out.println("ASSETS:");
        for (Asset a : list) System.out.println(a);
        if (list.size() == 0) System.out.println("Empty");
    }

    @Override
    public void addAsset() {
        try {
            System.out.print("symbol: ");
            String s = scanner.nextLine();
            System.out.print("price: ");
            BigDecimal p = new BigDecimal(scanner.nextLine());
            boolean ok = assets.create(new Asset(s, p));
            System.out.println(ok ? "added" : "not added");
        } catch (Exception e) {
            System.out.println("addAsset err: " + e.getMessage());
        }
    }

    @Override
    public void addUser() {
        try {
            System.out.print("name: ");
            String name = scanner.nextLine();
            System.out.print("balance: ");
            BigDecimal bal = new BigDecimal(scanner.nextLine());
            boolean ok = users.create(new User(name, bal));
            System.out.println(ok ? "user added" : "user not added");
        } catch (Exception e) {
            System.out.println("addUser err: " + e.getMessage());
        }
    }

    @Override
    public void buy() {
        try {
            System.out.print("userId: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.print("assetId: ");
            int assetId = Integer.parseInt(scanner.nextLine());
            System.out.print("qty: ");
            int qty = Integer.parseInt(scanner.nextLine());

            boolean ok = trades.buy(userId, assetId, qty);
            System.out.println(ok ? "BUY OK" : "BUY FAIL");
        } catch (Exception e) {
            System.out.println("buy err: " + e.getMessage());
        }
    }

    @Override
    public void sell() {
        try {
            System.out.print("userId: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.print("assetId: ");
            int assetId = Integer.parseInt(scanner.nextLine());
            System.out.print("qty: ");
            int qty = Integer.parseInt(scanner.nextLine());

            boolean ok = trades.sell(userId, assetId, qty);
            System.out.println(ok ? "SELL OK" : "SELL FAIL");
        } catch (Exception e) {
            System.out.println("sell err: " + e.getMessage());
        }
    }

    @Override
    public void portfolio() {
        try {
            System.out.print("userId: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.println(trades.portfolio(userId));
        } catch (Exception e) {
            System.out.println("portfolio err: " + e.getMessage());
        }
    }
}