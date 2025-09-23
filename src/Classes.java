enum Classes
{
    WARRIOR (250, 100, 6),
    MAGE (200, 250, 8),
    ROGUE (100, 350, 10);

    private int hp;
    private int attack;
    private double attackSpeed;

    Classes (int hp, int attack, double attackSpeed)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
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

    public double getAttackSpeed()
    {
        return attackSpeed;
    }

    public void showStats()
    {
        System.out.println(FontColors.CYAN + "\nStats of the " + name() + ":");  //Prints the name of the enum; the name() method is used exclusively with enums and returns the exact name of the enum constant.
        System.out.println(FontColors.PURPLE + "\nHP: " + FontColors.WHITE + hp);
        System.out.println(FontColors.PURPLE + "Attack: " + FontColors.WHITE + attack);
        System.out.println(FontColors.PURPLE + "Attack Speed: " + FontColors.WHITE + attackSpeed);
    }
}