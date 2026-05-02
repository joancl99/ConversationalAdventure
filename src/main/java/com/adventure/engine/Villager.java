package com.adventure.engine;

import com.adventure.event.PurchaseResult;
import com.adventure.event.ShopOffer;
import com.adventure.item.*;
import com.adventure.model.*;
import com.adventure.util.FontColors;
import java.util.*;

public class Villager {

    private final Random rand = new Random();
    private final Set<ItemType> itemsBuyed = new HashSet<>();

    // Returns ShopOffer.none() 80% of the time; otherwise a tiered offer
    public ShopOffer generateOffer(int playerCoins) {
        if (rand.nextInt(100) < 80) return ShopOffer.none();
        return buildOffer(tierFor(playerCoins));
    }

    // Called on each shop-loop iteration to reflect items bought since last refresh
    public ShopOffer refreshOffer(ShopOffer.Tier tier) {
        return buildOffer(tier);
    }

    public PurchaseResult buyPotion(String type, Potions poti, Coins coin) {
        if (coin.getCoins() < poti.getPotionPrice()) return PurchaseResult.insufficientFunds();
        if ("heal".equalsIgnoreCase(type)) poti.addHealPotion(1);
        else                               poti.addDamagePotion(1);
        coin.removeCoins(poti.getPotionPrice());
        return PurchaseResult.success();
    }

    public PurchaseResult buyItem(Player player, ItemType item, Coins coin) {
        if (coin.getCoins() < item.getItemPrice()) return PurchaseResult.insufficientFunds();
        player.setAttack(player.getAttack() + item.getItemAttack());
        player.setAttackSpeed(player.getAttackSpeed() + item.getItemAttackSpeed());
        player.setMaxHp(player.getMaxHp() + item.getItemHP());
        player.setHP(Math.min(player.getHP() + item.getItemHP(), player.getMaxHp()));
        coin.removeCoins(item.getItemPrice());
        itemsBuyed.add(item);
        return PurchaseResult.success();
    }

    // Console helper — GUI will render inventory with dedicated UI components
    public void showItemsBought() {
        if (itemsBuyed.isEmpty()) return;
        System.out.println(FontColors.CYAN + "\nItems you've bought:");
        for (ItemType item : itemsBuyed) {
            System.out.println(FontColors.PURPLE + item.getItemName() + ":" + FontColors.WHITE
                    + "(HP: " + item.getItemHP() + ", Attack: " + item.getItemAttack()
                    + ", Attack Speed: " + item.getItemAttackSpeed() + ")");
        }
    }

    public String serializeItems() {
        StringBuilder sb = new StringBuilder();
        for (ItemType item : itemsBuyed) sb.append(item.getItemName()).append(";");
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public void deserializeItems(String data) {
        itemsBuyed.clear();
        if (data == null || data.isEmpty()) return;
        for (String name : data.split(";")) {
            ItemType item = ItemsRegistry.getItemByName(name);
            if (item != null) itemsBuyed.add(item);
        }
    }

    // -------------------------------------------------------------------------

    private ShopOffer.Tier tierFor(int playerCoins) {
        if (playerCoins <= 60)  return ShopOffer.Tier.BRONZE;
        if (playerCoins <= 200) return ShopOffer.Tier.SILVER;
        return ShopOffer.Tier.GOLDEN;
    }

    private ShopOffer buildOffer(ShopOffer.Tier tier) {
        List<ItemType> all = itemsForTier(tier);
        List<ItemType> available = new ArrayList<>();
        for (ItemType item : all) {
            if (!itemsBuyed.contains(item)) available.add(item);
        }
        return new ShopOffer(tier, available, Potions.PRICE);
    }

    private List<ItemType> itemsForTier(ShopOffer.Tier tier) {
        switch (tier) {
            case BRONZE: return Arrays.asList(Items.BRUTAL_THIEF_KNIFE, Items.DEAD_MANS_ARMOR);
            case SILVER: return Arrays.asList(Items.HAMMER_OF_PURIFICATION, Items.SORCERER_HEAD);
            case GOLDEN: return Arrays.asList(Items.HELMET_OF_THE_CURSED_ABYSS, Items.MALEFIC_FIRE_SWORD);
            default:     return Collections.emptyList();
        }
    }
}
