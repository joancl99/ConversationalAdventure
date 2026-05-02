package com.adventure.engine;

import com.adventure.enemy.*;
import com.adventure.event.EnemyEncounter;
import com.adventure.event.PotionUseResult;
import com.adventure.model.*;
import java.util.Random;

public class BattleManager {

    private final Random rand = new Random();
    private int winCounter = 0;

    public int getWinCounter()              { return winCounter; }
    public void setWinCounter(int counter)  { this.winCounter = counter; }

    public EnemyEncounter selectEnemy() {
        if (winCounter < 10) {
            Enemies[] enemies = Enemies.values();
            return new EnemyEncounter(enemies[rand.nextInt(enemies.length)], EnemyEncounter.Tier.NORMAL);
        } else if (winCounter < 20) {
            MiniBosses[] minibosses = MiniBosses.values();
            return new EnemyEncounter(minibosses[rand.nextInt(minibosses.length)], EnemyEncounter.Tier.MINI_BOSS);
        } else {
            return new EnemyEncounter(FinalBoss.LETHALDEMIGOD, EnemyEncounter.Tier.FINAL_BOSS);
        }
    }

    /** Returns true if the player moves first. For tied speeds, resolves randomly. */
    public boolean isPlayerFirst(Player player, EnemyType enemy) {
        double playerSpeed = player.getAttackSpeed();
        double enemySpeed  = enemy.getEnemyAttackSpeed();
        if (playerSpeed > enemySpeed) return true;
        if (playerSpeed < enemySpeed) return false;
        return rand.nextBoolean();
    }

    /** Resolves a tie by random coin flip (for display purposes when caller already knows it's a tie). */
    public boolean resolveTie() {
        return rand.nextBoolean();
    }

    public int playerAttacks(Player player, int currentEnemyHp) {
        return currentEnemyHp - player.getAttack();
    }

    public void enemyAttacks(Player player, EnemyType enemy) {
        player.setHP(player.getHP() - enemy.getEnemyAttack());
    }

    public PotionUseResult useHealPotion(Player player, Potions poti) {
        if (poti.getHealPotions() <= 0)      return PotionUseResult.noHealPotions();
        if (player.getHP() >= player.getMaxHp()) return PotionUseResult.alreadyFullHp();

        int before = player.getHP();
        player.setHP(Math.min(player.getHP() + Potions.HEALING_POTION, player.getMaxHp()));
        poti.useHealPotion();
        return PotionUseResult.healed(player.getHP() - before);
    }

    public PotionUseResult useDamagePotion(Player player, Potions poti) {
        if (poti.getDamagePotions() <= 0) return PotionUseResult.noDamagePotions();

        player.setAttack(player.getAttack() + Potions.DMG_POTION);
        poti.useDamagePotion();
        return PotionUseResult.buffed(Potions.DMG_POTION);
    }

    public boolean tryEscape() {
        return rand.nextInt(100) < 65;
    }

    public void onEnemyDefeated(Coins coins) {
        winCounter++;
        coins.addCoins(5);
    }
}
