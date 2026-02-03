package com.company;

import com.company.controllers.TradeController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.AssetRepository;
import com.company.repositories.TradeRepository;
import com.company.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyApplication {
    public void start() {
        System.out.println("Application started...");

        Scanner scanner = new Scanner(System.in);
        IDB db = new PostgresDB();

        AssetRepository assetRepo = new AssetRepository(db);
        UserRepository userRepo = new UserRepository(db);
        TradeRepository tradeRepo = new TradeRepository(db);

        TradeController controller = new TradeController(assetRepo, userRepo, tradeRepo, scanner);

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("1", () -> controller.listAssets());
        actions.put("2", () -> controller.addAsset());
        actions.put("3", () -> controller.addUser());
        actions.put("4", () -> controller.buy());
        actions.put("5", () -> controller.sell());
        actions.put("6", () -> controller.portfolio());


        actions.put("7", () -> controller.banUser());
        actions.put("8", () -> controller.tradeHistory());

        while (true) {
            System.out.println("\n1) List assets");
            System.out.println("2) Add asset");
            System.out.println("3) Add user");
            System.out.println("4) Buy");
            System.out.println("5) Sell");
            System.out.println("6) Portfolio");
            System.out.println("7) Ban user");
            System.out.println("8) Trade history");
            System.out.println("0) Exit");
            System.out.print("> ");

            String c = scanner.nextLine();
            if ("0".equals(c)) break;

            Runnable action = actions.get(c);
            if (action != null) {
                action.run();
            } else {
                System.out.println("wrong menu");
            }
        }
    }
}
