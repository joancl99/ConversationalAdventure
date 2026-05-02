import java.util.Random;

public class Events
{
    private Random rand = new Random();

    private BattleManager enemyManager;
    private Player player;
    private Chest chest;
    private Potions poti;
    private Coins coin;
    private Villager villager;

    public Events(BattleManager enemyManager, Player player, Chest chest, Potions poti, Coins coin, Villager villager)
    {
        this.enemyManager = enemyManager;
        this.player = player;
        this.chest = chest;
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public GameResult generateEvent()
    {
        int chance = rand.nextInt(100);

        if (chance < 10)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return GameResult.CONTINUE;
        }

        int event = rand.nextInt(5);

        switch (event)
        {
            case 0:
                chest.foundChest();
                return GameResult.CONTINUE;
            case 1:
                return enemyManager.enemyAppears(player, poti, coin);
            case 2:
                poti.generatePotions();
                return GameResult.CONTINUE;
            case 3:
                coin.foundCoins();
                return GameResult.CONTINUE;
            case 4:
                villager.foundVillager(player, coin, poti);
                return GameResult.CONTINUE;
            default:
                return GameResult.CONTINUE;
        }
    }
}
