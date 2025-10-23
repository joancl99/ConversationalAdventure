import java.util.Random;
import java.util.Scanner;

public class Potions 
{
    protected static final int HEALING_POTION = 100;
    protected static final int DMG_POTION = 50;

    protected int counterHealPot = 0;
    protected int counterDmgPot = 0;

    protected final int pricePotions = 15;

    private Random rand = new Random();
    private Scanner scanner;

    public Potions(int counterHealPot, int counterDmgPot) 
    {
        scanner = new Scanner(System.in);

        this.counterHealPot = counterHealPot;
        this.counterDmgPot = counterDmgPot;
    }

    public void generatePotions()
    {
        int chancePotion = rand.nextInt(100);

        if (chancePotion < 60)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int potionAppears = rand.nextInt(2);

            switch (potionAppears) 
            {
                case 0:
                    System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a healing potion, it heals " + HEALING_POTION + " hp.");
                    System.out.println(FontColors.GREEN + "You take the healing potion. (Press ENTER)");
                    scanner.nextLine();
                    counterHealPot++;
                    break;
                case 1:
                    System.out.println(FontColors.YELLOW + "\nEvent: " + FontColors.GREEN + "You found a damage increase potion, it gives " + DMG_POTION + " damage.");
                    System.out.println(FontColors.GREEN + "You take the damage potion. (Press ENTER)");
                    scanner.nextLine();
                    counterDmgPot++;
                    break;
            }
        }
    }

    public int getPotionPrice()
    {
        return pricePotions;
    }

    public void showPotions()
    {
        System.out.println(FontColors.CYAN + "\nPotions:");
        System.out.println(FontColors.PURPLE + "Healing Potion: " + FontColors.WHITE + counterHealPot);
        System.out.println(FontColors.PURPLE + "Damage Potion: " + FontColors.WHITE + counterDmgPot);
    }
}
