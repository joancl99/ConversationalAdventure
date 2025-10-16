enum Items implements ItemType
{
    BRUTAL_THIEF_KNIFE (0, 20, 0, "Brutal Thief's Knife", 25),
    DEAD_MANS_ARMOR (50, 0, 0, "Dead Man's Armor", 25),

    HAMMER_OF_PURIFICATION (10, 80, 0, "Hammer of Purification", 50),
    SORCERER_HEAD (80, 10, 0, "Sorcerer's Head", 50),

    HELMET_OF_THE_CURSED_ABYSS (250, 50, 2, "Staff of the Cursed Abyss", 100),
    MALEFIC_FIRE_SWORD (50, 250, 2, "Malefic Fire Sword", 100);
    
    protected final int hp;
    protected final int attack;
    protected final double attackSpeed;
    protected final String name;
    protected final int price;

    Items (int hp, int attack, double attackSpeed, String name, int price)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.name = name;
        this.price = price;
    }

    public String getItemName() 
    { 
        return name; 
    }

    public int getItemHP() 
    { 
        return hp; 
    }

    public int getItemAttack() 
    { 
        return attack; 
    }

    public double getItemAttackSpeed() 
    { 
        return attackSpeed; 
    }

    public int getItemPrice()
    {
        return price;
    }
}
