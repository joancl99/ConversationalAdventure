import java.util.Random;
import java.util.Scanner;

public class Coins 
{
    private Scanner scanner;
    protected static final int COIN = 5;
    protected static final int BRONZE_COIN = 10;
    protected static final int SILVER_COIN = 25;
    protected static final int GOLD_COIN = 50;

    protected int amountOfCoins = 0;

    private Random rand = new Random();

    public Coins(int amountOfCoins)
    {
        scanner = new Scanner(System.in);
        
        this.amountOfCoins = amountOfCoins;
    }

    public int getCoins()
    {
        return amountOfCoins;
    }

    public void removeCoins(int amount) 
    {
        amountOfCoins -= amount;
    }

    public void foundCoins()
    {
        int chanceCoin = rand.nextInt(100);

        if (chanceCoin < 60)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int coinChance = rand.nextInt(100);

            if (coinChance < 40) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a coin! +" + COIN +" coin added.\n");
                System.out.println(FontColors.GREEN + "You take the coin. (Press ENTER)");
                scanner.nextLine();
                amountOfCoins += COIN;
            } 
            else if (coinChance < 70) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a bronze coin! +" + BRONZE_COIN +" coin added.\n");
                System.out.println(FontColors.GREEN + "You take the coins. (Press ENTER)");
                scanner.nextLine();
                amountOfCoins += BRONZE_COIN;
            } 
            else if (coinChance < 95) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Wow! You found a silver coin! +" + SILVER_COIN +" coin added.\n");
                System.out.println(FontColors.GREEN + "You take the coins. (Press ENTER)");
                scanner.nextLine();
                amountOfCoins += SILVER_COIN;
            } 
            else 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a gold coin! +" + GOLD_COIN +" coin added.\n");
                System.out.println(FontColors.GREEN + "You take the coins. (Press ENTER)");
                scanner.nextLine();
                amountOfCoins += GOLD_COIN;
            }
        }
    }

    public void showCoins()
    {
        System.out.println(FontColors.CYAN + "\nMoney:");
        System.out.println(FontColors.PURPLE + "Coins: " + FontColors.WHITE + amountOfCoins);
    }
}
