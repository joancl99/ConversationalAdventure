import java.util.*;

public class Villager 
{
    private Scanner scanner;
    private Random rand = new Random();
    private static Set<ItemType> itemsBuyed = new HashSet<>();

    public Villager() 
    {
        scanner = new Scanner(System.in);
    }

    public void foundVillager(Classes player, Coins coin, Potions poti) 
    {
        int chance = rand.nextInt(100);
        
        if (chance < 80) 
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
            return;
        }
        else
        {
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a Villager! He can sell you some items.\n");

            if (coin.getCoins() <= 60) 
            {
                bronzeShop(player, coin, poti);
            } 
            else if (coin.getCoins() > 60 && coin.getCoins() <= 200) 
            {
                silverShop(player, coin, poti);
            } 
            else if (coin.getCoins() > 200)
            {
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
            System.out.println(FontColors.WHITE + "\n1. " + FontColors.GREEN + "Healing Potion - " + FontColors.WHITE + poti.getPotionPrice() + " coins (+100 HP).");
            System.out.println(FontColors.WHITE + "2. " + FontColors.GREEN + "Damage Potion - " + FontColors.WHITE + poti.getPotionPrice() + " coins (+50 Attack).");

            int option = 3;
            for (ItemType item : shopItems) 
            {
                if (!itemsBuyed.contains(item)) 
                {
                    System.out.println(FontColors.WHITE + option + ". " + FontColors.GREEN + item.getItemName() + " - " + FontColors.WHITE + item.getItemPrice() + " coins (+" + item.getItemHP() + " HP / +" + item.getItemAttack() + " Attack / +" + item.getItemAttackSpeed() + " Attack Speed).");
                    itemsDisponibles.add(item);
                    option++;
                }
            }

            if (itemsDisponibles.isEmpty()) 
            {
                System.out.println(FontColors.RED + "No special items available. Only potions left.");
            }

            System.out.println(FontColors.WHITE + "\nSelect the number of the item to buy, press 'C' to look your coins, 'P' to look your potions or enter '0' to leave:" + FontColors.RESET);
            String input = scanner.nextLine().toUpperCase();

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
                case "C":
                    coin.showCoins();
                    break;
                case "P":
                    poti.showPotions();
                    break;
                default:
                    handleItemPurchase(input, itemsDisponibles, player, poti, coin);
                    break;
            }
        }
    }

    private void handleItemPurchase(String input, List<ItemType> itemsAvailable, Classes player, Potions poti, Coins coin) 
    {
        try 
        {
            int index = Integer.parseInt(input) - 3;

            if (index >= 0 && index < itemsAvailable.size()) 
            {
                buyItem(player, itemsAvailable.get(index), poti, coin);
            } 
            else 
            {
                System.out.println(FontColors.RED + "\nInvalid choice.");
            }
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(FontColors.RED + "\nInvalid input.");
        }
    }

    private void buyPotion(String potiType, Potions poti, Coins coin) 
    {
        if (coin.getCoins() < poti.getPotionPrice()) 
        {
            System.out.println(FontColors.RED + "\nYou don't have enough coins!");
            return;
        }

        if (potiType.equalsIgnoreCase("heal")) 
        {
            poti.counterHealPot++;
            System.out.println(FontColors.GREEN + "\nYou bought a Healing Potion for " + FontColors.WHITE + poti.getPotionPrice() + FontColors.YELLOW + " coins.");
        } 
        else if (potiType.equalsIgnoreCase("damage")) 
        {
            poti.counterDmgPot++;
            System.out.println(FontColors.GREEN + "\nYou bought a Damage Potion for " + FontColors.WHITE + poti.getPotionPrice() + FontColors.YELLOW + " coins.");
        }

        coin.removeCoins(poti.getPotionPrice());
    }

    private void buyItem(Classes player, ItemType item, Potions poti, Coins coin) 
    {
        if (coin.getCoins() < item.getItemPrice()) 
        {
            System.out.println(FontColors.RED + "\nYou don't have enough coins!");
            return;
        }

        System.out.println(FontColors.YELLOW + "\nAmazing, you bought the " + FontColors.BOLD + FontColors.WHITE + item.getItemName() + FontColors.RESET + FontColors.YELLOW + " for " + FontColors.WHITE + item.getItemPrice() + FontColors.YELLOW + " coins.");

        // Upgrade the base stats
        player.setAttack(player.getAttack() + item.getItemAttack());
        player.setAttackSpeed(player.getAttackSpeed() + item.getItemAttackSpeed());

        // Upgrade the maxHP
        player.setMaxHp(player.getMaxHp() + item.getItemHP());

        // Heal to maxHP
        player.setHP(Math.min(player.getHP() + item.getItemHP(), player.getMaxHp()));

        // Remove the money and register the purchase.
        coin.removeCoins(item.getItemPrice());
        itemsBuyed.add(item);

        System.out.println(FontColors.GREEN + "\nYour new stats: " + "HP: " + FontColors.WHITE + player.getHP() + FontColors.GREEN + "/" + FontColors.WHITE + player.getMaxHp() + FontColors.GREEN + ", Attack: " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + ", Attack Speed: " + FontColors.WHITE + player.getAttackSpeed());
    }

    public void showItemsBought()
    {
        if (itemsBuyed.isEmpty()) 
        {
            return;
        }

        System.out.println(FontColors.CYAN + "\nItems you've bought:");

        for (ItemType item : itemsBuyed) 
        {
            System.out.println(FontColors.PURPLE + item.getItemName()  + ":" + FontColors.WHITE + "(HP: " + item.getItemHP() + ", Attack: " + item.getItemAttack() + ", Attack Speed: " + item.getItemAttackSpeed() + ")");
        }
    }

    // Converts purchased items into a String for storage
    public String serializeItems() 
    {
        StringBuilder sb = new StringBuilder();
        for (ItemType item : itemsBuyed) 
        {
            sb.append(item.getItemName()).append(";");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // take off the last ";"
        return sb.toString();
    }

    // Reconstructs purchased items from a saved String
    public void deserializeItems(String data) 
    {
        itemsBuyed.clear();
        if (data != null && !data.isEmpty()) 
        {
            String[] parts = data.split(";");
            for (String name : parts) 
            {
                ItemType item = ItemsRegistry.getItemByName(name); // You need a ItemRegistry with all the ItemType
                if (item != null) itemsBuyed.add(item);
            }
        }
    }
}
