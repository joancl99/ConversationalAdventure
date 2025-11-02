import java.util.Random;
import java.util.Scanner;

public class Player 
{
    private Scanner scanner;

    public Player() 
    {
        scanner = new Scanner(System.in);
    }


    public void playerMovement(Classes player, BattleManager enemyManager, Potions poti, Coins coin, Chest chest, Villager villager, Inventory inventory)
    {
        Random random = new Random();

        Events events = new Events(enemyManager, player, chest, poti, coin, villager);

        GameLore lore = new GameLore();

        while (true) 
        {
            System.out.println(FontColors.BLUE + "\nWhat would you like to do?");
            System.out.println(FontColors.WHITE + "\n  'W' -" + FontColors.BLUE + " Advance.");
            System.out.println(FontColors.WHITE + "\n  'E' -" + FontColors.PURPLE + " Player Stats.");
            System.out.println(FontColors.WHITE + "\n  'R' -" + FontColors.CYAN + " Inventory.");
            System.out.println(FontColors.WHITE + "\n  'Q' -" + FontColors.RED + " Quit game.");

            System.out.println();
            System.out.println(FontColors.RESET + "Enter option: ");
            String option = scanner.nextLine().toUpperCase();

            switch (option) 
            {
                case "W":
                    System.out.println(FontColors.GREEN + "\nYou move forward.");
                
                    int randomOption = random.nextInt(2); // 0,1
                    switch (randomOption) 
                    {
                        case 0:
                            events.generateEvent();
                            break;
                        case 1:
                            lore.showLore(player, coin, poti);
                            break;
                    }
                    break;
                case "E":
                    player.showStats();
                    break;
                case "R":
                    inventory.objectsInInventory();
                    break;
                case "Q":
                    System.out.println(FontColors.RED + "\nYou stopped your adventure.\n");
                    return;
                default:
                    System.out.println(FontColors.RED + "\nInvalid input. Please enter W, E, R or Q.");
            }
        }
    }

    public Classes chooseClass() 
    {
        System.out.println(FontColors.GREEN + "\nHi! Welcome to RiverLand, the magical world where you will encounter all kinds of challenges.");
        System.out.println("\nHere, you will find plenty to do: face dangerous enemies, meet friendly people, deal with those who are not so friendly and discover different secrets.");
        System.out.println("Embark on your adventure, defeat your foes, and uncover all the hidden secrets of this land!");
        System.out.println("\nFirst of all, you need to tell your name. What's your name? : " + FontColors.RESET);
        String nameInput = scanner.nextLine();
        
        while (true)
        {
            System.out.println(FontColors.GREEN + "Fantastic! Then your name is " + FontColors.WHITE + FontColors.BOLD + nameInput + FontColors.RESET + FontColors.GREEN + "? " + FontColors.WHITE + "(Enter 'Y' or 'N').");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("Y"))
            {
                System.out.println(FontColors.GREEN + "That's a good name " + FontColors.WHITE + FontColors.BOLD + nameInput + ".");
                break;
            }
            else if (option.equalsIgnoreCase("N"))
            {
                System.out.println(FontColors.GREEN + "Then, what's your name?" + FontColors.RESET);
                nameInput = scanner.nextLine();
                continue;
            }
            else
            {
                System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' or 'N'.");
            }
        }

        System.out.println(FontColors.RESET + FontColors.GREEN + "Now that I know your name, you must choose a class.");
        System.out.println(FontColors.GREEN + "You need to select between the Warrior, Mage, or Rogue class. Each class has different stats based on HP, Damage, and Attack Speed.");
        System.out.println(FontColors.GREEN + "These three classes are very different from one another.");
        System.out.println(FontColors.GREEN + "The Warrior is designed for a brave hero who can take a lot of hits thanks to high HP (200), but sacrifices some Damage (75) and Attack Speed (4.5) in exchange for survivability.");
        System.out.println(FontColors.GREEN + "The Mage is a tactical and versatile class, with moderate HP (75), solid Damage (100), and decent Attack Speed (6.5), allowing them to survive while dealing consistent damage.");
        System.out.println(FontColors.GREEN + "The Rogue is for high-risk, high-reward players. They are very fragile and can be killed in a single hit by most enemies due to their low HP (50), but make up for it with very high Damage (150) and extreme Attack Speed (8.5), perfect for quick eliminations.");


        System.out.println(FontColors.GREEN + "Now that you know all of this, which class do you want to choose: WARRIOR, MAGE, or ROGUE? Type it: ");
        String input = scanner.nextLine().toUpperCase();

        try 
        {
            Classes classSelected = Classes.valueOf(input);
            System.out.println("You have chosen: " + classSelected);
            classSelected.showStats();
            return classSelected;
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println(FontColors.RED + "Invalid class. Try again with a valid class.");
            return chooseClass();
        }
    }
}
