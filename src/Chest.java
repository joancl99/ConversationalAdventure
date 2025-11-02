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
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a" + FontColors.WHITE + FontColors.BOLD + " Normal Chest" + FontColors.RESET + FontColors.GREEN + "! It has" + FontColors.WHITE + " 1" + FontColors.GREEN + " healing potion and " + FontColors.WHITE + "5"  + FontColors.GREEN + " coins.");
                System.out.println(FontColors.GREEN + "\nYou take the items of the chest. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot++;
                coin.amountOfCoins += 5;
            } 
            else if (chestChance < 70) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a" + FontColors.BLUE + FontColors.BOLD + " Silver Chest" + FontColors.RESET + FontColors.GREEN + "! It has" + FontColors.WHITE + " 2"  + FontColors.GREEN + " healing potions, " + FontColors.WHITE + "1 "  + FontColors.GREEN + "damage increase potion and " + FontColors.WHITE + "15" + FontColors.GREEN + " coins.");
                System.out.println(FontColors.GREEN + "\nYou take the items of the chest. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 2;
                poti.counterDmgPot += 1;
                coin.amountOfCoins += 15;
            } 
            else if (chestChance < 95) 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Amazing! You found a" + FontColors.YELLOW + FontColors.BOLD + " Golden Chest" + FontColors.RESET + FontColors.GREEN + "! It has" + FontColors.WHITE + " 2"  + FontColors.GREEN + " healing potions, " + FontColors.WHITE + "2 "  + FontColors.GREEN + "damage increase potion and " + FontColors.WHITE + "30" + FontColors.GREEN + " coins.");
                System.out.println(FontColors.GREEN + "\nYou take the items of the chest. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 2;
                poti.counterDmgPot += 2;
                coin.amountOfCoins += 30;
            } 
            else 
            {
                System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "Incredible! You found a" + FontColors.CYAN + FontColors.BOLD + " Platinum Chest" + FontColors.RESET + FontColors.GREEN + "! It has" + FontColors.WHITE + " 4"  + FontColors.GREEN + " healing potions, " + FontColors.WHITE + "4 "  + FontColors.GREEN + "damage increase potion and " + FontColors.WHITE + "75" + FontColors.GREEN + " coins.");
                System.out.println(FontColors.GREEN + "\nYou take the items of the chest. " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot += 4;
                poti.counterDmgPot += 4;
                coin.amountOfCoins += 75;
            }
        }
    }
}
