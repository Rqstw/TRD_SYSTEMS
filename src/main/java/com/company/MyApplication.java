package com.company;

import com.company.controllers.TradeController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.AssetRepository;
import com.company.repositories.TradeRepository;
import com.company.repositories.UserRepository;

import java.util.Scanner;

public class MyApplication {
    public void start() {
        Scanner scanner = new Scanner(System.in);

        IDB db = new PostgresDB();
        AssetRepository assetRepo = new AssetRepository(db);
        UserRepository userRepo = new UserRepository(db);
        TradeRepository tradeRepo = new TradeRepository(db);

        TradeController controller = new TradeController(assetRepo, userRepo, tradeRepo, scanner);

        while (true) {
            System.out.println("\n1) List assets");
            System.out.println("2) Add asset");
            System.out.println("3) Add user");
            System.out.println("4) Buy");
            System.out.println("5) Sell");
            System.out.println("6) Portfolio");
            System.out.println("0) Exit");
            System.out.print("> ");

            String c = scanner.nextLine();

            if (c.equals("1")) controller.listAssets();
            else if (c.equals("2")) controller.addAsset();
            else if (c.equals("3")) controller.addUser();
            else if (c.equals("4")) controller.buy();
            else if (c.equals("5")) controller.sell();
            else if (c.equals("6")) controller.portfolio();
            else if (c.equals("0")) break;
            else System.out.println("wrong menu");
        }
    }
}