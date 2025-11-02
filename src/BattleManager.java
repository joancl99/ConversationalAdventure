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
        boolean enemyAlreadyAttacked;

        TurnResult(int enemyHP, boolean combatEnded) 
        {
            this(enemyHP, combatEnded, false);
        }

        TurnResult(int enemyHP, boolean combatEnded, boolean enemyAlreadyAttacked) 
        {
            this.enemyHP = enemyHP;
            this.combatEnded = combatEnded;
            this.enemyAlreadyAttacked = enemyAlreadyAttacked;
        }
    }

    private boolean handlePotionUsage(Classes player, Potions poti, EnemyType enemyFound)
    {
        boolean potionChosen = false;
        boolean enemyAttacked = false;

        if (poti.counterHealPot <= 0 && poti.counterDmgPot <= 0) 
        {
            System.out.println(FontColors.RED + "You don't have any potion!");
            System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
            doEnemyTurn(player, enemyFound);
            return true; // enemy attacks
        }

        while (!potionChosen)
        {
            System.out.println("Which potion do you want to use? Healing (1) / Damage (2): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1"))
            {
                if (poti.counterHealPot <= 0)
                {
                    System.out.println(FontColors.RED + "\nYou don't have healing potions!");
                    System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
                    doEnemyTurn(player, enemyFound);
                    enemyAttacked = true;
                }
                else
                {
                    System.out.println(FontColors.GREEN + "\nYou gonna use a healing potion!");

                    if (player.getHP() >= player.maxHp)
                    {   
                        System.out.println(FontColors.RED + "You cannot use a healing potion, you are already full HP!\n" + FontColors.RESET);
                        continue;
                    }
                    else
                    {
                        int hpBeforeHealing = player.getHP();
                        player.setHP(player.getHP() + Potions.HEALING_POTION);

                        // Adjusts if it exceeds the maximum
                        if (player.getHP() > player.maxHp)
                        player.setHP(player.maxHp);

                        int restoredHp = player.getHP() - hpBeforeHealing;
                        System.out.println(FontColors.GREEN + "You restored " + FontColors.WHITE + restoredHp + FontColors.GREEN +" HP.");

                        poti.counterHealPot--;
                    }

                    System.out.println(FontColors.GREEN + "Current HP: " + FontColors.WHITE + player.getHP());
                    doEnemyTurn(player, enemyFound);
                    enemyAttacked = true;
                }
                potionChosen = true;
            }
            else if (choice.equals("2"))
            {
                if (poti.counterDmgPot <= 0)
                {
                    System.out.println(FontColors.RED + "\nYou don't have damage potions!");
                    System.out.println(FontColors.RED + "You were distracted... the enemy attacks!");
                    doEnemyTurn(player, enemyFound);
                    enemyAttacked = true;
                }
                else
                {
                    System.out.println(FontColors.GREEN + "\nYou drink a damage potion and feel stronger!");
                    player.setAttack(player.getAttack() + Potions.DMG_POTION);
                    System.out.println(FontColors.GREEN + "Current Attack: " + FontColors.WHITE + player.getAttack());
                    poti.counterDmgPot--;
                    doEnemyTurn(player, enemyFound);
                    enemyAttacked = true;
                }
                potionChosen = true;
            }
            else
            {
                System.out.println(FontColors.RED + "Invalid input, please enter '1' or '2'.");
            }
        }

        return enemyAttacked;
    }

    
    public void enemyAppears(Classes player, EnemyType enemyFound, Potions poti, Coins coin)
    {
        if (winCounter < 10) 
        {
            Enemies[] enemies = Enemies.values();
            enemyFound = enemies[rand.nextInt(enemies.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "An Enemy appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's the "+ FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
        } 
        else if (winCounter >= 10 && winCounter < 20) 
        {
            MiniBosses[] minibosses = MiniBosses.values();
            enemyFound = minibosses[rand.nextInt(minibosses.length)];
            System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.RED + "A Big Enemy appears! It's a mysterious creature!");
            System.out.println(FontColors.RED +"\nIt's the " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.RED + ":");  
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
                System.out.println(FontColors.RED + "\nInvalid input. Please enter 'Y' to fight, 'N' to scape or 'S' to show your stats.");
            }
        }
    }

    public void Battle(Classes player, EnemyType enemyFound, Potions poti, Coins coin) 
    {
        int enemyHP = enemyFound.getEnemyHP(); 
        boolean combatEnded = false;

        System.out.println(FontColors.YELLOW + "\nThe battle begins! Prepare yourself... " + FontColors.WHITE + "(Press ENTER)");
        scanner.nextLine();

        while (player.getHP() > 0 && enemyHP > 0 && !combatEnded) 
        {
            boolean playerFirst;

            if (player.getAttackSpeed() > enemyFound.getEnemyAttackSpeed()) 
            {
                playerFirst = true;
            } 
            else if (player.getAttackSpeed() < enemyFound.getEnemyAttackSpeed()) 
            {
                playerFirst = false;
            } 
            else 
            {
                playerFirst = rand.nextBoolean();
                System.out.println(FontColors.YELLOW + "\nBoth have the same attack speed! Who goes first will be decided randomly... " + FontColors.WHITE + "(Press ENTER)");
                scanner.nextLine();
            }

            //playerturn (the enemy can attack before, if it's faster)
            TurnResult result = doPlayerTurn(player, enemyHP, poti, enemyFound, playerFirst);
            enemyHP = result.enemyHP;

            if (result.combatEnded) 
            {
                combatEnded = true;
                break;
            }

            //enemyturn (just if he doesn't attack in the turn of the player)
            if (playerFirst && enemyHP > 0 && player.getHP() > 0 && !combatEnded && !result.enemyAlreadyAttacked)
            {
                doEnemyTurn(player, enemyFound);
            }

            System.out.println(FontColors.GREEN + "\nPlayer HP: " + FontColors.WHITE + Math.max(player.getHP(), 0));
            System.out.println(FontColors.RED + "Enemy HP: " + FontColors.WHITE + Math.max(enemyHP, 0));

            if (player.getHP() <= 0 || enemyHP <= 0) 
            {
                combatEnded = true;
            }
        }

        //combat END
        if (player.getHP() <= 0) 
        {
            System.out.println(FontColors.RED + "\nYou were defeated... (Press ENTER)");
            scanner.nextLine();
            System.out.println("The game will now close.");
            System.exit(0);
        }
        else if (enemyFound == FinalBoss.LETHALDEMIGOD && enemyHP <= 0) 
        {
            System.out.println(FontColors.GREEN + "\nVictory! You have defeated The " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.GREEN + ".");
            System.out.println(FontColors.YELLOW + "\nNow the darkness has fallen. Your name will be sung in every corner of this world.");
            System.out.println(FontColors.YELLOW + FontColors.BOLD + "\n\nTHE END\n\n" + FontColors.RESET);
            System.exit(0);
        } 
        else if (enemyHP <= 0) 
        {  
            System.out.println(FontColors.GREEN + "\nVictory! You have defeated the " + FontColors.BOLD + FontColors.WHITE + enemyFound.getEnemyName() + FontColors.RESET + FontColors.GREEN + ".");
            System.out.println(FontColors.GREEN + "You received " + FontColors.WHITE + "5" + FontColors.GREEN + " coins.");
            winCounter++;
            coin.amountOfCoins += 5;
            System.out.println(FontColors.YELLOW + "\nCurrent wins: " + FontColors.WHITE + winCounter + FontColors.WHITE + " (Press ENTER).");
            scanner.nextLine();
        }
    }


    private TurnResult doPlayerTurn(Classes player, int enemyHP, Potions poti, EnemyType enemyFound, boolean playerFirst)
    {
        boolean actionTaken = false;
        boolean combatEnded = false;

        while (!actionTaken && !combatEnded) 
        {
            System.out.println(FontColors.WHITE + "\nIt's your turn!");
            System.out.println("Press 'Z' to attack, 'R' to check your potions, 'X' to use one, 'E' to check your stats, or 'C' for try to escape:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Z")) 
            {
                //enemy faster
                if (player.getAttackSpeed() < enemyFound.getEnemyAttackSpeed()) 
                {
                    System.out.println(FontColors.RED + "\nThe enemy attacks first due to higher speed!");
                    combatEnded = doEnemyTurn(player, enemyFound);

                    if (player.getHP() <= 0 || combatEnded)
                    {
                        break;
                    }
                }
                //same speed
                else if (player.getAttackSpeed() == enemyFound.getEnemyAttackSpeed()) 
                {
                    if (!playerFirst)
                    {
                        System.out.println(FontColors.YELLOW + "\nThe enemy moves first this time! It was a random tie!");
                        combatEnded = doEnemyTurn(player, enemyFound);
                        if (player.getHP() <= 0 || combatEnded) break;
                    }
                    else
                    {
                        System.out.println(FontColors.YELLOW + "\nYou move first this time! It was a random tie!");
                    }
                }

                //player attack
                System.out.println(FontColors.WHITE + "\nPlayer's turn:");
                System.out.println(FontColors.GREEN + "You strike the enemy!");
                enemyHP -= player.getAttack();
                System.out.println("You dealt " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + " HP damage.");

                actionTaken = true;
            } 
            else if (input.equalsIgnoreCase("X")) 
            {
                boolean enemyAttacked = handlePotionUsage(player, poti, enemyFound);
                actionTaken = true;
                return new TurnResult(enemyHP, combatEnded, enemyAttacked);
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
                System.out.println(FontColors.RED + "\nInvalid input. Use 'Z', 'X', 'E', 'R' or 'C'.");
            }
        }

        return new TurnResult(enemyHP, combatEnded);
    }


    private boolean doEnemyTurn(Classes player, EnemyType enemyFound) 
    {
        System.out.println(FontColors.WHITE + "\nEnemy's turn:");
        System.out.println(FontColors.RED + "The enemy attacks you!");
        player.setHP(player.getHP() - enemyFound.getEnemyAttack());
        System.out.println("It deals " + FontColors.WHITE + enemyFound.getEnemyAttack() + FontColors.RED + " HP damage.");

        return false; 
    }
}
