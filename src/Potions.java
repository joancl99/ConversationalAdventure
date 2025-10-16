import java.util.Random;

public class Potions 
{
    protected static final int HEALING_POTION = 100;
    protected static final int DMG_POTION = 50;

    protected int counterHealPot = 0;
    protected int counterDmgPot = 0;

    protected final int pricePotions = 15;

    private Random rand = new Random();

    public Potions(int counterHealPot, int counterDmgPot) 
    {
        this.counterHealPot = counterHealPot;
        this.counterDmgPot = counterDmgPot;
    }

    public void generatePotions()
    {
        int chancePotion = rand.nextInt(100);

        if (chancePotion < 75)
        {
            System.out.println("\nNothing happens. You keep advancing.\n");
        }
        else 
        {
            int potionAppears = rand.nextInt(2); // 0 o 1

            switch (potionAppears) 
            {
                case 0:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a healing potion, it heals " + HEALING_POTION + " hp.\n");
                    counterHealPot++;
                    break;
                case 1:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a damage increase potion, it gives " + DMG_POTION + " damage.\n");
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
        System.out.println(FontColors.PURPLE + "\nHealing Potion: " + FontColors.WHITE + counterHealPot);
        System.out.println(FontColors.PURPLE + "Damage Potion: " + FontColors.WHITE + counterDmgPot);
    }
}
