package com.adventure.engine;

import com.adventure.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Save {

    private static final String SAVE_FILE = "./saveFile/save.json";
    private static final Gson   GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save(Player player, int winCounter, Inventory inventory) {
        SaveData data = new SaveData();
        data.playerClass   = player.getPlayerClass().name();
        data.hp            = player.getHP();
        data.maxHp         = player.getMaxHp();
        data.attack        = player.getAttack();
        data.attackSpeed   = player.getAttackSpeed();
        data.winCounter    = winCounter;
        data.healPotions   = inventory.getPotions().getHealPotions();
        data.damagePotions = inventory.getPotions().getDamagePotions();
        data.coins         = inventory.getCoins().getCoins();

        String serializedItems = inventory.getVillager().serializeItems();
        data.itemsBought = serializedItems.isEmpty()
                ? new ArrayList<>()
                : Arrays.asList(serializedItems.split(";"));

        try {
            File file = new File(SAVE_FILE);
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static Player loadSavedPlayer(BattleManager battleManager, Inventory inventory) {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return null;

        try (Reader reader = new FileReader(file)) {
            SaveData data = GSON.fromJson(reader, SaveData.class);

            Player player = new Player(Classes.valueOf(data.playerClass));
            player.setHP(data.hp);
            player.setMaxHp(data.maxHp);
            player.setAttack(data.attack);
            player.setAttackSpeed(data.attackSpeed);

            if (battleManager != null)
                battleManager.setWinCounter(data.winCounter);

            if (inventory != null) {
                inventory.getPotions().deserialize(data.healPotions + "," + data.damagePotions);
                inventory.getCoins().deserialize(String.valueOf(data.coins));
                String items = (data.itemsBought == null || data.itemsBought.isEmpty())
                        ? ""
                        : String.join(";", data.itemsBought);
                inventory.getVillager().deserializeItems(items);
            }

            return player;
        } catch (Exception e) {
            System.err.println("Failed to load save: " + e.getMessage());
            return null;
        }
    }

    public static boolean saveExists() {
        return new File(SAVE_FILE).exists();
    }

    public static void resetSave() {
        new File(SAVE_FILE).delete();
    }

    // ── Save data structure ────────────────────────────────────────────────────

    private static class SaveData {
        String       playerClass;
        int          hp;
        int          maxHp;
        int          attack;
        double       attackSpeed;
        int          winCounter;
        int          healPotions;
        int          damagePotions;
        int          coins;
        List<String> itemsBought;
    }
}
