import java.util.Random;

public class Coins 
{
    protected static final int COIN = 1;
    protected static final int BRONZE_COIN = 10;
    protected static final int SILVER_COIN = 25;
    protected static final int GOLD_COIN = 50;

    protected int amountOfCoins = 0;

    private Random rand = new Random();

    public Coins(int amountOfCoins)
    {
        this.amountOfCoins = amountOfCoins;
    }

    public void getCoins()
    {

    }

    public void foundCoins()
    {
        int chanceCoin = rand.nextInt(100);

        if (chanceCoin < 90)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int coinAppears = rand.nextInt(4); // 0 o 3

            switch (coinAppears) 
            {
                case 0:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a coin! +" + COIN +" coin added.\n");
                    amountOfCoins += COIN;
                    break;
                case 1:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a bronze coin! +" + BRONZE_COIN +" coin added.\n");
                    amountOfCoins += BRONZE_COIN;
                    break;
                case 2:
                    System.out.println(FontColors.GREEN + "\nEvent: Wow! You found a silver coin! +" + SILVER_COIN +" coin added.\n");
                    amountOfCoins += SILVER_COIN;
                    break;
                case 3:
                    System.out.println(FontColors.GREEN + "\nEvent: Amazing! You found a gold coin! +" + GOLD_COIN +" coin added.\n");
                    amountOfCoins += GOLD_COIN;
                    break;
            }
        }
    }

    public void showCoins()
    {
        System.out.println(FontColors.CYAN + "\nMoney:");
        System.out.println(FontColors.PURPLE + "\nCoins: " + FontColors.WHITE + amountOfCoins);
    }
}
