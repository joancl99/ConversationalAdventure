package com.adventure;

import java.util.Random;

public class Potions {

    public static final int HEALING_POTION = 100;
    public static final int DMG_POTION     = 50;
    public static final int PRICE          = 15;

    private int counterHealPot;
    private int counterDmgPot;
    private final Random rand = new Random();

    public Potions(int counterHealPot, int counterDmgPot) {
        this.counterHealPot = counterHealPot;
        this.counterDmgPot  = counterDmgPot;
    }

    public int getHealPotions()            { return counterHealPot; }
    public int getDamagePotions()          { return counterDmgPot; }
    public void addHealPotion(int amount)  { counterHealPot += amount; }
    public void addDamagePotion(int amount){ counterDmgPot  += amount; }
    public void useHealPotion()            { counterHealPot--; }
    public void useDamagePotion()          { counterDmgPot--; }
    public int getPotionPrice()            { return PRICE; }

    public PotionEvent generatePotions() {
        if (rand.nextInt(100) < 60) return PotionEvent.none();

        if (rand.nextInt(2) == 0) {
            counterHealPot++;
            return PotionEvent.heal();
        } else {
            counterDmgPot++;
            return PotionEvent.damage();
        }
    }

    public String serialize() {
        return counterHealPot + "," + counterDmgPot;
    }

    public void deserialize(String data) {
        if (data != null && data.contains(",")) {
            String[] parts = data.split(",");
            counterHealPot = Integer.parseInt(parts[0]);
            counterDmgPot  = Integer.parseInt(parts[1]);
        }
    }

    // Console helper — GUI reads getters directly
    public void showPotions() {
        System.out.println(FontColors.CYAN + "\nPotions:");
        System.out.println(FontColors.PURPLE + "Healing Potion: " + FontColors.WHITE + counterHealPot);
        System.out.println(FontColors.PURPLE + "Damage Potion: "  + FontColors.WHITE + counterDmgPot);
    }
}
