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
        if (list == null || list.isEmpty()) System.out.println("Empty");
        else {
            for (Asset a : list) System.out.println(a);
        }
    }

    @Override
    public void addAsset() {
        try {
            System.out.print("admin userId: ");
            int adminId = Integer.parseInt(scanner.nextLine());
            User admin = users.getById(adminId);

            if (admin == null || !"ADMIN".equalsIgnoreCase(admin.role)) {
                System.out.println("Access denied");
                return;
            }

            System.out.print("symbol: ");
            String s = scanner.nextLine();

            System.out.print("price: ");
            BigDecimal p = new BigDecimal(scanner.nextLine());

            System.out.print("categoryId (empty if none): ");
            String catStr = scanner.nextLine();
            Integer catId = null;
            if (catStr != null && !catStr.trim().isEmpty()) {
                catId = Integer.parseInt(catStr);
            }

            Asset a = new Asset(s, p);
            a.categoryId = catId;

            boolean ok = assets.create(a);
            System.out.println(ok ? "Asset added" : "Asset not added");

        } catch (Exception e) {
            System.out.println("addAsset err: " + e.getMessage());
        }
    }

    @Override
    public void addUser() {
        try {
            System.out.print("admin userId: ");
            int adminId = Integer.parseInt(scanner.nextLine());
            User admin = users.getById(adminId);

            if (admin == null || !"ADMIN".equalsIgnoreCase(admin.role)) {
                System.out.println("Access denied");
                return;
            }

            System.out.print("name: ");
            String name = scanner.nextLine();

            System.out.print("balance: ");
            BigDecimal bal = new BigDecimal(scanner.nextLine());

            User newUser = new User();
            newUser.name = name;
            newUser.balance = bal;
            newUser.role = "USER";
            newUser.isBanned = false;

            boolean ok = users.create(newUser);
            System.out.println(ok ? "User added" : "User not added");

        } catch (Exception e) {
            System.out.println("addUser err: " + e.getMessage());
        }
    }

    @Override
    public void buy() {
        System.out.println("BUY not implemented here (use repository logic)");

    }

    @Override
    public void sell() {
        System.out.println("SELL not implemented here (use repository logic)");

    }

    @Override
    public void portfolio() {
        System.out.println("PORTFOLIO not implemented here (use repository logic)");

    }


    public void banUser() {
        try {
            System.out.print("admin userId: ");
            int adminId = Integer.parseInt(scanner.nextLine());
            User admin = users.getById(adminId);

            if (admin == null || !"ADMIN".equalsIgnoreCase(admin.role)) {
                System.out.println("Access denied");
                return;
            }

            System.out.print("userId to ban: ");
            int uid = Integer.parseInt(scanner.nextLine());

            boolean ok = users.setBanStatus(uid, true);
            System.out.println(ok ? "User banned" : "Ban failed");

        } catch (Exception e) {
            System.out.println("banUser err: " + e.getMessage());
        }
    }


    public void tradeHistory() {
        try {
            System.out.print("userId: ");
            int uid = Integer.parseInt(scanner.nextLine());
            String h = trades.fullTradeHistory(uid);
            System.out.println(h);
        } catch (Exception e) {
            System.out.println("history err: " + e.getMessage());
        }
    }
}
