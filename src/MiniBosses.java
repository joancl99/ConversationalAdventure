enum MiniBosses implements EnemyType
{
    MegaGoblin (200, 80, 7),
    AncientDragon (220, 90, 7.5),
    SkeletonKing (200, 85, 7),
    IronColossus (250, 100, 6.5),
    AbyssalHydra (230, 95, 7.5);

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;

    MiniBosses(int enemyHp, int enemyAttack, double enemyAttackSpeed) 
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
