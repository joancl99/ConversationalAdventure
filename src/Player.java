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
        System.out.println(FontColors.GREEN + "\nHi! " + FontColors.WHITE + "Welcome to " + FontColors.YELLOW + "RiverLand" + FontColors.WHITE + ", the magical world where you will encounter all kinds of " + FontColors.RED + "challenges" + FontColors.WHITE + ".");
        System.out.println(FontColors.WHITE + "\nHere, you will find plenty to do: face " + FontColors.RED + "dangerous enemies" + FontColors.WHITE + ", meet " + FontColors.GREEN + "friendly people" + FontColors.WHITE + ", deal with those who are not so friendly and discover different " + FontColors.YELLOW + "secrets" + FontColors.WHITE + ".");
        System.out.println(FontColors.WHITE + "Embark on your " + FontColors.YELLOW + "adventure" + FontColors.WHITE + ", defeat your " + FontColors.RED + "foes" + FontColors.WHITE + ", and uncover all the hidden " + FontColors.YELLOW + "secrets" + FontColors.WHITE + " of this land!");
        System.out.println(FontColors.WHITE + "\nFirst of all, you need to tell your name. What's your " + FontColors.CYAN + FontColors.BOLD + "name" + FontColors.RESET + FontColors.WHITE + "?: " + FontColors.RESET);  
        String nameInput = scanner.nextLine();
        
        while (true)
        {
            System.out.println(FontColors.WHITE + "Fantastic! Then your name is " + FontColors.CYAN + FontColors.BOLD + nameInput + FontColors.RESET + FontColors.WHITE + "? " + FontColors.GREEN + "(Enter 'Y' or 'N').");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("Y"))
            {
                System.out.println(FontColors.WHITE + "\nThat's a good name " + FontColors.CYAN + FontColors.BOLD + nameInput + FontColors.WHITE + ".");
                break;
            }
            else if (option.equalsIgnoreCase("N"))
            {
                System.out.println(FontColors.WHITE + "\nThen, what's your name?" + FontColors.RESET);
                nameInput = scanner.nextLine();
                continue;
            }
            else
            {
                System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' or 'N'.\n");
            }
        }

        System.out.println(FontColors.RESET + FontColors.WHITE + "\nNow that I know your name, you must choose a class. " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.WHITE + "You need to select between the " + FontColors.YELLOW + "Warrior" + FontColors.WHITE + ", the " + FontColors.BLUE + "Mage" + FontColors.WHITE + ", or the " + FontColors.PURPLE + "Rogue " + FontColors.WHITE + "class. Each class has different stats based on " + FontColors.YELLOW + "HP" + FontColors.WHITE + ", " + FontColors.YELLOW + "Damage" + FontColors.WHITE + ", and " + FontColors.YELLOW + "Attack Speed" + FontColors.WHITE + ". " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.WHITE + "These three classes are very different from one another. " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.WHITE + "The " + FontColors.YELLOW + "Warrior " + FontColors.WHITE + "is designed for a brave hero who can take a lot of hits thanks to high " + FontColors.YELLOW + "HP " + FontColors.WHITE + "(" + FontColors.CYAN + "200" + FontColors.WHITE + "), but sacrifices some " + FontColors.YELLOW + "Damage " + FontColors.WHITE + "(" + FontColors.CYAN + "75" + FontColors.WHITE + ") and " + FontColors.YELLOW + "Attack Speed " + FontColors.WHITE + "(" + FontColors.CYAN + "4.5" + FontColors.WHITE + ") in exchange for survivability. " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();
        
        System.out.println(FontColors.WHITE + "The " + FontColors.BLUE + "Mage " + FontColors.WHITE + "is a tactical and versatile class, with moderate " + FontColors.YELLOW + "HP " + FontColors.WHITE + "(" + FontColors.CYAN + "75" + FontColors.WHITE + "), solid " + FontColors.YELLOW + "Damage " + FontColors.WHITE + "(" + FontColors.CYAN + "100" + FontColors.WHITE + "), and decent " + FontColors.YELLOW + "Attack Speed " + FontColors.WHITE + "(" + FontColors.CYAN + "6.5" + FontColors.WHITE + "), allowing them to survive while dealing consistent damage. " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.WHITE + "The " + FontColors.PURPLE + "Rogue " + FontColors.WHITE + "is for high-risk, high-reward players. They can be killed in a single hit by most enemies due to their low " + FontColors.YELLOW + "HP " + FontColors.WHITE + "(" + FontColors.CYAN + "50" + FontColors.WHITE + "), but make up for it with very high " + FontColors.YELLOW + "Damage " + FontColors.WHITE + "(" + FontColors.CYAN + "150" + FontColors.WHITE + ") and extreme " + FontColors.YELLOW + "Attack Speed " + FontColors.WHITE + "(" + FontColors.CYAN + "8.5" + FontColors.WHITE + "), perfect for quick eliminations. " + FontColors.GREEN + "(Press ENTER)");
        scanner.nextLine();

        System.out.println(FontColors.WHITE + "Now that you know all of this, which class do you want to choose: " + FontColors.YELLOW + "WARRIOR" + FontColors.WHITE + "," + FontColors.BLUE + " MAGE" + FontColors.WHITE + ", or " + FontColors.PURPLE + "ROGUE" + FontColors.WHITE + "? Type it: " + FontColors.WHITE);
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
