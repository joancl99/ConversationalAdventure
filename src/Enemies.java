public enum Enemies implements EnemyType
{
    GOBLIN(100, 50, 5.5),
    TROLL(120,60, 5),
    SKELETON(110, 55, 5.5),
    DRAGON(150, 70, 6),
    WOLVE(100, 50, 6),
    GHOST(90, 50, 6.5),
    SPIDER(80, 40, 6);

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;

    Enemies(int enemyHp, int enemyAttack, double enemyAttackSpeed) 
    {
        this.enemyHp = enemyHp;
        this.enemyAttack = enemyAttack;
        this.enemyAttackSpeed = enemyAttackSpeed;
    }

    public String getName() 
    { 
        return this.name(); 
    }

    public int getEnemyHP() 
    { 
        return enemyHp; 
    
    }
    public int getEnemyAttack() 
    { 
        return enemyAttack; 
    }
    public double getEnemyAttackSpeed() 
    { 
        return enemyAttackSpeed; 
    }
}
