package com.adventure.event;

import com.adventure.item.ItemType;
import java.util.Collections;
import java.util.List;

public class ShopOffer {

    public enum Tier { NONE, BRONZE, SILVER, GOLDEN }

    private final Tier tier;
    private final List<ItemType> availableItems;
    private final int potionPrice;

    public ShopOffer(Tier tier, List<ItemType> availableItems, int potionPrice) {
        this.tier = tier;
        this.availableItems = availableItems;
        this.potionPrice = potionPrice;
    }

    public static ShopOffer none() {
        return new ShopOffer(Tier.NONE, Collections.emptyList(), 0);
    }

    public boolean appeared()                  { return tier != Tier.NONE; }
    public Tier getTier()                      { return tier; }
    public List<ItemType> getAvailableItems()  { return availableItems; }
    public int getPotionPrice()                { return potionPrice; }

    public String getTierName() {
        switch (tier) {
            case BRONZE:  return "Bronze";
            case SILVER:  return "Silver";
            case GOLDEN:  return "Golden";
            default:      return "";
        }
    }
}
