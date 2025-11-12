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
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.WHITE + FontColors.BOLD + "Coin" + FontColors.RESET + FontColors.GREEN + "! + " + FontColors.WHITE + COIN + FontColors.GREEN + " coins added.");
                System.out.println(FontColors.GREEN + "\nYou take the coins. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                amountOfCoins += COIN;
            } 
            else if (coinChance < 70) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a " + FontColors.BLUE + FontColors.BOLD + "Bronze Coin" + FontColors.RESET + FontColors.GREEN + "! + " + FontColors.WHITE + BRONZE_COIN + FontColors.GREEN + " coins added.");
                System.out.println(FontColors.GREEN + "\nYou take the coins. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                amountOfCoins += BRONZE_COIN;
            } 
            else if (coinChance < 95) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + " Wow! You found a " + FontColors.YELLOW + FontColors.BOLD + "Silver Coin" + FontColors.RESET + FontColors.GREEN + "! + " + FontColors.WHITE + SILVER_COIN + FontColors.GREEN + " coins added.");
                System.out.println(FontColors.GREEN + "\nYou take the coins. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                amountOfCoins += SILVER_COIN;
            } 
            else 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a " + FontColors.CYAN + FontColors.BOLD + "GOLD Coin" + FontColors.RESET + FontColors.GREEN + "! + " + FontColors.WHITE + GOLD_COIN + FontColors.GREEN + " coins added.");
                System.out.println(FontColors.GREEN + "\nYou take the coins. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                amountOfCoins += GOLD_COIN;
            }
        }
    }

    public String serialize() {
    return String.valueOf(amountOfCoins);
}

public void deserialize(String data) {
    if (data != null) {
        amountOfCoins = Integer.parseInt(data);
    }
}

    public void showCoins()
    {
        System.out.println(FontColors.CYAN + "\nMoney:");
        System.out.println(FontColors.PURPLE + "Coins: " + FontColors.WHITE + amountOfCoins);
    }
}
