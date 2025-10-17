import java.util.*;

public class Villager 
{
    private Scanner scanner;
    private Random rand = new Random();
    private static Set<ItemType> itemsComprados = new HashSet<>();

    public Villager() {
        scanner = new Scanner(System.in);
    }

    public void foundVillager(Classes player, Coins coin, Potions poti) {
        int chance = rand.nextInt(100);
        
        if (chance < 80) 
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return;
        }
        else
        {
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a Villager! He can sell you some items.\n");

            if (coin.getCoins() <= 60) {
            bronzeShop(player, coin, poti);
            } else if (coin.getCoins() <= 200) {
                silverShop(player, coin, poti);
            } else {
                goldenShop(player, coin, poti);
            }
        }
    }

    private void bronzeShop(Classes player, Coins coin, Potions poti) 
    {
        shopLoop(player, coin, poti, "Bronze", Arrays.asList(Items.BRUTAL_THIEF_KNIFE, Items.DEAD_MANS_ARMOR));
    }

    private void silverShop(Classes player, Coins coin, Potions poti) 
    {
        shopLoop(player, coin, poti, "Silver", Arrays.asList(Items.HAMMER_OF_PURIFICATION, Items.SORCERER_HEAD));
    }

    private void goldenShop(Classes player, Coins coin, Potions poti) 
    {
        shopLoop(player, coin, poti, "Golden", Arrays.asList(Items.HELMET_OF_THE_CURSED_ABYSS, Items.MALEFIC_FIRE_SWORD));
    }

    private void shopLoop(Classes player, Coins coin, Potions poti, String shopName, List<ItemType> shopItems) 
    {
        boolean shopping = true;

        while (shopping) 
        {
            List<ItemType> itemsDisponibles = new ArrayList<>();
            System.out.println(FontColors.YELLOW + "\nVillager's " + shopName + " Shop:" + FontColors.WHITE);
            System.out.println("1. Healing Potion - " + poti.getPotionPrice() + " coins (+100 HP)");
            System.out.println("2. Damage Potion - " + poti.getPotionPrice() + " coins (+50 Attack)");

            int option = 3;
            for (ItemType item : shopItems) 
            {
                if (!itemsComprados.contains(item)) 
                {
                    System.out.println(option + ". " + item.getItemName() + " - " + item.getItemPrice() + " coins");
                    itemsDisponibles.add(item);
                    option++;
                }
            }

            if (itemsDisponibles.isEmpty()) 
            {
                System.out.println("No special items available. Only potions left.");
            }

            System.out.println("Select item to buy or 0 to leave:");
            String input = scanner.nextLine();

            switch (input) 
            {
                case "1":
                    buyPotion("heal", poti, coin);
                    break;
                case "2":
                    buyPotion("damage", poti, coin);
                    break;
                case "0":
                    shopping = false;
                    break;
                default:
                    handleItemPurchase(input, itemsDisponibles, player, poti, coin);
                    break;
            }
        }
    }

    private void handleItemPurchase(String input, List<ItemType> itemsDisponibles, Classes player, Potions poti, Coins coin) 
    {
        try 
        {
            int index = Integer.parseInt(input) - 3;
            if (index >= 0 && index < itemsDisponibles.size()) 
            {
                buyItem(player, itemsDisponibles.get(index), poti, coin);
            } 
            else 
            {
                System.out.println("Invalid choice.");
            }
        } 
        catch (NumberFormatException e) 
        {
            System.out.println("Invalid input.");
        }
    }

    private void buyPotion(String potiType, Potions poti, Coins coin) 
    {
        if (coin.getCoins() < poti.getPotionPrice()) 
        {
            System.out.println(FontColors.RED + "You don't have enough coins!");
            return;
        }

        if (potiType.equalsIgnoreCase("heal")) 
        {
            poti.counterHealPot++;
            System.out.println("You bought a Healing Potion for " + poti.getPotionPrice() + " coins.");
        } 
        else if (potiType.equalsIgnoreCase("damage")) 
        {
            poti.counterDmgPot++;
            System.out.println("You bought a Damage Potion for " + poti.getPotionPrice() + " coins.");
        }

        coin.removeCoins(poti.getPotionPrice());
    }

    private void buyItem(Classes player, ItemType item, Potions poti, Coins coin) 
    {
        if (coin.getCoins() < item.getItemPrice()) 
        {
            System.out.println(FontColors.RED + "You don't have enough coins!");
            return;
        }

        System.out.println("Amazing, you bought the " + item.getItemName() + " for " + item.getItemPrice() + " coins.");
        player.setHP(player.getHP() + item.getItemHP());
        player.setAttack(player.getAttack() + item.getItemAttack());
        coin.removeCoins(item.getItemPrice());
        itemsComprados.add(item);
        System.out.println(FontColors.GREEN + "Your new stats: HP " + player.getHP() + ", Damage " + player.getAttack());
    }
}
