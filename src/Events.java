import java.util.Random;

public class Events 
{
    private Random rand = new Random();

    private EnemyManager enemyManager; // referencia a enemyManager
    private Classes player;
    private EnemyType enemyType;
    private Chest chest;

    public Events(EnemyManager enemyManager, Classes player, Chest chest) 
    {
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
    }

    public void generateEvent() 
    {
        int chance = rand.nextInt(100); // 0-99

        if (chance < 10) 
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        } 
        else 
        {
            int event = rand.nextInt(2); // 0 o 1

            if (enemyManager.winCounter < 5)
            {
                switch (event) 
                {
                    case 0:
                        chest.foundChest();
                        break;
                    case 1:
                        enemyManager.enemyAppears(player, enemyType);
                        break;
                }
            }
            else if (enemyManager.winCounter >= 5)
            {
                switch (event) 
                {
                    case 0:
                        chest.foundChest();
                        break;
                    case 1:
                        enemyManager.enemyAppears(player, enemyType);
                        break;
                }
            }
        }
    }
}