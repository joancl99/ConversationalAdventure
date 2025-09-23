import java.util.Random;

public class Potions 
{
    protected static final int HEALING_POTION = 300;
    protected static final int DMG_POTION = 50;
    protected static final int ATK_SPEED_POTION = 50;
    protected static final int RANGE_POTION = 50;

    protected int counterHealPot = 0;
    protected int counterDmgPot = 0;
    protected int counterAtkSpeedPot = 0;
    protected int counterRangePot = 0;

    private Random rand = new Random();

    public Potions(int counterHealPot, int counterDmgPot, int counterAtkSpeedPot, int counterRangePot) 
    {
        this.counterHealPot = counterHealPot;
        this.counterDmgPot = counterDmgPot;
        this.counterAtkSpeedPot = counterAtkSpeedPot;
        this.counterRangePot = counterRangePot;
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
            int potionAppears = rand.nextInt(4); // 0 o 3

            switch (potionAppears) 
            {
                case 0:
                    System.out.println(FontColors.GREEN + "\nEvent: You found a healing potion, it heals " + HEALING_POTION + " hp.\n");
                    counterHealPot++;
                    break;
                case 1:
                    System.out.println(FontColors.RED + "\nEvent: You found a damage increase potion, it gives " + DMG_POTION + " damage.\n");
                    counterDmgPot++;
                    break;
                case 2:
                    System.out.println(FontColors.YELLOW + "\nEvent: You found a attack speed increase potion, it gives " + ATK_SPEED_POTION + " of attack speed.\n");
                    counterAtkSpeedPot++;
                    break;
                case 3:
                    System.out.println(FontColors.BLUE + "\nEvent: You found a range increase potion, it gives " + RANGE_POTION + " of range.\n");
                    counterRangePot++;
                    break;
            }
        }
    }

    public void showPotions()
    {
        System.out.println(FontColors.CYAN + "\nPotions:");  // Imprime el nombre del enum, método name() se utiliza exclusivamente con enums, devuelve el nombre exacto de la constante del enum
        System.out.println(FontColors.PURPLE + "\nHealing Potion: " + FontColors.WHITE + counterHealPot);
        System.out.println(FontColors.PURPLE + "Damage Potion: " + FontColors.WHITE + counterDmgPot);
        System.out.println(FontColors.PURPLE + "Attack Speed Potion: " + FontColors.WHITE + counterAtkSpeedPot);
        System.out.println(FontColors.PURPLE + "Range Potion: " + FontColors.WHITE + counterRangePot);
    }
}
