public class Items
{
    private String name;
    private int hp;
    private int attack;
    private double attackSpeed;

    public Items (String name, int hp, int attack, double attackSpeed) 
    {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.attackSpeed = attackSpeed;
    }

    public String getName() 
    { 
        return name; 
    }

    public int getHP() 
    { 
        return hp; 
    }

    public int getAttack() 
    { 
        return attack; 
    }

    public double getAttackSpeed() 
    { 
        return attackSpeed; 
    }
}