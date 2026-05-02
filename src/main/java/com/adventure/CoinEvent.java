package com.adventure;

public class CoinEvent {

    public enum Tier { NONE, COIN, BRONZE, SILVER, GOLD }

    private final Tier tier;
    private final int amount;

    private CoinEvent(Tier tier, int amount) {
        this.tier = tier;
        this.amount = amount;
    }

    public static CoinEvent none()   { return new CoinEvent(Tier.NONE,   0); }
    public static CoinEvent coin()   { return new CoinEvent(Tier.COIN,   Coins.COIN); }
    public static CoinEvent bronze() { return new CoinEvent(Tier.BRONZE, Coins.BRONZE_COIN); }
    public static CoinEvent silver() { return new CoinEvent(Tier.SILVER, Coins.SILVER_COIN); }
    public static CoinEvent gold()   { return new CoinEvent(Tier.GOLD,   Coins.GOLD_COIN); }

    public Tier getTier()  { return tier; }
    public int getAmount() { return amount; }
}
