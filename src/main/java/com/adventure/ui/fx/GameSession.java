package com.adventure.ui.fx;

import com.adventure.engine.*;
import com.adventure.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSession {

    private Player player;
    private final BattleManager battleManager = new BattleManager();
    private final Potions potions   = new Potions(0, 0);
    private final Coins coins       = new Coins(0);
    private final Chest chest       = new Chest();
    private final Villager villager = new Villager();
    private final Inventory inventory;
    private final List<LogEntry> sessionLog = new ArrayList<>();

    public GameSession() {
        this.inventory = new Inventory(potions, coins, villager);
    }

    public boolean hasSavedGame() {
        return Save.saveExists();
    }

    public boolean loadGame() {
        player = Save.loadSavedPlayer(battleManager, inventory);
        return player != null;
    }

    public void newGame(Classes chosenClass) {
        player = new Player(chosenClass);
        battleManager.setWinCounter(0);
        potions.deserialize("0,0");
        coins.deserialize("0");
        villager.deserializeItems("");
        sessionLog.clear();
    }

    public void save() {
        if (player != null)
            Save.save(player, battleManager.getWinCounter(), inventory);
    }

    public void addToLog(String text, String color) {
        sessionLog.add(new LogEntry(text, color));
    }

    public void addSeparatorToLog() {
        sessionLog.add(new LogEntry("", "#c6d0a4", true));
    }

    public List<LogEntry> getSessionLog() {
        return Collections.unmodifiableList(sessionLog);
    }

    public Player getPlayer()                { return player; }
    public BattleManager getBattleManager()  { return battleManager; }
    public Potions getPotions()              { return potions; }
    public Coins getCoins()                  { return coins; }
    public Chest getChest()                  { return chest; }
    public Villager getVillager()            { return villager; }
    public Inventory getInventory()          { return inventory; }
}
