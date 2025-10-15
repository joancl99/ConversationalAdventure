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

    public void foundVillager()
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

                    ItemType brutalKnifeType = ItemType.BRUTAL_THIEF_KNIFE;
                    System.out.println(FontColors.CYAN + "3. " + brutalKnifeType.name + FontColors.WHITE + " - 15 coins " + FontColors.RED + "(+" + brutalKnifeType.attack + " Attack, +" + brutalKnifeType.hp + " HP)");

                    //ItemType 

                    System.out.println("Select (1), (2) or (3) to buy the item that you want: ");
                    String input = scanner.nextLine();
                    
                    int priceOfItem = 0;

                    switch (input) 
                    {
                        case "1":
                            priceOfItem = 5;
                            break;
                        case "2":
                            priceOfItem = 5;
                            break;
                        case "3":
                            priceOfItem = 15;
                            break;
                        default:
                            System.out.println(FontColors.RED + "\nInvalid input. Please enter (1), (2) or (3).");
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
                            Items boughtItem = new Items(brutalKnifeType.name, brutalKnifeType.hp, brutalKnifeType.attack, brutalKnifeType.attackSpeed);    
                    }
                }
                else if (coin.getCoins() > 15 && coin.getCoins() <= 50) 
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Shop (Advanced):" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Steel Sword" + FontColors.WHITE + " - 25 coins " + FontColors.RED + "(+30 Attack)");
                    System.out.println(FontColors.CYAN + "2. Large Healing Potion" + FontColors.WHITE + " - 20 coins " + FontColors.GREEN + "(+250 HP)");
                    System.out.println(FontColors.CYAN + "3. Magic Scroll" + FontColors.WHITE + " - 40 coins " + FontColors.BLUE + "(Unlocks Spellcasting)");
                }
                else 
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Elite Shop:" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Legendary Sword" + FontColors.WHITE + " - 75 coins " + FontColors.RED + "(+100 Attack)");
                    System.out.println(FontColors.CYAN + "2. Elixir of Immortality" + FontColors.WHITE + " - 100 coins " + FontColors.GREEN + "(+Full Heal + Buffs)");
                    System.out.println(FontColors.CYAN + "3. Dragon Armor" + FontColors.WHITE + " - 120 coins " + FontColors.BLUE + "(+200 Defense)");
                }
            }  
        }
    }
}
