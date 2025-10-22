public class Inventory 
{
    private Potions poti;
    private Coins coin;
    private Villager villager;

    public Inventory(Potions poti, Coins coin, Villager villager)
    {
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public void objectsInInventory()
    {
        poti.showPotions();
        coin.showCoins();
        villager.showItemsBought();
    }
}