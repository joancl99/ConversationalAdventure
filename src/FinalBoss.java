enum FinalBoss implements EnemyType
{
    LETHALDEMIGOD(550, 150, 8, "Infernal Beast Demigod");

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;
    private final String name;

    FinalBoss(int enemyHp, int enemyAttack, double enemyAttackSpeed, String name) 
    {
        this.enemyHp = enemyHp;
        this.enemyAttack = enemyAttack;
        this.enemyAttackSpeed = enemyAttackSpeed;
        this.name = name;
    }

    public String getEnemyName() 
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

    public String getFinalBossName()
    {
        return name;
    }
}