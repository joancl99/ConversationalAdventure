package com.adventure.ui.console;

import com.adventure.engine.*;
import com.adventure.model.*;
import com.adventure.util.FontColors;
import java.util.Scanner;

public class StartOrContinueGame {

    private final Scanner scanner;

    public StartOrContinueGame(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startOrContinue() {
        PlayerController playerController = new PlayerController(scanner);
        BattleManager enemyManager = new BattleManager(scanner);
        Potions poti = new Potions(0, 0);
        Coins coin = new Coins(0);
        Chest chest = new Chest();
        Villager villager = new Villager();
        Inventory inventory = new Inventory(poti, coin, villager);

        Player player = Save.loadSavedPlayer(enemyManager, inventory);

        if (player == null) {
            Classes chosenClass = playerController.chooseClass();
            player = new Player(chosenClass);
            Save.save(player, enemyManager.getWinCounter(), inventory);
        } else {
            System.out.println(FontColors.PURPLE + "\nYou are continuing as: " + FontColors.CYAN + FontColors.BOLD + player.getPlayerClass().getClassName() + FontColors.RESET);
            System.out.println(FontColors.PURPLE + "Current HP: " + FontColors.WHITE + player.getHP() + FontColors.PURPLE + "/" + FontColors.WHITE + player.getMaxHp());
            player.showStats();
            inventory.objectsInInventory();
        }

        GameResult result = playerController.playerMovement(player, enemyManager, poti, coin, chest, villager, inventory);
        if (result != GameResult.GAME_OVER)
            Save.save(player, enemyManager.getWinCounter(), inventory);
    }
}
