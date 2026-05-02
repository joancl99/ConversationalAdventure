package com.adventure.engine;

import com.adventure.model.Potions;
import com.adventure.model.Coins;

public class Inventory {

    private final Potions poti;
    private final Coins coin;
    private final Villager villager;

    public Inventory(Potions poti, Coins coin, Villager villager) {
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public Potions getPotions()  { return poti; }
    public Coins getCoins()      { return coin; }
    public Villager getVillager(){ return villager; }

    // Console helper — GUI will render inventory with dedicated UI components
    public void objectsInInventory() {
        poti.showPotions();
        coin.showCoins();
        villager.showItemsBought();
    }

    public String serialize() {
        return poti.serialize() + "\n" + coin.serialize() + "\n" + villager.serializeItems();
    }

    public void deserialize(String data) {
        if (data == null) return;
        String[] lines = data.split("\n");
        if (lines.length > 0) poti.deserialize(lines[0]);
        if (lines.length > 1) coin.deserialize(lines[1]);
        if (lines.length > 2) villager.deserializeItems(lines[2]);
    }
}
