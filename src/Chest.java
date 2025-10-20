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
            int chestChance = rand.nextInt(100);
            
            if (chestChance < 60) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a chest! It haves 1 healing potion and 5 coins.\n");
                poti.counterHealPot++;
                coin.amountOfCoins += 5;
            } 
            else if (chestChance < 85) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a silver chest! It haves 2 healing potions, 1 damage increase potion and 15 coins.\n");
                poti.counterHealPot += 2;
                poti.counterDmgPot += 1;
                coin.amountOfCoins += 15;
            } 
            else if (chestChance < 95) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a golden chest! It haves 2 healing potions, 2 damage increase potions and 30 coins.\n");
                poti.counterHealPot += 2;
                poti.counterDmgPot += 2;
                coin.amountOfCoins += 30;
            } 
            else 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Incredible! You found a platinum chest! It haves 4 healing potions, 4 damage increase potions and 75 coins.\n");
                poti.counterHealPot += 4;
                poti.counterDmgPot += 4;
                coin.amountOfCoins += 75;
            }
        }
    }
}
