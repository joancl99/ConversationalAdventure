package com.adventure.model;

import com.adventure.event.CoinEvent;
import com.adventure.util.FontColors;
import java.util.Random;

public class Coins {

    public static final int COIN        = 5;
    public static final int BRONZE_COIN = 10;
    public static final int SILVER_COIN = 25;
    public static final int GOLD_COIN   = 50;

    private int amountOfCoins;
    private final Random rand = new Random();

    public Coins(int amountOfCoins) { this.amountOfCoins = amountOfCoins; }

    public int getCoins()               { return amountOfCoins; }
    public void addCoins(int amount)    { amountOfCoins += amount; }
    public void removeCoins(int amount) { amountOfCoins -= amount; }

    public CoinEvent foundCoins() {
        if (rand.nextInt(100) < 60) return CoinEvent.none();

        int roll = rand.nextInt(100);
        CoinEvent event;
        if (roll < 40)      event = CoinEvent.coin();
        else if (roll < 70) event = CoinEvent.bronze();
        else if (roll < 95) event = CoinEvent.silver();
        else                event = CoinEvent.gold();

        addCoins(event.getAmount());
        return event;
    }

    public String serialize()          { return String.valueOf(amountOfCoins); }

    public void deserialize(String data) {
        if (data != null) amountOfCoins = Integer.parseInt(data);
    }

    // Console helper — GUI reads getters directly
    public void showCoins() {
        System.out.println(FontColors.CYAN + "\nMoney:");
        System.out.println(FontColors.PURPLE + "Coins: " + FontColors.WHITE + amountOfCoins);
    }
}
