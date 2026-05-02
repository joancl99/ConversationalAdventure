package com.adventure.ui.console;

import com.adventure.engine.Chest;
import com.adventure.engine.GameLore;
import com.adventure.engine.Inventory;
import com.adventure.engine.Save;
import com.adventure.engine.Villager;
import com.adventure.event.*;
import com.adventure.model.*;
import com.adventure.util.FontColors;
import java.util.Random;
import java.util.Scanner;

public class PlayerController {

    private final Scanner scanner;

    public PlayerController(Scanner scanner) {
        this.scanner = scanner;
    }

    public GameResult playerMovement(Player player, ConsoleBattleController battleController, Potions poti, Coins coin, Chest chest, Villager villager, Inventory inventory) {
        Random random = new Random();

        Events events = new Events(scanner, battleController, player, chest, poti, coin, villager);
        GameLore lore = new GameLore();

        while (true) {
            System.out.println(FontColors.BLUE + "\nWhat would you like to do?");
            System.out.println(FontColors.WHITE + "\n  'W' -" + FontColors.BLUE + " Advance.");
            System.out.println(FontColors.WHITE + "\n  'E' -" + FontColors.PURPLE + " Player Stats.");
            System.out.println(FontColors.WHITE + "\n  'R' -" + FontColors.CYAN + " Inventory.");
            System.out.println(FontColors.WHITE + "\n  'Q' -" + FontColors.RED + " Quit game.");

            System.out.println();
            System.out.println(FontColors.RESET + "Enter option: ");
            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "W":
                    System.out.println(FontColors.GREEN + "\nYou move forward.");

                    GameResult result = random.nextInt(2) == 0
                        ? events.generateEvent()
                        : resolveLoreEvent(lore.generateLore(), player, coin, poti);

                    if (result != GameResult.CONTINUE)
                        return result;

                    break;
                case "E":
                    player.showStats();
                    break;
                case "R":
                    inventory.objectsInInventory();
                    break;
                case "Q":
                    System.out.println(FontColors.RED + "\nYou stopped your adventure.\n");
                    return GameResult.CONTINUE;
                default:
                    System.out.println(FontColors.RED + "\nInvalid input. Please enter W, E, R or Q.");
            }
        }
    }

    public Classes chooseClass() {
        System.out.println(FontColors.GREEN + "\nHi! Welcome to " + FontColors.YELLOW + "RiverLand" + FontColors.GREEN + ", the magical world where you will encounter all kinds of " + FontColors.RED + "challenges" + FontColors.GREEN + ".");
        System.out.println(FontColors.GREEN + "\nHere, you will find plenty to do: face " + FontColors.RED + "dangerous enemies" + FontColors.GREEN + ", meet " + FontColors.CYAN + "friendly people" + FontColors.GREEN + ", deal with those who are not so friendly and discover different " + FontColors.YELLOW + "secrets" + FontColors.GREEN + ".");
        System.out.println(FontColors.GREEN + "Embark on your " + FontColors.YELLOW + "adventure" + FontColors.GREEN + ", defeat your " + FontColors.RED + "foes" + FontColors.GREEN + ", and uncover all the hidden " + FontColors.CYAN + "secrets" + FontColors.GREEN + ", finding the differents" + FontColors.YELLOW + "events " + FontColors.GREEN + " of this land!");
        System.out.println(FontColors.GREEN + "\nFirst of all, you need to tell your name. What's your " + FontColors.CYAN + FontColors.BOLD + "name" + FontColors.RESET + FontColors.GREEN + "?: " + FontColors.RESET);
        String nameInput = scanner.nextLine();

        while (true) {
            System.out.println(FontColors.GREEN + "\nFantastic! Then your name is " + FontColors.CYAN + FontColors.BOLD + nameInput + FontColors.RESET + FontColors.GREEN + "? " + FontColors.WHITE + "(Enter 'Y' or 'N').");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("Y")) {
                System.out.println(FontColors.GREEN + "\nThat's a good name " + FontColors.CYAN + FontColors.BOLD + nameInput + FontColors.GREEN + ".");
                break;
            } else if (option.equalsIgnoreCase("N")) {
                System.out.println(FontColors.GREEN + "\nThen, what's your name?" + FontColors.RESET);
                nameInput = scanner.nextLine();
            } else {
                System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' or 'N'.\n");
            }
        }

        System.out.println(FontColors.RESET + FontColors.GREEN + "\nNow that I know your name, you must choose a class. " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "You need to select between the " + FontColors.YELLOW + "Warrior" + FontColors.GREEN + ", the " + FontColors.BLUE + "Mage" + FontColors.GREEN + ", or the " + FontColors.PURPLE + "Rogue " + FontColors.GREEN + "class. Each class has different stats based on " + FontColors.YELLOW + "HP" + FontColors.GREEN + ", " + FontColors.YELLOW + "Damage" + FontColors.GREEN + ", and " + FontColors.YELLOW + "Attack Speed" + FontColors.GREEN + ". " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "These three classes are very different from one another. " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "The " + FontColors.YELLOW + "Warrior " + FontColors.GREEN + "is designed for a brave hero who can take a lot of hits thanks to high " + FontColors.YELLOW + "HP " + FontColors.GREEN + "(" + FontColors.CYAN + "200" + FontColors.GREEN + "), but sacrifices some " + FontColors.YELLOW + "Damage " + FontColors.GREEN + "(" + FontColors.CYAN + "75" + FontColors.GREEN + ") and " + FontColors.YELLOW + "Attack Speed " + FontColors.GREEN + "(" + FontColors.CYAN + "4.5" + FontColors.GREEN + ") in exchange for survivability. " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "The " + FontColors.BLUE + "Mage " + FontColors.GREEN + "is a tactical and versatile class, with moderate " + FontColors.YELLOW + "HP " + FontColors.GREEN + "(" + FontColors.CYAN + "75" + FontColors.GREEN + "), solid " + FontColors.YELLOW + "Damage " + FontColors.GREEN + "(" + FontColors.CYAN + "100" + FontColors.GREEN + "), and decent " + FontColors.YELLOW + "Attack Speed " + FontColors.GREEN + "(" + FontColors.CYAN + "6.5" + FontColors.GREEN + "), allowing them to survive while dealing consistent damage. " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "The " + FontColors.PURPLE + "Rogue " + FontColors.GREEN + "is for high-risk, high-reward players. They can be killed in a single hit by most enemies due to their low " + FontColors.YELLOW + "HP " + FontColors.GREEN + "(" + FontColors.CYAN + "50" + FontColors.GREEN + "), but make up for it with very high " + FontColors.YELLOW + "Damage " + FontColors.GREEN + "(" + FontColors.CYAN + "150" + FontColors.GREEN + ") and extreme " + FontColors.YELLOW + "Attack Speed " + FontColors.GREEN + "(" + FontColors.CYAN + "8.5" + FontColors.GREEN + "), perfect for quick eliminations. " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.GREEN + "Now that you know all of this, which class do you want to choose: " + FontColors.YELLOW + "WARRIOR" + FontColors.GREEN + "," + FontColors.BLUE + " MAGE" + FontColors.GREEN + ", or " + FontColors.PURPLE + "ROGUE" + FontColors.GREEN + "? Type it: " + FontColors.RESET);

        while (true) {
            String input = scanner.nextLine().toUpperCase();
            try {
                Classes classSelected = Classes.valueOf(input);
                System.out.println(FontColors.GREEN + "\nYou have chosen: " + FontColors.YELLOW + classSelected);
                classSelected.showBaseStats();
                return classSelected;
            } catch (IllegalArgumentException e) {
                System.out.println(FontColors.RED + "Invalid class. Please enter WARRIOR, MAGE, or ROGUE.");
            }
        }
    }

    // Console adapter for LoreEvent — will be replaced by WorldController (JavaFX)
    private GameResult resolveLoreEvent(LoreEvent event, Player player, Coins coin, Potions poti) {
        if (event.getNpcName() != null)
            System.out.println(FontColors.YELLOW + "\n" + event.getNpcName() + ":" + FontColors.GREEN + " " + event.getMessage());
        else
            System.out.println(FontColors.GREEN + "\n" + event.getMessage());

        if (!event.requiresChoice()) {
            System.out.println(FontColors.WHITE + "(Press ENTER)");
            scanner.nextLine();
            return applyLoreEffect(event.getEffectOnYes(), player, coin, poti);
        }

        System.out.println(FontColors.GREEN + "Enter 'Y' or 'N':");
        while (true) {
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.println(FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                return applyLoreEffect(event.getEffectOnYes(), player, coin, poti);
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println(FontColors.GREEN + "You continue on your path." + FontColors.WHITE + " (Press ENTER)");
                scanner.nextLine();
                return applyLoreEffect(event.getEffectOnNo(), player, coin, poti);
            } else {
                System.out.println(FontColors.RED + "Invalid input. Enter 'Y' or 'N'.");
            }
        }
    }

    private GameResult applyLoreEffect(LoreEffect effect, Player player, Coins coin, Potions poti) {
        switch (effect.getType()) {
            case HEAL_FULL:
                player.restoreHp();
                return GameResult.CONTINUE;
            case COINS:
                coin.addCoins(effect.getValue());
                System.out.println(FontColors.GREEN + "+" + effect.getValue() + " coins added!");
                return GameResult.CONTINUE;
            case POTION_HEAL:
                poti.addHealPotion(1);
                System.out.println(FontColors.GREEN + "Healing potion added to inventory!");
                return GameResult.CONTINUE;
            case POTION_DMG:
                poti.addDamagePotion(1);
                System.out.println(FontColors.GREEN + "Damage potion added to inventory!");
                return GameResult.CONTINUE;
            case DAMAGE:
                player.setHP(player.getHP() - effect.getValue());
                System.out.println(FontColors.RED + "You lost " + effect.getValue() + " HP! Current HP: " + Math.max(player.getHP(), 0));
                if (player.getHP() <= 0) {
                    System.out.println(FontColors.RED + "\nYou were defeated...");
                    scanner.nextLine();
                    Save.resetSave();
                    System.out.println("\nThe game will now close.\n");
                    return GameResult.GAME_OVER;
                }
                return GameResult.CONTINUE;
            default:
                return GameResult.CONTINUE;
        }
    }
}
