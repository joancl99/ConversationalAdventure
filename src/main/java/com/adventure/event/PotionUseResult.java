package com.adventure.event;

public class PotionUseResult {

    public enum Status {
        HEALED,
        ALREADY_FULL_HP,
        NO_HEAL_POTIONS,
        BUFFED,
        NO_DAMAGE_POTIONS
    }

    private final Status status;
    private final int value;

    private PotionUseResult(Status status, int value) {
        this.status = status;
        this.value = value;
    }

    public static PotionUseResult healed(int amount)     { return new PotionUseResult(Status.HEALED, amount); }
    public static PotionUseResult alreadyFullHp()        { return new PotionUseResult(Status.ALREADY_FULL_HP, 0); }
    public static PotionUseResult noHealPotions()        { return new PotionUseResult(Status.NO_HEAL_POTIONS, 0); }
    public static PotionUseResult buffed(int amount)     { return new PotionUseResult(Status.BUFFED, amount); }
    public static PotionUseResult noDamagePotions()      { return new PotionUseResult(Status.NO_DAMAGE_POTIONS, 0); }

    public Status getStatus() { return status; }
    public int getValue()     { return value; }
}
