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
        System.out.println(FontColors.YELLOW + FontColors.BOLD + FontColors.UNDERLINE + "\n\nWelcome to Astoria" + FontColors.RESET);

        while (true) 
        {
            System.out.println(FontColors.GREEN + "\nMain Menu:");
            System.out.println("\n1. Start Game");
            System.out.println("2. Reset Save");
            System.out.println("3. Exit");

            System.out.println();  // salto de línea extra antes del input
            System.out.println(FontColors.RESET + "Enter option: ");
            String input = scanner.nextLine();

            switch (input) 
            {
                case "1":
                    StartOrContinueGame socg = new StartOrContinueGame();
                    socg.startOrContinue();  // nombre del método corregido
                    return; // salir del menú una vez empiece el juego
                case "2":
                    Save.resetSave();
                    System.out.println("Save deleted successfully.");
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
