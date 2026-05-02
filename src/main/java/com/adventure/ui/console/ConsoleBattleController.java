package com.adventure.ui.console;

import com.adventure.engine.BattleManager;
import com.adventure.engine.Save;
import com.adventure.enemy.EnemyType;
import com.adventure.enemy.FinalBoss;
import com.adventure.event.EnemyEncounter;
import com.adventure.event.PotionUseResult;
import com.adventure.model.*;
import com.adventure.util.FontColors;
import java.util.Scanner;

public class ConsoleBattleController {

    private final Scanner scanner;
    private final BattleManager battleManager;

    public ConsoleBattleController(Scanner scanner, BattleManager battleManager) {
        this.scanner = scanner;
        this.battleManager = battleManager;
    }

    public int getWinCounter() { return battleManager.getWinCounter(); }

    public GameResult handleEncounter(Player player, Potions poti, Coins coin) {
        EnemyEncounter encounter = battleManager.selectEnemy();
        EnemyType enemy = encounter.getEnemy();

        switch (encounter.getTier()) {
            case NORMAL:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "An Enemy appears! It's a mysterious creature!");
                System.out.println(FontColors.RED + "\nIt's the " + FontColors.BOLD + FontColors.WHITE + enemy.getEnemyName() + FontColors.RESET + FontColors.RED + ":");
                break;
            case MINI_BOSS:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "A Big Enemy appears! It's a mysterious creature!");
                System.out.println(FontColors.RED + "\nIt's the " + FontColors.BOLD + FontColors.WHITE + enemy.getEnemyName() + FontColors.RESET + FontColors.RED + ":");
                break;
            case FINAL_BOSS:
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "The Final Boss appears!: The "
                        + FontColors.BOLD + FontColors.WHITE + enemy.getEnemyName() + FontColors.RESET + FontColors.RED + " is here!: ");
                break;
        }

        System.out.println(FontColors.RED + "\nHP: "            + FontColors.WHITE + enemy.getEnemyHP());
        System.out.println(FontColors.RED + "Attack: "          + FontColors.WHITE + enemy.getEnemyAttack());
        System.out.println(FontColors.RED + "Attack Speed: "    + FontColors.WHITE + enemy.getEnemyAttackSpeed());
        System.out.println(FontColors.WHITE + "\nYou gonna fight him? Enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) {
                System.out.println(FontColors.GREEN + "\nYou're going to fight.");
                return runBattle(player, enemy, poti, coin);
            } else if (input.equalsIgnoreCase("N")) {
                System.out.println(FontColors.YELLOW + "\nYou have run away.");
                return GameResult.CONTINUE;
            } else if (input.equalsIgnoreCase("S")) {
                player.showStats();
                System.out.println("\nNow you want to fight or run away? Enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
            } else {
                System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
            }
        }
    }

    private GameResult runBattle(Player player, EnemyType enemy, Potions poti, Coins coin) {
        int enemyHp = enemy.getEnemyHP();

        System.out.println(FontColors.YELLOW + "\nThe battle begins! Prepare yourself... " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        while (player.getHP() > 0 && enemyHp > 0) {
            boolean isTie = player.getAttackSpeed() == enemy.getEnemyAttackSpeed();
            boolean playerFirst = isTie ? battleManager.resolveTie()
                                        : battleManager.isPlayerFirst(player, enemy);

            if (isTie) {
                System.out.println(FontColors.YELLOW + "\nBoth have the same attack speed! Who goes first will be decided randomly... " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
            }

            TurnOutcome outcome = doPlayerTurn(player, enemyHp, poti, enemy, playerFirst);
            enemyHp = outcome.enemyHp;

            if (outcome.battleEnded) break;

            if (playerFirst && enemyHp > 0 && player.getHP() > 0 && !outcome.enemyAlreadyAttacked) {
                doEnemyTurn(player, enemy);
            }

            System.out.println(FontColors.GREEN + "\nPlayer HP: " + FontColors.WHITE + Math.max(player.getHP(), 0));
            System.out.println(FontColors.RED   + "Enemy HP: "   + FontColors.WHITE + Math.max(enemyHp, 0));
        }

        if (player.getHP() <= 0) {
            System.out.println(FontColors.RED + "\nYou were defeated... (Press ENTER)");
            scanner.nextLine();
            Save.resetSave();
            System.out.println("\nThe game will now close.\n");
            return GameResult.GAME_OVER;
        } else if (enemy == FinalBoss.LETHALDEMIGOD && enemyHp <= 0) {
            System.out.println(FontColors.GREEN + "\nVictory! You have defeated The " + FontColors.BOLD + FontColors.WHITE
                    + enemy.getEnemyName() + FontColors.RESET + FontColors.GREEN + ".");
            System.out.println(FontColors.YELLOW + "\nNow the darkness has fallen. Your name will be sung in every corner of this world.");
            System.out.println(FontColors.YELLOW + FontColors.BOLD + "\n\nTHE END\n\n" + FontColors.RESET);
            return GameResult.VICTORY;
        } else {
            System.out.println(FontColors.GREEN + "\nVictory! You have defeated the " + FontColors.BOLD + FontColors.WHITE
                    + enemy.getEnemyName() + FontColors.RESET + FontColors.GREEN + ".");
            System.out.println(FontColors.GREEN + "You received " + FontColors.WHITE + "5" + FontColors.GREEN + " coins.");
            battleManager.onEnemyDefeated(coin);
            System.out.println(FontColors.YELLOW + "\nCurrent wins: " + FontColors.WHITE + battleManager.getWinCounter() + FontColors.WHITE + " (Press ENTER).");
            scanner.nextLine();
            return GameResult.CONTINUE;
        }
    }

    private TurnOutcome doPlayerTurn(Player player, int enemyHp, Potions poti, EnemyType enemy, boolean playerFirst) {
        boolean actionTaken = false;
        boolean battleEnded = false;
        boolean enemyAlreadyAttacked = false;

        while (!actionTaken && !battleEnded) {
            System.out.println(FontColors.WHITE + "\nIt's your turn!");
            System.out.println("Press 'Z' to attack, 'R' to check your potions, 'X' to use one, 'E' to check your stats, or 'C' for try to escape:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Z")) {
                if (player.getAttackSpeed() < enemy.getEnemyAttackSpeed()) {
                    System.out.println(FontColors.RED + "\nThe enemy attacks first due to higher speed!");
                    doEnemyTurn(player, enemy);
                    enemyAlreadyAttacked = true;
                    if (player.getHP() <= 0) break;
                } else if (player.getAttackSpeed() == enemy.getEnemyAttackSpeed()) {
                    if (!playerFirst) {
                        System.out.println(FontColors.YELLOW + "\nThe enemy moves first this time! It was a random tie!");
                        doEnemyTurn(player, enemy);
                        enemyAlreadyAttacked = true;
                        if (player.getHP() <= 0) break;
                    } else {
                        System.out.println(FontColors.YELLOW + "\nYou move first this time! It was a random tie!");
                    }
                }

                System.out.println(FontColors.WHITE + "\nPlayer's turn:");
                System.out.println(FontColors.GREEN + "You strike the enemy!");
                enemyHp = battleManager.playerAttacks(player, enemyHp);
                System.out.println("You dealt " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + " HP damage.");
                actionTaken = true;

            } else if (input.equalsIgnoreCase("X")) {
                boolean attacked = handlePotionChoice(player, poti, enemy);
                return new TurnOutcome(enemyHp, false, attacked);

            } else if (input.equalsIgnoreCase("C")) {
                if (battleManager.tryEscape()) {
                    System.out.println(FontColors.YELLOW + "\nYou have run away.");
                    scanner.nextLine();
                    battleEnded = true;
                } else {
                    System.out.println(FontColors.YELLOW + "\nYou couldn't escape!");
                    actionTaken = true;
                }

            } else if (input.equalsIgnoreCase("E")) {
                player.showStats();
            } else if (input.equalsIgnoreCase("R")) {
                poti.showPotions();
            } else {
                System.out.println(FontColors.RED + "\nInvalid input. Use 'Z', 'X', 'E', 'R' or 'C'.");
            }
        }

        return new TurnOutcome(enemyHp, battleEnded, enemyAlreadyAttacked);
    }

    private boolean handlePotionChoice(Player player, Potions poti, EnemyType enemy) {
        if (poti.getHealPotions() <= 0 && poti.getDamagePotions() <= 0) {
            System.out.println(FontColors.RED + "You don't have any potion!");
            System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
            doEnemyTurn(player, enemy);
            return true;
        }

        while (true) {
            System.out.println("Which potion do you want to use? Healing (1) / Damage (2): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                PotionUseResult result = battleManager.useHealPotion(player, poti);
                if (result.getStatus() == PotionUseResult.Status.ALREADY_FULL_HP) {
                    System.out.println(FontColors.RED + "You cannot use a healing potion, you are already full HP!\n" + FontColors.RESET);
                    continue;
                }
                if (result.getStatus() == PotionUseResult.Status.NO_HEAL_POTIONS) {
                    System.out.println(FontColors.RED + "\nYou don't have healing potions!");
                    System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
                    doEnemyTurn(player, enemy);
                    return true;
                }
                System.out.println(FontColors.GREEN + "\nYou gonna use a healing potion!");
                System.out.println(FontColors.GREEN + "You restored " + FontColors.WHITE + result.getValue() + FontColors.GREEN + " HP.");
                System.out.println(FontColors.GREEN + "Current HP: " + FontColors.WHITE + player.getHP());
                doEnemyTurn(player, enemy);
                return true;

            } else if (choice.equals("2")) {
                PotionUseResult result = battleManager.useDamagePotion(player, poti);
                if (result.getStatus() == PotionUseResult.Status.NO_DAMAGE_POTIONS) {
                    System.out.println(FontColors.RED + "\nYou don't have damage potions!");
                    System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
                    doEnemyTurn(player, enemy);
                    return true;
                }
                System.out.println(FontColors.GREEN + "\nYou drink a damage potion and feel stronger!");
                System.out.println(FontColors.GREEN + "Current Attack: " + FontColors.WHITE + player.getAttack());
                doEnemyTurn(player, enemy);
                return true;

            } else {
                System.out.println(FontColors.RED + "Invalid input, please enter '1' or '2'.");
            }
        }
    }

    private void doEnemyTurn(Player player, EnemyType enemy) {
        System.out.println(FontColors.WHITE + "\nEnemy's turn:");
        System.out.println(FontColors.RED + "The enemy attacks you!");
        battleManager.enemyAttacks(player, enemy);
        System.out.println("It deals " + FontColors.WHITE + enemy.getEnemyAttack() + FontColors.RED + " HP damage.");
    }

    private static class TurnOutcome {
        final int enemyHp;
        final boolean battleEnded;
        final boolean enemyAlreadyAttacked;

        TurnOutcome(int enemyHp, boolean battleEnded, boolean enemyAlreadyAttacked) {
            this.enemyHp = enemyHp;
            this.battleEnded = battleEnded;
            this.enemyAlreadyAttacked = enemyAlreadyAttacked;
        }
    }
}
