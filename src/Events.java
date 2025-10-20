import java.util.Random;

public class Events 
{
    private Random rand = new Random();

    private BattleManager enemyManager;
    private Classes player;
    private EnemyType enemyType;
    private Chest chest;
    private Potions poti;
    private Coins coin;
    private Villager villager;

    public Events(BattleManager enemyManager, Classes player, Chest chest, Potions poti, Coins coin, Villager villager) 
    {
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
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
            int event = rand.nextInt(5); // 0 o 1

            switch (event) 
            {
                case 0:
                    chest.foundChest();
                    break;
                case 1:
                    enemyManager.enemyAppears(player, enemyType, poti, coin);
                    break;
                case 2:
                    poti.generatePotions();
                    break;
                case 3:
                    coin.foundCoins();
                    break;
                case 4:
                    villager.foundVillager(player, coin, poti);
                    break;
            }
        }
    }
}