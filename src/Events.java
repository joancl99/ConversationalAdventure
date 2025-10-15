import java.util.Random;

public class Events 
{
    private Random rand = new Random();

    private BattleManager enemyManager;
    private Classes player;
    private EnemyType enemyType;
    private Chest chest;

    public Events(BattleManager enemyManager, Classes player, Chest chest) 
    {
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
    }

    public void generateEvent(Potions poti) 
    {
        int chance = rand.nextInt(100); // 0-99

        if (chance < 10) 
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        } 
        else 
        {
            int event = rand.nextInt(2); // 0 o 1

            switch (event) 
            {
                case 0:
                    chest.foundChest();
                    break;
                case 1:
                    enemyManager.enemyAppears(player, enemyType, poti);
                    break;
            }
        }
    }
}