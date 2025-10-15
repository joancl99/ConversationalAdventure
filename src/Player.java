import java.util.Random;
import java.util.Scanner;

public class Player 
{
    private Scanner scanner;

    public Player() 
    {
        scanner = new Scanner(System.in);
    }


    public void playerMovement(Classes clase, BattleManager enemyManager, Potions poti, Coins coin, Chest chest)
    {
        Random random = new Random();

        Events events = new Events(enemyManager, clase, chest);

        while (true) 
        {
            System.out.println(FontColors.BLUE + "\nWhat would you like to do?");
            System.out.println(FontColors.WHITE + "\n  'W' " + FontColors.BLUE + "- Advance.");
            System.out.println(FontColors.WHITE + "\n  'E' " + FontColors.PURPLE + "- Look your stats.");
            System.out.println(FontColors.WHITE + "\n  'R' " + FontColors.CYAN + "- Inventory.");
            System.out.println(FontColors.WHITE + "\n  'Q' " + FontColors.RED + "- Quit game.");

            System.out.println();
            System.out.println(FontColors.RESET + "Enter option: ");
            String option = scanner.nextLine().toUpperCase();

            switch (option) 
            {
                case "W":
                    System.out.println(FontColors.GREEN + "\nYou move forward.");
                
                    int randomOption = random.nextInt(4); // 0,1,2,3
                    switch (randomOption) 
                    {
                        case 0:
                            events.generateEvent(poti);
                            break;
                        case 1:
                            poti.generatePotions();
                            break;
                        case 2:
                            coin.foundCoins();
                            break;
                        case 3:
                            System.out.println("\nNothing happens. You keep advancing.\n");
                            break;
                    }
                    break;
                case "E":
                    clase.showStats();
                    break;
                case "R":
                    poti.showPotions();
                    coin.showCoins();
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
            System.out.println("Invalid class. Try again with a valid class.");
            return chooseClass();
        }
    }
}
