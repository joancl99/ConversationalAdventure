public enum Enemies implements EnemyType
{
    GOBLIN(100, 50, 5.5, "Goblin"),
    TROLL(120,60, 5, "Troll"),
    SKELETON(110, 55, 5.5, "Skeleton"),
    DRAGON(150, 70, 6, "Dragon"),
    WOLVE(100, 50, 6, "Wolve"),
    GHOST(90, 50, 6.5, "Ghost"),
    SPIDER(80, 40, 6, "Spider");

    private final int enemyHp;
    private final int enemyAttack;
    private final double enemyAttackSpeed;
    private final String enemyName;

    Enemies(int enemyHp, int enemyAttack, double enemyAttackSpeed, String enemyName) 
    {
        this.enemyHp = enemyHp;
        this.enemyAttack = enemyAttack;
        this.enemyAttackSpeed = enemyAttackSpeed;
        this.enemyName = enemyName;
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
        return enemyName;
    }
}
