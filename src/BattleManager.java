import java.util.Random;
import java.util.Scanner;

public class BattleManager 
{
    private Scanner scanner;
    private Random rand = new Random();

    protected int winCounter = 0;

    public BattleManager() 
    {
        scanner = new Scanner(System.in);
    }

    //Auxiliar class to return the turn result
    private static class TurnResult 
    {
        int enemyHP;
        boolean combatEnded;

        TurnResult(int enemyHP, boolean combatEnded)
        {
            this.enemyHP = enemyHP;
            this.combatEnded = combatEnded;
        }
    }
    
    public void enemyAppears(Classes player, EnemyType enemyFound, Potions poti, Coins coin)
    {
        if (winCounter < 5) 
        {
            Enemies[] enemies = Enemies.values();
            enemyFound = enemies[rand.nextInt(enemies.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "An enemy appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's a "+ FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
        } 
        else if (winCounter >= 5 && winCounter < 10) 
        {
            MiniBosses[] minibosses = MiniBosses.values();
            enemyFound = minibosses[rand.nextInt(minibosses.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "A Mini Boss appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's a " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
        } 
        else if (winCounter >= 10)
        {
            enemyFound = FinalBoss.LETHALDEMIGOD;
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "The Final Boss appears!: The " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + " is here!: ");
        }

        System.out.println(FontColors.RED +"\nHP: " + FontColors.WHITE + enemyFound.getEnemyHP());
        System.out.println(FontColors.RED +"Attack: "  + FontColors.WHITE +  enemyFound.getEnemyAttack());
        System.out.println(FontColors.RED +"Attack Speed: "  + FontColors.WHITE +  enemyFound.getEnemyAttackSpeed());

        System.out.println(FontColors.WHITE + "\nYou gonna fight him? Enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
        
        while(true)
        {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y"))
            {
                System.out.println(FontColors.GREEN + "\nYou're going to fight.");
                Battle(player, enemyFound, poti, coin);
                break;
            }
            else if (input.equalsIgnoreCase("N"))
            {
                System.out.println(FontColors.YELLOW +"\nYou have run away.");
                break;
            }
            else if (input.equalsIgnoreCase("S"))
            {
                player.showStats();
                System.out.println("\nNow you want to fight or run away? Enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
            }
            else
            {
                System.out.println("\nInvalid input. Please enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
            }
        }
    }

    public void Battle(Classes player, EnemyType enemy, Potions poti, Coins coin) 
    {
        int enemyHP = enemy.getEnemyHP(); 
        boolean combatEnded = false;

        while (player.getHP() > 0 && enemyHP > 0 && !combatEnded) 
        {
            boolean playerFirst;
            if (player.getAttackSpeed() > enemy.getEnemyAttackSpeed()) 
            {
                playerFirst = true;
            } 
            else if (player.getAttackSpeed() < enemy.getEnemyAttackSpeed()) 
            {
                playerFirst = false;
            } 
            else 
            {
                playerFirst = rand.nextBoolean();
                System.out.println(FontColors.YELLOW + "\nBoth have the same attack speed! Who goes first will be decided randomly... (Press ENTER).");
                scanner.nextLine().trim();
            }

            if (playerFirst) 
            {
                TurnResult result = doPlayerTurn(player, enemyHP, poti);
                enemyHP = result.enemyHP;
                combatEnded = result.combatEnded;

                if (!combatEnded && enemyHP > 0 && player.getHP() > 0) 
                {
                    combatEnded = doEnemyTurn(player, enemy);
                }
            } 
            else 
            {
                combatEnded = doEnemyTurn(player, enemy);

                if (!combatEnded && enemyHP > 0 && player.getHP() > 0) 
                {
                    TurnResult result = doPlayerTurn(player, enemyHP, poti);
                    enemyHP = result.enemyHP;
                    combatEnded = result.combatEnded;
                }
            }

            System.out.println(FontColors.GREEN + "\nPlayer HP: " + FontColors.WHITE + Math.max(player.getHP(), 0));
            System.out.println(FontColors.RED + "Enemy HP: " + FontColors.WHITE + Math.max(enemyHP, 0));

            if (player.getHP() <= 0 || enemyHP <= 0) 
            {
                combatEnded = true;
                break;
            }
        }
        
        if (player.getHP() <= 0) 
        {
            System.out.println(FontColors.RED + "\nYou were defeated... (Press ENTER).");
            scanner.nextLine();
            System.out.println("The game will now close.");
            System.exit(0);
        }
        else if (enemyHP <= 0) 
        {
            System.out.println(FontColors.GREEN + "\nYou have won! You won " + FontColors.WHITE + "5" + FontColors.GREEN + " coins.");
            winCounter++;
            coin.amountOfCoins += 5;
            System.out.println(FontColors.YELLOW + "Current wins: " + FontColors.WHITE + winCounter + FontColors.GREEN + " (Press ENTER).");
            scanner.nextLine();
        }
    }

    private TurnResult doPlayerTurn(Classes player, int enemyHP, Potions poti) 
    {
        boolean actionTaken = false;
        boolean combatEnded = false;

        while (!actionTaken && !combatEnded) 
        {
            System.out.println(FontColors.WHITE + "\nIt's your turn!");
            System.out.println("Press 'Z' to attack, 'X' to use a potion, 'E' to show your stats, 'R' to look your inventory or 'C' to try to escape:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Z")) 
            {
                System.out.println(FontColors.GREEN + "\nYou strike the enemy!");
                enemyHP -= player.getAttack();
                System.out.println("You dealt " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + " HP damage.");
                actionTaken = true;
            } 
            else if (input.equalsIgnoreCase("X")) 
            {
                boolean potionChosen = false;

                if (poti.counterHealPot <= 0 && poti.counterDmgPot <= 0) 
                {
                    System.out.println(FontColors.RED + "You don't have any potion!");
                    continue;
                }

                while (!potionChosen)
                {
                    System.out.println("Which potion do you want to use?: Healing potion (1) or damage increase potion (2): ");
                    String potionChoice = scanner.nextLine().trim();

                    if (potionChoice.equals("1"))
                    {
                        if (poti.counterHealPot <= 0) 
                        {
                            System.out.println(FontColors.RED + "You don't have any healing potion!");
                            System.out.println(FontColors.RED + "You were distracted looking for a potion and now it's the enemy's turn!");
                            potionChosen = true;
                            actionTaken = true;
                        }
                        else
                        {
                            System.out.println(FontColors.GREEN + "\nYou restore some health by drinking a healing potion.");
                            player.setHP(player.getHP() + Potions.HEALING_POTION);
                            System.out.println(FontColors.GREEN + "Your actual HP is " + FontColors.WHITE + player.getHP());
                            poti.counterHealPot--;
                            potionChosen = true;
                            actionTaken = true;
                        }
                    }
                    else if (potionChoice.equals("2"))
                    {
                        if (poti.counterDmgPot <= 0) 
                        {
                            System.out.println(FontColors.RED + "You don't have any damage increase potion!");
                            System.out.println(FontColors.RED + "You were distracted looking for a potion and now it's the enemy's turn!");
                            potionChosen = true;
                            actionTaken = true;
                        }
                        else 
                        {
                            System.out.println(FontColors.GREEN + "\nYou gained some damage by drinking a damage increase potion.");
                            player.setAttack(player.getAttack() + Potions.DMG_POTION);
                            System.out.println(FontColors.GREEN + "Your actual damage is " + FontColors.WHITE + player.getAttack());
                            poti.counterDmgPot--;
                            potionChosen = true;
                            actionTaken = true;
                        }
                    }
                    else
                    {
                        System.out.println("Invalid input, please enter (1) or (2).");
                    }
                }
            } 
            else if (input.equalsIgnoreCase("C")) 
            {
                int chance = rand.nextInt(100);

                if (chance < 65) 
                {
                    System.out.println(FontColors.YELLOW + "\nYou have run away.");
                    scanner.nextLine();
                    combatEnded = true; 
                } 
                else 
                {
                    System.out.println(FontColors.YELLOW + "\nYou couldn't escape!");
                    actionTaken = true;
                }
            } 
            else if (input.equalsIgnoreCase("E")) 
            {
                player.showStats();
            } 
            else if (input.equalsIgnoreCase("R"))
            {
                poti.showPotions();
            }
            else 
            {
                System.out.println("\nInvalid input. Use 'Z', 'X', 'E', 'R' or 'C'.");
            }
        }
        return new TurnResult(enemyHP, combatEnded);
    }

    private boolean doEnemyTurn(Classes player, EnemyType enemy) 
    {
        System.out.println(FontColors.WHITE + "\nEnemy's turn:");
        System.out.println(FontColors.RED + "The enemy attacks you!");
        player.setHP(player.getHP() - enemy.getEnemyAttack());
        System.out.println("It deals " + FontColors.WHITE + enemy.getEnemyAttack() + FontColors.RED + " HP damage.");

        return false; 
    }
}
