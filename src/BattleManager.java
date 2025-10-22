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
        if (winCounter < 10) 
        {
            Enemies[] enemies = Enemies.values();
            enemyFound = enemies[rand.nextInt(enemies.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "An Enemy appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's a "+ FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
        } 
        else if (winCounter >= 10 && winCounter < 20) 
        {
            MiniBosses[] minibosses = MiniBosses.values();
            enemyFound = minibosses[rand.nextInt(minibosses.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "A Big Enemy appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's a " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
        } 
        else if (winCounter >= 20)
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

    System.out.println(FontColors.YELLOW + "\nThe battle begins! Prepare yourself... (Press ENTER)");
    scanner.nextLine();

    while (player.getHP() > 0 && enemyHP > 0 && !combatEnded) 
    {
        // Determinar quién es más rápido
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
            System.out.println(FontColors.YELLOW + 
                "\nBoth have the same attack speed! Who goes first will be decided randomly... (Press ENTER).");
            scanner.nextLine().trim();
        }

        // Mostrar turno del jugador primero, pero aplicar el orden real después
        TurnResult result = doPlayerTurn(player, enemyHP, poti, enemy, playerFirst);
        enemyHP = result.enemyHP;

        if (result.combatEnded) 
        {
            combatEnded = true;
            break;
        }

        // Si el jugador fue más rápido, el enemigo ataca después
        if (playerFirst && enemyHP > 0 && player.getHP() > 0 && !combatEnded)
        {
            combatEnded = doEnemyTurn(player, enemy);
        }

        System.out.println(FontColors.GREEN + "\nPlayer HP: " + FontColors.WHITE + Math.max(player.getHP(), 0));
        System.out.println(FontColors.RED + "Enemy HP: " + FontColors.WHITE + Math.max(enemyHP, 0));

        if (player.getHP() <= 0 || enemyHP <= 0) 
        {
            combatEnded = true;
            break;
        }
    }

    // Fin del combate
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


private TurnResult doPlayerTurn(Classes player, int enemyHP, Potions poti, EnemyType enemy, boolean playerFirst)
{
    boolean actionTaken = false;
    boolean combatEnded = false;

    while (!actionTaken && !combatEnded) 
    {
        System.out.println(FontColors.WHITE + "\nIt's your turn!");
        System.out.println("Press 'Z' to attack, 'R' to check your potions, 'X' to select the potion you want to use (if you have one), 'E' to show your stats or 'C' to try to escape:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("Z")) 
        {
            // if the enemy is faster, hits first
            if (player.getAttackSpeed() < enemy.getEnemyAttackSpeed()) 
            {
                System.out.println(FontColors.RED + "\nThe enemy attacks first due to higher speed!");
                combatEnded = doEnemyTurn(player, enemy);

                if (player.getHP() <= 0 || combatEnded)
                    break;
            }
            // if they have the same speed, and the enemy win the random
            else if (player.getAttackSpeed() == enemy.getEnemyAttackSpeed() && !playerFirst)
            {
                System.out.println(FontColors.YELLOW + "\nThe enemy moves first this time! It was a random tie!");
                combatEnded = doEnemyTurn(player, enemy);

                if (player.getHP() <= 0 || combatEnded)
                {
                    break;
                }
            }
            else
            {
                System.out.println(FontColors.YELLOW + "\nYou moves first this time! It was a random tie!");
            }

            // Player attacks
            System.out.println(FontColors.WHITE + "\nPlayer's turn:");
            System.out.println(FontColors.GREEN + "You strike the enemy!");
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
                System.out.println(FontColors.RED + "You were distracted looking for a potion and now it's the enemy's turn!");
                potionChosen = true;
                actionTaken = true;
            }

            while (!potionChosen)
            {
                System.out.println("Which potion do you want to use?: Healing potion (Press '1') or damage increase potion (Press '2'): ");
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
                    System.out.println("Invalid input, please enter '1' or '2'.");
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
