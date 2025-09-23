public enum Enemies implements EnemyType
{
    GOBLIN(200, 100, 5.5, 5),
    TROLL(200, 100, 5.5, 5),
    SKELETON(200, 100, 5.5, 5),
    DRAGON(200, 100, 5.5, 5),
    WOLVE(200, 100, 5.5, 5),
    GHOST(200, 100, 5.5, 5),
    SPIDER(200, 100, 5.5, 5);

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;
    private final int enemyRange;

    Enemies(int enemyHp, int enemyAttack, double enemyAttackSpeed, int enemyRange) 
    {
        this.enemyHp = enemyHp;
        this.enemyAttack = enemyAttack;
        this.enemyAttackSpeed = enemyAttackSpeed;
        this.enemyRange = enemyRange;
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
    public int getEnemyRange() 
    { 
        return enemyRange; 
    }
}
