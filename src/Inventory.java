public class Inventory {
    private Potions poti;
    private Coins coin;
    private Villager villager;

    public Inventory(Potions poti, Coins coin, Villager villager) {
        this.poti = poti;
        this.coin = coin;
        this.villager = villager;
    }

    public void objectsInInventory() {
        poti.showPotions();
        coin.showCoins();
        villager.showItemsBought();
    }

    // Getters necesarios para Save
    public Potions getPotions() { return poti; }
    public Coins getCoins() { return coin; }
    public Villager getVillager() { return villager; }

    // Serializar todo el inventario como texto
    public String serialize() {
        return poti.serialize() + "\n" + coin.serialize() + "\n" + villager.serializeItems();
    }

    // Deserializar desde texto
    public void deserialize(String data) {
        if (data != null) {
            String[] lines = data.split("\n");
            if (lines.length > 0) poti.deserialize(lines[0]);
            if (lines.length > 1) coin.deserialize(lines[1]);
            if (lines.length > 2) villager.deserializeItems(lines[2]);
        }
    }
}
