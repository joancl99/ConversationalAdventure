package com.adventure;

public class LoreEffect {

    public enum Type { NONE, HEAL_FULL, COINS, POTION_HEAL, POTION_DMG, DAMAGE }

    private final Type type;
    private final int value;

    private LoreEffect(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    public static LoreEffect none()              { return new LoreEffect(Type.NONE, 0); }
    public static LoreEffect healFull()          { return new LoreEffect(Type.HEAL_FULL, 0); }
    public static LoreEffect coins(int amount)   { return new LoreEffect(Type.COINS, amount); }
    public static LoreEffect potionHeal()        { return new LoreEffect(Type.POTION_HEAL, 1); }
    public static LoreEffect potionDmg()         { return new LoreEffect(Type.POTION_DMG, 1); }
    public static LoreEffect damage(int amount)  { return new LoreEffect(Type.DAMAGE, amount); }

    public Type getType()  { return type; }
    public int getValue()  { return value; }
}
