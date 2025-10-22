import java.util.Random;
import java.util.Scanner;

public class Chest 
{
    private Scanner scanner;
    private Potions poti;
    private Coins coin;

    private Random rand = new Random();

    public Chest(Potions poti, Coins coin) 
    {
        scanner = new Scanner(System.in);
        
        this.poti = poti;
        this.coin = coin;
    }

    public void foundChest()
    {
        int chanceChest = rand.nextInt(100);

        if (chanceChest < 60)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int chestChance = rand.nextInt(100);
            
            if (chestChance < 40) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a normal chest! It has 1 healing potion and 5 coins.\n");
                System.out.println(FontColors.GREEN + "You take the items of the chest. (Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot++;
                coin.amountOfCoins += 5;
            } 
            else if (chestChance < 70) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a silver chest! It has 2 healing potions, 1 damage increase potion and 15 coins.\n");
                System.out.println(FontColors.GREEN + "You take the items of the chest. (Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 2;
                poti.counterDmgPot += 1;
                coin.amountOfCoins += 15;
            } 
            else if (chestChance < 95) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a golden chest! It has 2 healing potions, 2 damage increase potions and 30 coins.\n");
                System.out.println(FontColors.GREEN + "You take the items of the chest. (Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 2;
                poti.counterDmgPot += 2;
                coin.amountOfCoins += 30;
            } 
            else 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Incredible! You found a platinum chest! It has 4 healing potions, 4 damage increase potions and 75 coins.\n");
                System.out.println(FontColors.GREEN + "You take the items of the chest. (Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 4;
                poti.counterDmgPot += 4;
                coin.amountOfCoins += 75;
            }
        }
    }
}
