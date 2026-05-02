package com.adventure.model;

import com.adventure.util.FontColors;

public enum Classes {
    WARRIOR(200,  75, 4.5, "Warrior"),
    MAGE   ( 75, 100, 6.5, "Mage"),
    ROGUE  ( 50, 150, 8.5, "Rogue");

    private final int hp;
    private final int attack;
    private final double attackSpeed;
    private final String name;

    Classes(int hp, int attack, double attackSpeed, String name) {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.name = name;
    }

    public int getHP()              { return hp; }
    public int getAttack()          { return attack; }
    public double getAttackSpeed()  { return attackSpeed; }
    public String getClassName()    { return name; }

    public void showBaseStats() {
        System.out.println(FontColors.CYAN + "\nBase stats of the " + name + ":");
        System.out.println(FontColors.PURPLE + "\nHP: "           + FontColors.WHITE + hp);
        System.out.println(FontColors.PURPLE + "Attack: "         + FontColors.WHITE + attack);
        System.out.println(FontColors.PURPLE + "Attack Speed: "   + FontColors.WHITE + attackSpeed);
    }
}
