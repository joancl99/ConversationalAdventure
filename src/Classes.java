enum Classes
{
    WARRIOR (250, 100, 6, "Warrior"),
    MAGE (200, 150, 8, "Mage"),
    ROGUE (100, 250, 10, "Rogue");

    private int hp;
    private int attack;
    private double attackSpeed;
    private final String name;

    Classes (int hp, int attack, double attackSpeed, String name)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.name = name;
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
}