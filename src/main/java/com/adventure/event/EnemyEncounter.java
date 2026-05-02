package com.adventure.event;

import com.adventure.enemy.EnemyType;

public class EnemyEncounter {

    public enum Tier { NORMAL, MINI_BOSS, FINAL_BOSS }

    private final EnemyType enemy;
    private final Tier tier;

    public EnemyEncounter(EnemyType enemy, Tier tier) {
        this.enemy = enemy;
        this.tier = tier;
    }

    public EnemyType getEnemy() { return enemy; }
    public Tier getTier() { return tier; }
}
