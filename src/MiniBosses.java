enum MiniBosses implements EnemyType
{
    MegaGoblin (200, 80, 7, "Mega Goblin"),
    AncientDragon (220, 90, 7.5, "Ancient Dragon"),
    SkeletonKing (200, 85, 7, "Skeleton King"),
    IronColossus (250, 100, 6.5, "Iron Colossus"),
    AbyssalHydra (230, 95, 7.5, "Abyssal Hydra");

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;
    private final String name;

    MiniBosses(int enemyHp, int enemyAttack, double enemyAttackSpeed, String name) 
    {
        this.enemyHp = enemyHp;
        this.enemyAttack = enemyAttack;
        this.enemyAttackSpeed = enemyAttackSpeed;
        this.name = name;
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

    public String getEnemyName()
    {
        return name;
    }
}
