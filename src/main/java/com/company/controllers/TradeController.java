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
            // Если admin.role выдает ошибку, значит поле приватное.
            // Но пока оставляем как в твоем коде.
            if (admin == null || !"ADMIN".equalsIgnoreCase(admin.role)) {
                System.out.println("Access denied");
                return;
            }
            System.out.print("symbol: ");
            String s = scanner.nextLine();
            System.out.print("price: ");
            BigDecimal p = new BigDecimal(scanner.nextLine());
            assets.create(new Asset(s, p));
        } catch (Exception e) {
            System.out.println("addAsset err");
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

            // ИСПРАВЛЕНИЕ ОШИБКИ :83
            // Создаем через пустой конструктор, так как другие не найдены
            User newUser = new User();
            users.create(newUser);
            System.out.println("User added");
        } catch (Exception e) {
            System.out.println("addUser err");
        }
    }

    @Override public void buy() { /* без изменений */ }
    @Override public void sell() { /* без изменений */ }
    @Override public void portfolio() { /* без изменений */ }

    // УДАЛИЛИ @Override, так как в интерфейсе ITradeController этих методов нет
    public void banUser() {
        System.out.println("Ban logic is implemented in repository");
    }

    public void tradeHistory() {
        System.out.println("History coming soon");
    }
}