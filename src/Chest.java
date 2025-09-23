import java.util.Random;

public class Chest 
{
    private Potions poti;
    private Coins coin;

    private Random rand = new Random();

    public Chest(Potions poti, Coins coin) 
    {
        this.poti = poti;
        this.coin = coin;
    }

    public void foundChest()
    {
        int chanceChest = rand.nextInt(100);

        if (chanceChest < 90)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int chestAppears = rand.nextInt(5); // 0 o 4

            switch (chestAppears) 
            {
                case 0:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a chest! It haves 1 healing potion and 5 coins.\n");
                    poti.counterHealPot++;
                    coin.amountOfCoins += 5;
                    break;
                case 1:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a bronze chest! It haves 2 healing potions, 1 damage increase potion and 10 coins.\n");
                    poti.counterHealPot += 2;
                    poti.counterDmgPot += 1;
                    coin.amountOfCoins += 10;
                    break;
                case 2:
                    System.out.println(FontColors.GREEN + "\nEvent: Amazing! You found a silver chest! It haves 2 healing potions, 1 damage increase potion and 25 coins.\n");
                    poti.counterHealPot += 2;
                    poti.counterDmgPot += 1;
                    coin.amountOfCoins += 25;
                    break;
                case 3:
                    System.out.println(FontColors.GREEN + "\nEvent: Amazing! You found a golden chest! It haves 3 healing potions, 3 damage increase potions and 50 coins.\n");
                    poti.counterHealPot += 3;
                    poti.counterDmgPot += 3;
                    coin.amountOfCoins += 50;
                    break;
                case 4:
                    System.out.println(FontColors.GREEN + "\nEvent: Incredible! You found a platinum chest! It haves 4 healing potions, 4 damage increase potions and 100 coins.\n");
                    poti.counterHealPot += 4;
                    poti.counterDmgPot += 4;
                    coin.amountOfCoins += 100;
                    break;
            }
        }
    }
}
