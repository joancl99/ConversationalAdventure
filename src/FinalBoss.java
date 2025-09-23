enum FinalBoss implements EnemyType
{
    LETHALDEMIGOD(500, 150, 8);

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;

    FinalBoss(int enemyHp, int enemyAttack, double enemyAttackSpeed) 
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