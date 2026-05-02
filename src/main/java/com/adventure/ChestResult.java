package com.adventure;

public class ChestResult {

    public enum Tier { NONE, NORMAL, SILVER, GOLDEN, PLATINUM }

    private final Tier tier;
    private final int healPotions;
    private final int damagePotions;
    private final int coins;

    private ChestResult(Tier tier, int healPotions, int damagePotions, int coins) {
        this.tier = tier;
        this.healPotions = healPotions;
        this.damagePotions = damagePotions;
        this.coins = coins;
    }

    public static ChestResult none()     { return new ChestResult(Tier.NONE,     0, 0,  0); }
    public static ChestResult normal()   { return new ChestResult(Tier.NORMAL,   1, 0,  5); }
    public static ChestResult silver()   { return new ChestResult(Tier.SILVER,   2, 1, 15); }
    public static ChestResult golden()   { return new ChestResult(Tier.GOLDEN,   2, 2, 30); }
    public static ChestResult platinum() { return new ChestResult(Tier.PLATINUM, 4, 4, 75); }

    public Tier getTier()         { return tier; }
    public int getHealPotions()   { return healPotions; }
    public int getDamagePotions() { return damagePotions; }
    public int getCoins()         { return coins; }
}
