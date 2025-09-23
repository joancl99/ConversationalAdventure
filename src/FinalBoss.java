enum FinalBoss implements EnemyType
{
    MEGADIOSMORTIFERO(400, 200, 7.5, 10);

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;
    private final int enemyRange;

    FinalBoss(int enemyHp, int enemyAttack, double enemyAttackSpeed, int enemyRange) 
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