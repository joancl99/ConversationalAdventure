package com.adventure.ui.console;

import com.adventure.engine.Save;
import com.adventure.util.FontColors;
import java.util.Scanner;

public class GameManager {

    private final Scanner scanner;

    public GameManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startGame() {
        System.out.println(FontColors.YELLOW + FontColors.BOLD + FontColors.UNDERLINE + "\n\nWelcome to this Conversational Adventure:" + FontColors.RESET);

        while (true) {
            System.out.println(FontColors.CYAN + "\nMain Menu:");
            System.out.println(FontColors.WHITE + "\n1. " + FontColors.GREEN + "Start Game.");
            System.out.println(FontColors.WHITE + "2. " + FontColors.GREEN + "Reset Save.");
            System.out.println(FontColors.WHITE + "3. " + FontColors.GREEN + "Exit.");

            System.out.println();
            System.out.println(FontColors.RESET + "Enter option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    StartOrContinueGame socg = new StartOrContinueGame(scanner);
                    socg.startOrContinue();
                    return;
                case "2":
                    Save.resetSave();
                    System.out.println("\nSave deleted successfully.");
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println(FontColors.RED + "Invalid option. Try again.");
            }
        }
    }
}
