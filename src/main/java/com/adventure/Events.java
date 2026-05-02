package com.adventure;

import java.util.Random;

public class Events
{
    private Random rand = new Random();

    private BattleManager enemyManager;
    private Player player;
    private Chest chest;
    private Potions poti;
    private Coins coin;
    private Villager villager;

    public Events(BattleManager enemyManager, Player player, Chest chest, Potions poti, Coins coin, Villager villager)
    {
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public GameResult generateEvent()
    {
        if (rand.nextInt(100) < 10)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        switch (rand.nextInt(5))
        {
            case 0: return handleChest();
            case 1: return enemyManager.enemyAppears(player, poti, coin);
            case 2: return handlePotion();
            case 3: return handleCoins();
            case 4:
                villager.foundVillager(player, coin, poti);
                return GameResult.CONTINUE;
            default:
                return GameResult.CONTINUE;
        }
    }

    private GameResult handleChest()
    {
        ChestResult result = chest.foundChest();

        if (result.getTier() == ChestResult.Tier.NONE)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        switch (result.getTier())
        {
            case NORMAL:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a" + FontColors.WHITE + FontColors.BOLD + " Normal Chest" + FontColors.RESET + FontColors.GREEN + "!");
                break;
            case SILVER:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a" + FontColors.BLUE + FontColors.BOLD + " Silver Chest" + FontColors.RESET + FontColors.GREEN + "!");
                break;
            case GOLDEN:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a" + FontColors.YELLOW + FontColors.BOLD + " Golden Chest" + FontColors.RESET + FontColors.GREEN + "!");
                break;
            case PLATINUM:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Incredible! You found a" + FontColors.CYAN + FontColors.BOLD + " Platinum Chest" + FontColors.RESET + FontColors.GREEN + "!");
                break;
            default:
                break;
        }

        System.out.println(FontColors.GREEN + "It contains " + FontColors.WHITE + result.getHealPotions() + FontColors.GREEN
                + " healing potion(s), " + FontColors.WHITE + result.getDamagePotions()
                + FontColors.GREEN + " damage potion(s) and " + FontColors.WHITE + result.getCoins() + FontColors.GREEN + " coins.");

        poti.addHealPotion(result.getHealPotions());
        poti.addDamagePotion(result.getDamagePotions());
        coin.addCoins(result.getCoins());

        return GameResult.CONTINUE;
    }

    private GameResult handlePotion()
    {
        PotionEvent result = poti.generatePotions();

        switch (result.getType())
        {
            case NONE:
                System.out.println("\nNothing happens. You keep advancing.\n");
                break;
            case HEAL:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.WHITE + FontColors.BOLD + "healing potion" + FontColors.RESET + FontColors.GREEN + ", it heals " + FontColors.WHITE + Potions.HEALING_POTION + " HP.");
                break;
            case DAMAGE:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.WHITE + FontColors.BOLD + "damage potion" + FontColors.RESET + FontColors.GREEN + ", it gives +" + FontColors.WHITE + Potions.DMG_POTION + FontColors.GREEN + " attack.");
                break;
        }

        return GameResult.CONTINUE;
    }

    private GameResult handleCoins()
    {
        CoinEvent result = coin.foundCoins();

        switch (result.getTier())
        {
            case NONE:
                System.out.println("\nNothing happens. You keep advancing.\n");
                break;
            case COIN:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.WHITE + FontColors.BOLD + "Coin" + FontColors.RESET + FontColors.GREEN + "! +" + result.getAmount() + " coins.");
                break;
            case BRONZE:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.BLUE + FontColors.BOLD + "Bronze Coin" + FontColors.RESET + FontColors.GREEN + "! +" + result.getAmount() + " coins.");
                break;
            case SILVER:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Wow! You found a " + FontColors.YELLOW + FontColors.BOLD + "Silver Coin" + FontColors.RESET + FontColors.GREEN + "! +" + result.getAmount() + " coins.");
                break;
            case GOLD:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a " + FontColors.CYAN + FontColors.BOLD + "Gold Coin" + FontColors.RESET + FontColors.GREEN + "! +" + result.getAmount() + " coins.");
                break;
        }

        return GameResult.CONTINUE;
    }
}
