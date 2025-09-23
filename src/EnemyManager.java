import java.util.Random;
import java.util.Scanner;

public class EnemyManager 
{
    protected int winCounter = 0;

    private Scanner scanner;
    private Random rand = new Random();

    public EnemyManager() 
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
    
    public void enemyAppears(Classes player, EnemyType enemyFound)
    {
        if (winCounter < 5) 
        {
            Enemies[] enemies = Enemies.values();
            enemyFound = enemies[rand.nextInt(enemies.length)];
        } 
        else if (winCounter >= 5 && winCounter < 10) 
        {
            MiniBosses[] minibosses = MiniBosses.values();
            enemyFound = minibosses[rand.nextInt(minibosses.length)];
        } 
        else if (winCounter >= 10)
        {
            enemyFound = FinalBoss.LETHALDEMIGOD;
        }

        System.out.println(FontColors.RED + "\nEvent: An enemy appears! It's a mysterious creature.");
        System.out.println(FontColors.RED +"\nIt's a " + enemyFound);  
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
                Battle(player, enemyFound);
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

    public void Battle(Classes player, EnemyType enemy) 
    {
        int enemyHP = enemy.getEnemyHP(); 
        boolean combatEnded = false;

        System.out.println("\nA battle is about to begin!:");

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
                TurnResult result = doPlayerTurn(player, enemyHP);
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
                    TurnResult result = doPlayerTurn(player, enemyHP);
                    enemyHP = result.enemyHP;
                    combatEnded = result.combatEnded;
                }
            }

            //Show HP
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
            System.out.println(FontColors.GREEN + "\nYou have won!");
            winCounter++;
            System.out.println("Current wins: " + winCounter + " (Press ENTER).");
            scanner.nextLine();
        } 
        else if (combatEnded) 
        {
            System.out.println(FontColors.YELLOW + "\nYou have run away.");
            scanner.nextLine();
        }
    }

    private TurnResult doPlayerTurn(Classes player, int enemyHP) 
    {
        boolean actionTaken = false;
        boolean combatEnded = false;

        while (!actionTaken && !combatEnded) 
        {
            System.out.println(FontColors.WHITE + "\nIt's your turn!");
            System.out.println("Press 'Z' to attack, 'X' to heal, 'C' to try to escape or 'S' to show your stats:");
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
                System.out.println(FontColors.GREEN + "\nYou restore some health by drinking a potion.");
                player.setHP(player.getHP() + Potions.HEALING_POTION);
                System.out.println(FontColors.GREEN + "Your actual HP is " + FontColors.WHITE + player.getHP());
                actionTaken = true;
            } 
            else if (input.equalsIgnoreCase("C")) 
            {
                int chance = rand.nextInt(100);

                if (chance < 65) 
                {
                    System.out.println(FontColors.YELLOW + "\nYou have run away.");
                    combatEnded = true; 
                } 
                else 
                {
                    System.out.println(FontColors.YELLOW + "\nYou couldn't escape!");
                    actionTaken = true;
                }
            } 
            else if (input.equalsIgnoreCase("S")) 
            {
                player.showStats();
            } 
            else 
            {
                System.out.println("\nInvalid input. Use 'Z', 'X', 'C' o 'S'.");
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
