enum Classes
{
    WARRIOR (200, 100, 5.5, 5),
    MAGE (200, 750, 76.5,50),
    ROGUE (50, 200, 10,20);

    private int hp;
    private int attack;
    private double attackSpeed;
    private int range;

    Classes (int hp, int attack, double attackSpeed, int range)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.range = range;
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

    public int getRange()
    {
        return range;
    }

    public void showStats()
    {
        System.out.println(FontColors.CYAN + "\nStats of the " + name() + ":");  // Imprime el nombre del enum, método name() se utiliza exclusivamente con enums, devuelve el nombre exacto de la constante del enum
        System.out.println(FontColors.PURPLE + "\nHP: " + FontColors.WHITE + hp);
        System.out.println(FontColors.PURPLE + "Attack: " + FontColors.WHITE + attack);
        System.out.println(FontColors.PURPLE + "Attack Speed: " + FontColors.WHITE + attackSpeed);
        System.out.println(FontColors.PURPLE + "Range: " + FontColors.WHITE + range);
    }
}