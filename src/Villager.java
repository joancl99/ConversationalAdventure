import java.util.Random;
import java.util.Scanner;

public class Villager 
{
    Coins coin = new Coins(0);
    Potions poti = new Potions(0, 0);
    
    private Scanner scanner;
    private Random rand = new Random();

    public Villager()
    {
        scanner = new Scanner(System.in);
    }

    public void foundVillager(Classes player, ItemType itemFound)
    {
        int chanceFindVillager = rand.nextInt(100);

        if (chanceFindVillager < 85)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a Villager! He can sell you some items.\n");

            while (true) 
            {
                if (coin.getCoins() <= 15) 
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Shop:" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Healing Potion" + FontColors.WHITE + " - 5 coins " + FontColors.GREEN + "(+100 HP)");
                    System.out.println(FontColors.CYAN + "2. Damage Potion" + FontColors.WHITE + " - 5 coins " + FontColors.RED + "(+50 Attack)");

                    itemFound = Items.BRUTAL_THIEF_KNIFE;
                    System.out.println(FontColors.CYAN + "3. " + itemFound.getItemName() + FontColors.WHITE + " - 15 coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");

                    itemFound = Items.DEAD_MANS_ARMOR;
                    System.out.println(FontColors.CYAN + "4. " + itemFound.getItemName() + FontColors.WHITE + " - 15 coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");

                    System.out.println("Select (1), (2), (3) or (4) to buy the item that you want or (5) to leave the shop: ");
                    String input = scanner.nextLine();
                    
                    int priceOfItem = 0;

                    switch (input) 
                    {
                        case "1":
                            priceOfItem = 5;
                        case "2":
                            priceOfItem = 5;
                        case "3":
                            priceOfItem = 15;
                        case "4":
                            priceOfItem = 15;
                        case "5":
                            break;
                        default:
                            System.out.println(FontColors.RED + "\nInvalid input. Please enter (1), (2), (3), (4) or (5).");
                            continue;
                    }

                    if (coin.getCoins() < priceOfItem) 
                    {
                        System.out.println(FontColors.RED + "You don't have enough coins!");
                        continue;
                    }

                    switch (input) 
                    {
                        case "1":
                            System.out.println("Nice, you bought a Healing Potion for " + priceOfItem + " coins.");
                            poti.counterHealPot++;
                            coin.removeCoins(priceOfItem);
                            break;
                        case "2":
                            System.out.println("Nice, you bought a Damage Potion for " + priceOfItem + " coins.");
                            poti.counterDmgPot++;
                            coin.removeCoins(priceOfItem);
                            break;
                        case "3":
                            itemFound = Items.BRUTAL_THIEF_KNIFE;
                            System.out.println("Amazing, you bought the " + itemFound + " for " + priceOfItem + " coins.");
                            player.setHP(player.getHP() + itemFound.getItemHP());
                            player.setAttack(player.getAttack() + itemFound.getItemAttack());
                            System.out.println(FontColors.GREEN + "Your actual HP is " + FontColors.WHITE + player.getHP() + "and your actual damage is " + itemFound.getItemAttack() + ".");
                        case "4": 
                            itemFound = Items.DEAD_MANS_ARMOR;
                            System.out.println("Amazing, you bought the " + itemFound + " for " + priceOfItem + " coins."); 
                    }
                }
            }  
        }
    }
}