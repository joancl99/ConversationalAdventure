import java.util.*;

public class Villager 
{
    private Scanner scanner;
    private Random rand = new Random();
    private static Set<ItemType> itemsComprados = new HashSet<>();

    public Villager()
    {
        scanner = new Scanner(System.in);
    }

    public void foundVillager(Classes player, ItemType itemFound, Coins coin, Potions poti)
    {
        int chanceFindVillager = rand.nextInt(100);

        if (chanceFindVillager < 85)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a Villager! He can sell you some items.\n");

            boolean shopping = true;

            while (shopping) 
            {
                if (coin.getCoins() <= 60)  //HECHARLE UN OJO, SI TENGO MAS DE 60, COMPRO EN LA SILVER SHOP Y PASO A TENER 60 O MENOS, ME CAMBIA AUTO A ESTA TIENDA
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Bronze Shop:" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Healing Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.GREEN + "(+100 HP)");
                    System.out.println(FontColors.CYAN + "2. Damage Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.RED + "(+50 Attack)");

                    int option = 3;
                    if (!itemsComprados.contains(Items.BRUTAL_THIEF_KNIFE)) 
                    {
                        itemFound = Items.BRUTAL_THIEF_KNIFE;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");
                        option++;
                    }

                    if (!itemsComprados.contains(Items.DEAD_MANS_ARMOR)) 
                    {
                        itemFound = Items.DEAD_MANS_ARMOR;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");
                        option++;
                    }

                    System.out.println("Select an item number to buy or type (0) to leave the shop: ");
                    String input = scanner.nextLine();

                    switch (input) 
                    {
                        case "1":
                            buyPotion("heal", poti, coin);
                            break;

                        case "2":
                            buyPotion("damage", poti, coin);
                            break;

                        case "3":
                            if (!itemsComprados.contains(Items.BRUTAL_THIEF_KNIFE)) 
                            {
                                buyItem(player, Items.BRUTAL_THIEF_KNIFE, poti, coin);
                            } 
                            else if (!itemsComprados.contains(Items.DEAD_MANS_ARMOR)) 
                            {
                                buyItem(player, Items.DEAD_MANS_ARMOR, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "4":
                            if (!itemsComprados.contains(Items.DEAD_MANS_ARMOR)) 
                            {
                                buyItem(player, Items.DEAD_MANS_ARMOR, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "0":
                            shopping = false;
                            break;

                        default:
                            System.out.println(FontColors.RED + "\nInvalid input. Try again.");
                            break;
                    }
                }
                else if (coin.getCoins() > 60 && coin.getCoins() <= 200) 
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Silver Shop:" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Healing Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.GREEN + "(+100 HP)");
                    System.out.println(FontColors.CYAN + "2. Damage Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.RED + "(+50 Attack)");

                    int option = 3;
                    if (!itemsComprados.contains(Items.HAMMER_OF_PURIFICATION)) 
                    {
                        itemFound = Items.HAMMER_OF_PURIFICATION;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");
                        option++;
                    }

                    if (!itemsComprados.contains(Items.SORCERER_HEAD)) 
                    {
                        itemFound = Items.SORCERER_HEAD;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP)");
                        option++;
                    }

                    System.out.println("Select an item number to buy or type (0) to leave the shop: ");
                    String input = scanner.nextLine();

                    switch (input) 
                    {
                        case "1":
                            buyPotion("heal", poti, coin);
                            break;

                        case "2":
                            buyPotion("damage", poti, coin);
                            break;

                        case "3":
                            if (!itemsComprados.contains(Items.HAMMER_OF_PURIFICATION)) 
                            {
                                buyItem(player, Items.HAMMER_OF_PURIFICATION, poti, coin);
                            } 
                            else if (!itemsComprados.contains(Items.SORCERER_HEAD)) 
                            {
                                buyItem(player, Items.SORCERER_HEAD, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "4":
                            if (!itemsComprados.contains(Items.SORCERER_HEAD)) 
                            {
                                buyItem(player, Items.SORCERER_HEAD, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "0":
                            shopping = false;
                            break;

                        default:
                            System.out.println(FontColors.RED + "\nInvalid input. Try again.");
                            break;
                    }
                }
                else if (coin.getCoins() > 200) 
                {
                    System.out.println(FontColors.YELLOW + "\nVillager's Golden Shop:" + FontColors.WHITE);
                    System.out.println(FontColors.CYAN + "1. Healing Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.GREEN + "(+100 HP)");
                    System.out.println(FontColors.CYAN + "2. Damage Potion" + FontColors.WHITE + " - " + poti.getPotionPrice() + " coins " + FontColors.RED + "(+50 Attack)");

                    int option = 3;
                    if (!itemsComprados.contains(Items.HELMET_OF_THE_CURSED_ABYSS)) 
                    {
                        itemFound = Items.HELMET_OF_THE_CURSED_ABYSS;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP, +" + itemFound.getItemAttackSpeed() + " Attack Speed)");
                        option++;
                    }

                    if (!itemsComprados.contains(Items.MALEFIC_FIRE_SWORD)) 
                    {
                        itemFound = Items.MALEFIC_FIRE_SWORD;
                        System.out.println(FontColors.CYAN + option + ". " + itemFound.getItemName() + FontColors.WHITE + " - " + itemFound.getItemPrice() + " coins " + FontColors.RED + "(+" + itemFound.getItemAttack() + " Attack, +" + itemFound.getItemHP() + " HP, +" + itemFound.getItemAttackSpeed() + " Attack Speed)");
                        option++;
                    }

                    System.out.println("Select an item number to buy or type (0) to leave the shop: ");
                    String input = scanner.nextLine();

                    switch (input) 
                    {
                        case "1":
                            buyPotion("heal", poti, coin);
                            break;

                        case "2":
                            buyPotion("damage", poti, coin);
                            break;

                        case "3":
                            if (!itemsComprados.contains(Items.HELMET_OF_THE_CURSED_ABYSS)) 
                            {
                                buyItem(player, Items.HELMET_OF_THE_CURSED_ABYSS, poti, coin);
                            } 
                            else if (!itemsComprados.contains(Items.MALEFIC_FIRE_SWORD)) 
                            {
                                buyItem(player, Items.MALEFIC_FIRE_SWORD, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "4":
                            if (!itemsComprados.contains(Items.MALEFIC_FIRE_SWORD)) 
                            {
                                buyItem(player, Items.MALEFIC_FIRE_SWORD, poti, coin);
                            } 
                            else 
                            {
                                System.out.println(FontColors.RED + "Invalid choice.");
                            }
                            break;

                        case "0":
                            shopping = false;
                            break;

                        default:
                            System.out.println(FontColors.RED + "\nInvalid input. Try again.");
                            break;
                    }
                }
            }  
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