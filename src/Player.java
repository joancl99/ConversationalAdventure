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
        System.out.println("Which class do you want to choose, WARRIOR, MAGE or ROGUE? Write it: ");
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
