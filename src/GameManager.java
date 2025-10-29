import java.util.Scanner;

public class GameManager 
{
    private Scanner scanner;

    public GameManager() 
    {
        scanner = new Scanner(System.in);
    }

    public void startGame() 
    {
        System.out.println(FontColors.YELLOW + FontColors.BOLD + FontColors.UNDERLINE + "\n\nWelcome to this Conversational Adventure:" + FontColors.RESET);

        while (true) 
        {
            System.out.println(FontColors.CYAN + "\nMain Menu:");
            System.out.println(FontColors.WHITE + "\n1. " + FontColors.GREEN + "Start Game.");
            System.out.println(FontColors.WHITE + "2. " + FontColors.GREEN + "Reset Save.");
            System.out.println(FontColors.WHITE + "3. " + FontColors.GREEN + "Exit.");

            System.out.println();
            System.out.println(FontColors.RESET + "Enter option: ");
            String input = scanner.nextLine();

            switch (input) 
            {
                case "1":
                    StartOrContinueGame socg = new StartOrContinueGame();
                    socg.startOrContinue();
                    return;
                case "2":
                    Save.resetSave();
                    System.out.println("Save deleted successfully.");
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(FontColors.RED + "Invalid option. Try again.");
            }
        }
    }
}
