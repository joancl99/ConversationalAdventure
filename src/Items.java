enum Items implements ItemType
{
    BRUTAL_THIEF_KNIFE (0, 20, 0, "Brutal Thief's Knife"),
    DEAD_MANS_ARMOR (50, 0, 0, "Dead Man's Armor"),

    HAMMER_OF_PURIFICATION (10, 80, 0, "Hammer of Purification"),
    SORCERER_HEAD (80, 10, 0, "Sorcerer's Head"),

    STAFF_OF_THE_CURSED_ABYSS (50, 150, 2, "Staff of the Cursed Abyss"),
    NECROMANCER_SKELETON (250, 50, 2, "Necromancer's Skeleton"),
    MALEFIC_FIRE_SWORD (50, 200, 2, "Malefic Fire Sword");
    
    protected final int hp;
    protected final int attack;
    protected final double attackSpeed;
    protected final String name;

    Items (int hp, int attack, double attackSpeed, String name)
    {
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.name = name;
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
}
