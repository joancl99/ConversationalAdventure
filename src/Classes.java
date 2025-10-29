enum Classes
{
    WARRIOR (200, 75, 4.5, "Warrior"),
    MAGE (75, 10000, 60.5, "Mage"),
    ROGUE (50, 150, 8, "Rogue");

    private int hp;
    private int attack;
    private double attackSpeed;
    private final String name;
    private final int maxHp;

    Classes (int hp, int attack, double attackSpeed, String name)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.name = name;
        this.maxHp = hp;
    }

    public int getHP()
    {
        return hp;
    }
    
    public void setHP(int hp) 
    {
        this.hp = hp;
    }

    public int getAttack()
    {
        return attack;
    }

    public void setAttack(int attack) 
    {
        this.attack = attack;
    }

    public double getAttackSpeed()
    {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }

    public String getClassName()
    {
        return name;
    }

    public void showStats()
    {
        System.out.println(FontColors.CYAN + "\nStats of the " + name + ":");
        System.out.println(FontColors.PURPLE + "\nHP: " + FontColors.WHITE + hp);
        System.out.println(FontColors.PURPLE + "Attack: " + FontColors.WHITE + attack);
        System.out.println(FontColors.PURPLE + "Attack Speed: " + FontColors.WHITE + attackSpeed);
    }

    public void restoreHp() 
    {
        this.hp = this.maxHp;
        System.out.println(FontColors.GREEN + name + "'s HP has been fully restored to " + maxHp + "!");
    }
}