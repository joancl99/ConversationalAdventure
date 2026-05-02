package com.adventure.ui.console;

import com.adventure.engine.*;
import com.adventure.event.*;
import com.adventure.item.ItemType;
import com.adventure.model.*;
import com.adventure.util.FontColors;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Events {

    private final Scanner scanner;
    private final Random rand = new Random();

    private final BattleManager enemyManager;
    private final Player player;
    private final Chest chest;
    private final Potions poti;
    private final Coins coin;
    private final Villager villager;

    public Events(Scanner scanner, BattleManager enemyManager, Player player, Chest chest,
                  Potions poti, Coins coin, Villager villager) {
        this.scanner = scanner;
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public GameResult generateEvent() {
        if (rand.nextInt(100) < 10) {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        switch (rand.nextInt(5)) {
            case 0: return handleChest();
            case 1: return enemyManager.enemyAppears(player, poti, coin);
            case 2: return handlePotion();
            case 3: return handleCoins();
            case 4: return handleVillager();
            default: return GameResult.CONTINUE;
        }
    }

    private GameResult handleChest() {
        ChestResult result = chest.foundChest();

        if (result.getTier() == ChestResult.Tier.NONE) {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        switch (result.getTier()) {
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

    private GameResult handlePotion() {
        PotionEvent result = poti.generatePotions();

        switch (result.getType()) {
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

    private GameResult handleCoins() {
        CoinEvent result = coin.foundCoins();

        switch (result.getTier()) {
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

    private GameResult handleVillager() {
        ShopOffer offer = villager.generateOffer(coin.getCoins());

        if (!offer.appeared()) {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a Villager! He can sell you some items.\n");

        ShopOffer.Tier tier = offer.getTier();
        boolean shopping = true;

        while (shopping) {
            offer = villager.refreshOffer(tier);
            List<ItemType> available = offer.getAvailableItems();

            System.out.println(FontColors.YELLOW + "\nVillager's " + offer.getTierName() + " Shop:" + FontColors.WHITE);
            System.out.println(FontColors.WHITE + "\n1. " + FontColors.GREEN + "Healing Potion - " + FontColors.WHITE + offer.getPotionPrice() + " coins (+100 HP).");
            System.out.println(FontColors.WHITE + "2. " + FontColors.GREEN + "Damage Potion - "  + FontColors.WHITE + offer.getPotionPrice() + " coins (+50 Attack).");

            int option = 3;
            for (ItemType item : available) {
                System.out.println(FontColors.WHITE + option + ". " + FontColors.GREEN + item.getItemName()
                        + " - " + FontColors.WHITE + item.getItemPrice() + " coins (+"
                        + item.getItemHP() + " HP / +" + item.getItemAttack()
                        + " Attack / +" + item.getItemAttackSpeed() + " Attack Speed).");
                option++;
            }

            if (available.isEmpty())
                System.out.println(FontColors.RED + "No special items available. Only potions left.");

            System.out.println(FontColors.WHITE + "\nSelect the number of the item to buy, press 'C' to look your coins, 'P' to look your potions or enter '0' to leave:" + FontColors.RESET);
            String input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "1": {
                    PurchaseResult r = villager.buyPotion("heal", poti, coin);
                    if (r.isSuccess())
                        System.out.println(FontColors.GREEN + "\nYou bought a Healing Potion for " + FontColors.WHITE + offer.getPotionPrice() + FontColors.YELLOW + " coins.");
                    else
                        System.out.println(FontColors.RED + "\nYou don't have enough coins!");
                    break;
                }
                case "2": {
                    PurchaseResult r = villager.buyPotion("damage", poti, coin);
                    if (r.isSuccess())
                        System.out.println(FontColors.GREEN + "\nYou bought a Damage Potion for " + FontColors.WHITE + offer.getPotionPrice() + FontColors.YELLOW + " coins.");
                    else
                        System.out.println(FontColors.RED + "\nYou don't have enough coins!");
                    break;
                }
                case "0":
                    shopping = false;
                    break;
                case "C":
                    coin.showCoins();
                    break;
                case "P":
                    poti.showPotions();
                    break;
                default:
                    handleItemPurchase(input, available);
                    break;
            }
        }

        return GameResult.CONTINUE;
    }

    private void handleItemPurchase(String input, List<ItemType> available) {
        try {
            int index = Integer.parseInt(input) - 3;
            if (index >= 0 && index < available.size()) {
                ItemType item = available.get(index);
                PurchaseResult r = villager.buyItem(player, item, coin);
                if (r.isSuccess()) {
                    System.out.println(FontColors.YELLOW + "\nAmazing, you bought the " + FontColors.BOLD + FontColors.WHITE + item.getItemName() + FontColors.RESET + FontColors.YELLOW + " for " + FontColors.WHITE + item.getItemPrice() + FontColors.YELLOW + " coins.");
                    System.out.println(FontColors.GREEN + "\nYour new stats: HP: " + FontColors.WHITE + player.getHP() + FontColors.GREEN + "/" + FontColors.WHITE + player.getMaxHp() + FontColors.GREEN + ", Attack: " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + ", Attack Speed: " + FontColors.WHITE + player.getAttackSpeed());
                } else {
                    System.out.println(FontColors.RED + "\nYou don't have enough coins!");
                }
            } else {
                System.out.println(FontColors.RED + "\nInvalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println(FontColors.RED + "\nInvalid input.");
        }
    }
}
