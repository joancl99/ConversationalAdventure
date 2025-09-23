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

    // Clase auxiliar para devolver resultado del turno
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
            enemyFound = FinalBoss.MEGADIOSMORTIFERO;
        }

        System.out.println(FontColors.RED + "\nEvent: An enemy appears! It's a mysterious creature.");
        System.out.println(FontColors.RED +"\nIt's a " + enemyFound);  
        System.out.println(FontColors.RED +"\nHP: " + FontColors.WHITE + enemyFound.getEnemyHP());
        System.out.println(FontColors.RED +"Attack: "  + FontColors.WHITE +  enemyFound.getEnemyAttack());
        System.out.println(FontColors.RED +"Attack Speed: "  + FontColors.WHITE +  enemyFound.getEnemyAttackSpeed());
        System.out.println(FontColors.RED +"Range: "  + FontColors.WHITE +  enemyFound.getEnemyRange());

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
                System.out.println(FontColors.RED +"\nYou have run away.");
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

        System.out.println("\nComienza el combate:");

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
                System.out.println(FontColors.YELLOW + "\n¡Ambos tienen la misma velocidad! Se decide al azar quién va primero... (Pulse 'ENTER')");
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

            // Mostrar HP tras la ronda
            System.out.println(FontColors.GREEN + "\nHP jugador: " + FontColors.WHITE + Math.max(player.getHP(), 0));
            System.out.println(FontColors.RED + "HP enemigo: " + FontColors.WHITE + Math.max(enemyHP, 0));

            if (player.getHP() <= 0 || enemyHP <= 0) 
            {
                combatEnded = true;
                break;
            }
        }
        
        if (player.getHP() <= 0) 
        {
            System.out.println(FontColors.RED + "\nHas sido derrotado...");
            scanner.nextLine();
        } 
        else if (enemyHP <= 0) 
        {
            System.out.println(FontColors.GREEN + "\n¡Has ganado!");
            winCounter++;
            System.out.println("llevas " + winCounter + " victorias");
            scanner.nextLine();
        } 
        else if (combatEnded) 
        {
            System.out.println(FontColors.YELLOW + "\nHas huido del combate.");
            scanner.nextLine();
        }
    }

    private TurnResult doPlayerTurn(Classes player, int enemyHP) 
    {
        boolean actionTaken = false;
        boolean combatEnded = false;

        while (!actionTaken && !combatEnded) 
        {
            System.out.println(FontColors.WHITE + "\nTu turno:");
            System.out.println("Pulsa Z para atacar, X para curarte, C para intentar huir o 'S' para mostrar tus stats: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Z")) 
            {
                System.out.println(FontColors.GREEN + "Lanzas un Kaioken.");
                enemyHP -= player.getAttack();
                System.out.println("Le has quitado " + FontColors.WHITE + player.getAttack() + FontColors.GREEN + " de HP.");
                actionTaken = true;
            } 
            else if (input.equalsIgnoreCase("X")) 
            {
                System.out.println(FontColors.GREEN + "Te has tomado una poción.");
                player.setHP(player.getHP() + Potions.HEALING_POTION);
                System.out.println(FontColors.GREEN + "Tu vida actual es de " + FontColors.WHITE + player.getHP());
                actionTaken = true;
            } 
            else if (input.equalsIgnoreCase("C")) 
            {
                int chance = rand.nextInt(100);

                if (chance < 65) 
                {
                    System.out.println("Has huido con éxito.");
                    combatEnded = true; 
                } 
                else 
                {
                    System.out.println("No has podido escapar.");
                    actionTaken = true;
                }
            } 
            else if (input.equalsIgnoreCase("S")) 
            {
                player.showStats();
            } 
            else 
            {
                System.out.println("\nEntrada inválida. Usa 'Z', 'X', 'C' o 'S'.");
            }
        }
        return new TurnResult(enemyHP, combatEnded);
    }

    private boolean doEnemyTurn(Classes player, EnemyType enemy) 
    {
        System.out.println(FontColors.WHITE + "\nTurno del enemigo:");
        System.out.println(FontColors.RED + "El enemigo ha lanzado una bomba invasora de mundos");

        player.setHP(player.getHP() - enemy.getEnemyAttack());
        System.out.println("Te ha quitado " + FontColors.WHITE + enemy.getEnemyAttack() + FontColors.RED + " de HP.");

        return false; 
    }
}
