import java.util.Random;
import java.util.Scanner;

public class GameLore 
{
    private Random random = new Random();
    private Scanner scanner;

    public GameLore() 
    {
        scanner = new Scanner(System.in);
    }

    public void showLore(Classes player, Coins coin, Potions poti) 
    {
        int randomOption = random.nextInt(28);

        switch (randomOption) 
        {
            case 0:
                System.out.println(FontColors.YELLOW + "\nMagician Frog:" + FontColors.GREEN + " You look like you've had a rough journey. You want to take a break, traveler?");
                System.out.println(FontColors.GREEN + "Enter 'Y' to take a rest or 'N' to stay on your path.");
                String option = scanner.nextLine().trim();

                while (true)
                {   
                    if (option.equalsIgnoreCase("Y"))
                    {
                        System.out.println(FontColors.GREEN + "\nYou take a rest at the Froggie's house.");
                        System.out.println(FontColors.YELLOW + "\nYou feel completely refreshed! All your HP has been restored. (Press ENTER)");
                        scanner.nextLine();
                        player.restoreHp();
                        break;
                    }
                    else if (option.equalsIgnoreCase("N"))
                    {
                        System.out.println(FontColors.GREEN + "\nYou stay on your path. (Press ENTER)");
                        scanner.nextLine();
                        break;
                    }
                    else
                    {
                        System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' or 'N'.");
                        option = scanner.nextLine();
                    }
                }
                break;
            case 1:
                System.out.println(FontColors.YELLOW + "\nFarmer:" + FontColors.GREEN + " It looks like there are so many monsters around here. Be careful on your travels!\n");
                break;
            case 2:
                System.out.println(FontColors.YELLOW + "\nMerchant:" + FontColors.GREEN + " Greetings, traveler! Are you looking for potions or weapons today? you do'n found the " + FontColors.WHITE + FontColors.BOLD + "Villager's Shop " + FontColors.RESET + FontColors.GREEN + "yet?\n");
                break;
            case 3:
                System.out.println(FontColors.YELLOW + "\nTraveler:" + FontColors.GREEN + " Gold can't save you from what lurks in the dark, but maybe can help you.");
                System.out.println("The Traveler gives you 20 coins! (Press ENTER)");
                scanner.nextLine();
                coin.amountOfCoins += 20;
                break;
            case 4:
                System.out.println(FontColors.YELLOW + "\nCity Guard:" + FontColors.GREEN + " You look so weak! You maybe need an upgrade.");
                System.out.println("The City Guard gives you a damage increase potion! (Press ENTER)");
                scanner.nextLine();
                poti.counterDmgPot++;
                break;
            case 5:
                System.out.println(FontColors.YELLOW + "\nMysterious Sage:" + FontColors.GREEN + " I prepared a magic drink that may be useful for you...");
                System.out.println(FontColors.GREEN + "Enter 'Y' to drink or 'N' to go away.");
                String drinkOption = scanner.nextLine().trim();

                while (true)
                {   
                    if (drinkOption.equalsIgnoreCase("Y"))
                    {
                        System.out.println("\nYou drinked the mysterious drink.");
                        System.out.println(FontColors.RED + "\nIt was a trap! The Mysterious Sage was a terrible witch!");
                        System.out.println(FontColors.RED + "\nYou lost 20 HP! (Press ENTER)");
                        scanner.nextLine();
                        player.setHP(player.getHP() - 20);
                        break;
                    }
                    else if (drinkOption.equalsIgnoreCase("N"))
                    {
                        System.out.println(FontColors.GREEN + "You run away from this crazy Sage. (Press ENTER)");
                        scanner.nextLine();
                        break;
                    }
                    else
                    {
                        System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' or 'N'.");
                        drinkOption = scanner.nextLine();
                    }
                }
                break;
            case 6:
                System.out.println(FontColors.GREEN + "\nYou found a " + FontColors.WHITE + FontColors.BOLD + 
                           "Note:" + FontColors.RESET + FontColors.GREEN + " In this magic world, people whisper about a diabolic beast capable of destroying anyone who faces it...");
                System.out.println("(Press ENTER)");
                scanner.nextLine();
                break;
            case 7:
                System.out.println(FontColors.GREEN + "\nYou found a page of a " + FontColors.WHITE + FontColors.BOLD + 
                           "Diary:" + FontColors.RESET + FontColors.GREEN + " I heard travelers talking in fear about a diabolic beast that destroys everyone who faces it...");
                System.out.println("(Press ENTER)");
                scanner.nextLine();
                break;
            case 8:
                System.out.println(FontColors.GREEN + "\nYou found a page of a " + FontColors.WHITE + FontColors.BOLD + 
                           "Diary:" + FontColors.RESET + FontColors.GREEN + " I saw a hunter burned to ashes... It was't a normal Dragon's fire. It was something worse.\n");
                System.out.println("(Press ENTER)");
                scanner.nextLine();
                break;
            case 9:
                System.out.println(FontColors.GREEN + "\nYou see a dog running toward you. It has something in its mouth... it's a " + FontColors.WHITE + FontColors.BOLD + "healing potion!\n"  + FontColors.RESET +
                FontColors.GREEN + "You take the healing potion from the dog! (Press ENTER)");
                scanner.nextLine();
                poti.counterHealPot++;
                break;
            case 10:
                System.out.println(FontColors.GREEN + "\nAll is calm, or so it seems...\n");
                break;
            case 11:
                System.out.println(FontColors.GREEN + "\nThe wind whispers... and you believe something is watching you.\n");
                break;
            case 12:
                System.out.println(FontColors.GREEN + "\nSilence... too much silence.\n");
                break;
            case 13:
                System.out.println(FontColors.GREEN + "\nYou notice footprints that aren't yours.\n");
                break;
            case 14:
                System.out.println(FontColors.GREEN + "\nAll seems peaceful... perhaps too peaceful.\n");
                break;
            case 15:
                System.out.println(FontColors.GREEN + "\nYou feel as if someone—or something—is following you.\n");
                break;
            case 16:
                System.out.println(FontColors.GREEN + "\nA cold breeze sends chills down your spine.\n");
                break;
            case 17:
                System.out.println(FontColors.GREEN + "\nBirds are singing somewhere in the distance.\n");
                break;
            case 18:
                System.out.println(FontColors.GREEN + "\nA sudden silence fills the forest.\n");
                break;
            case 19:
                System.out.println(FontColors.GREEN + "\nYou stop for a moment, enjoying the silence of nature.\n");
                break;
            case 20:
                System.out.println(FontColors.GREEN + "\nYou hear a river flowing gently nearby.\n");
                break;
            case 21:
                System.out.println(FontColors.GREEN + "\nThe forest is so dense... It feels relaxing here.\n");
                break;
            case 22:
                System.out.println(FontColors.GREEN + "\nIn the distance, mountains rise majestically, their peaks touching the clouds.\n");
                break;
            case 23:
                System.out.println(FontColors.GREEN + "\nIt's freezing here... Maybe you should have brought a jacket.\n");
                break;
            case 24:
                System.out.println(FontColors.GREEN + "\nA butterfly lands on your sword. Don't get any ideas, it's not enchanted.\n");
                break;
            case 25:
                System.out.println(FontColors.GREEN + "\nThe path looks dangerous, but hey, you look even more dangerous...\n");
                break;
            case 26:
                System.out.println(FontColors.GREEN + "\nYou take a deep breath of fresh air… and immediately sneeze.\n");
                break;
            case 27:
                System.out.println(FontColors.GREEN + "\nWait, that tree looks familiar... Am I going in circles?\n");
                break;
        }
    }
}