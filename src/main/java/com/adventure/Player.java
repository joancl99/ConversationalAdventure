package com.adventure;

public class Player
{
    private final Classes playerClass;
    private int hp;
    private int maxHp;
    private int attack;
    private double attackSpeed;

    public Player(Classes playerClass)
    {
        this.playerClass = playerClass;
        this.hp = playerClass.getHP();
        this.maxHp = playerClass.getHP();
        this.attack = playerClass.getAttack();
        this.attackSpeed = playerClass.getAttackSpeed();
    }

    public Classes getPlayerClass() { return playerClass; }

    public int getHP() { return hp; }
    public void setHP(int hp) { this.hp = hp; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public double getAttackSpeed() { return attackSpeed; }
    public void setAttackSpeed(double attackSpeed) { this.attackSpeed = attackSpeed; }

    public void restoreHp()
    {
        this.hp = this.maxHp;
        System.out.println(FontColors.GREEN + playerClass.getClassName() + "'s HP has been fully restored to " + maxHp + "!");
    }

    public void showStats()
    {
        System.out.println(FontColors.CYAN + "\nStats of the " + playerClass.getClassName() + ":");
        System.out.println(FontColors.PURPLE + "\nHP: " + FontColors.WHITE + hp + FontColors.PURPLE + " / " + FontColors.WHITE + maxHp);
        System.out.println(FontColors.PURPLE + "Attack: " + FontColors.WHITE + attack);
        System.out.println(FontColors.PURPLE + "Attack Speed: " + FontColors.WHITE + attackSpeed);
    }
}
