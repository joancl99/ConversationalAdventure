package com.adventure.ui.fx;

import com.adventure.engine.Villager;
import com.adventure.event.PurchaseResult;
import com.adventure.event.ShopOffer;
import com.adventure.item.ItemType;
import com.adventure.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

public class ShopController {

    @FXML private Label  lblTierBadge;
    @FXML private Label  lblCoinsHeader;
    @FXML private Button btnHeal;
    @FXML private Button btnDmg;
    @FXML private VBox   itemsSection;
    @FXML private Label  lblFeedback;
    @FXML private Label  lblCoinsStatus;
    @FXML private Label  lblPotionsStatus;

    private SceneManager sceneManager;
    private GameSession  session;
    private Player       player;
    private Potions      potions;
    private Coins        coins;
    private Villager     villager;
    private ShopOffer    offer;

    public void initShop(SceneManager sceneManager, GameSession session, ShopOffer offer) {
        this.sceneManager = sceneManager;
        this.session      = session;
        this.player       = session.getPlayer();
        this.potions      = session.getPotions();
        this.coins        = session.getCoins();
        this.villager     = session.getVillager();
        this.offer        = offer;

        applyTierStyle();
        refreshPotionButtons();
        refreshItemsSection();
        refreshStatus();
    }

    // ── Potion purchases ───────────────────────────────────────────────────────
    @FXML
    private void onBuyHeal() {
        int price = potions.getPotionPrice();
        PurchaseResult result = villager.buyPotion("heal", potions, coins);
        if (result.isSuccess()) {
            showFeedback("💊 Heal potion purchased!", "feedback-success");
            session.addToLog("  💊 Purchased: Heal Potion  (-" + price + " coins)", LogPalette.HEAL);
        } else {
            showFeedback("Not enough coins. Need " + price + "c.", "feedback-error");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    @FXML
    private void onBuyDmg() {
        int price = potions.getPotionPrice();
        PurchaseResult result = villager.buyPotion("damage", potions, coins);
        if (result.isSuccess()) {
            showFeedback("⚗  Damage potion purchased!", "feedback-buff");
            session.addToLog("  ⚗  Purchased: Damage Potion  (-" + price + " coins)", LogPalette.BUFF);
        } else {
            showFeedback("Not enough coins. Need " + price + "c.", "feedback-error");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    @FXML
    private void onLeave() {
        session.addToLog("  Left the shop.  [Coins remaining: " + coins.getCoins() + "]", LogPalette.SOFT);
        sceneManager.showWorld();
    }

    // ── Item cards ─────────────────────────────────────────────────────────────
    private void refreshItemsSection() {
        itemsSection.getChildren().clear();

        offer = villager.refreshOffer(offer.getTier());
        java.util.List<ItemType> items = offer.getAvailableItems();

        if (items.isEmpty()) {
            Label lbl = new Label("All items from this merchant have been purchased.");
            lbl.getStyleClass().add("note-italic");
            itemsSection.getChildren().add(lbl);
            return;
        }

        Label header = new Label("ITEMS");
        header.getStyleClass().add("section-label");
        itemsSection.getChildren().add(header);

        HBox cards = new HBox(18);
        cards.setAlignment(Pos.TOP_LEFT);
        for (ItemType item : items) {
            cards.getChildren().add(buildItemCard(item));
        }
        itemsSection.getChildren().add(cards);

        Separator sep = new Separator();
        VBox.setMargin(sep, new Insets(20, 0, 0, 0));
        itemsSection.getChildren().add(sep);
    }

    private VBox buildItemCard(ItemType item) {
        VBox card = new VBox(12);
        card.setPrefWidth(210);
        card.setAlignment(Pos.TOP_LEFT);
        card.getStyleClass().add("panel-frame");

        Label name = new Label(item.getItemName());
        name.getStyleClass().add("item-name");
        name.setWrapText(true);
        name.setMaxWidth(180);

        Separator sep = new Separator();

        VBox stats = new VBox(6);
        if (item.getItemHP() != 0)
            stats.getChildren().add(statRow("HP",  "+" + item.getItemHP()));
        if (item.getItemAttack() != 0)
            stats.getChildren().add(statRow("ATK", "+" + item.getItemAttack()));
        if (item.getItemAttackSpeed() != 0)
            stats.getChildren().add(statRow("SPD", "+" + item.getItemAttackSpeed()));

        Button buyBtn = new Button("BUY  —  " + item.getItemPrice() + " coins");
        buyBtn.setPrefWidth(178);
        buyBtn.setPrefHeight(36);
        buyBtn.getStyleClass().add("btn-secondary");
        buyBtn.setDisable(coins.getCoins() < item.getItemPrice());
        buyBtn.setOnAction(e -> onBuyItem(item));

        card.getChildren().addAll(name, sep, stats, buyBtn);
        return card;
    }

    private HBox statRow(String label, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        Label lbl = new Label(label);
        lbl.getStyleClass().add("text-stat-label");
        lbl.setPrefWidth(44);
        Label val = new Label(value);
        val.getStyleClass().add("text-stat-value");
        row.getChildren().addAll(lbl, val);
        return row;
    }

    private void onBuyItem(ItemType item) {
        PurchaseResult result = villager.buyItem(player, item, coins);
        if (result.isSuccess()) {
            showFeedback("✓ " + item.getItemName() + " purchased! Stats upgraded.", "feedback-success");
            session.addToLog("  ✓ Purchased: " + item.getItemName()
                    + "  (-" + item.getItemPrice() + " coins)", LogPalette.HEAL);
            if (item.getItemHP()          != 0) session.addToLog("    HP  +" + item.getItemHP(),          LogPalette.HEAL);
            if (item.getItemAttack()      != 0) session.addToLog("    ATK +" + item.getItemAttack(),      LogPalette.DAMAGE);
            if (item.getItemAttackSpeed() != 0) session.addToLog("    SPD +" + item.getItemAttackSpeed(), LogPalette.LORE);
            refreshItemsSection();
        } else {
            showFeedback("Not enough coins. Need " + item.getItemPrice() + "c.", "feedback-error");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    // ── UI helpers ─────────────────────────────────────────────────────────────
    private void applyTierStyle() {
        String tierColor = tierColor(offer.getTier());
        lblTierBadge.setText(offer.getTierName().toUpperCase());
        lblTierBadge.setStyle("-fx-text-fill: " + tierColor + "; -fx-border-color: " + tierColor + ";");
        lblCoinsHeader.setText("💰 " + coins.getCoins() + " coins");
    }

    private void refreshPotionButtons() {
        int price = potions.getPotionPrice();
        btnHeal.setText("💊 HEAL POTION  —  " + price + "c");
        btnDmg.setText("⚗  DMG POTION  —  " + price + "c");
        boolean canAffordPotion = coins.getCoins() >= price;
        btnHeal.setDisable(!canAffordPotion);
        btnDmg.setDisable(!canAffordPotion);
        lblCoinsHeader.setText("💰 " + coins.getCoins() + " coins");
    }

    private void refreshStatus() {
        lblCoinsStatus.setText("💰 " + coins.getCoins() + " coins");
        lblPotionsStatus.setText(
            "💊 ×" + potions.getHealPotions() + "   ⚗ ×" + potions.getDamagePotions()
        );
    }

    private void showFeedback(String msg, String styleClass) {
        lblFeedback.setText(msg);
        lblFeedback.getStyleClass().removeAll("feedback-success", "feedback-buff", "feedback-error");
        lblFeedback.getStyleClass().add(styleClass);
    }

    /**
     * Tier colour hierarchy (Living Meadow palette):
     *  bronze = humble earth-tone, river-blue = the costly imported pigment,
     *  sun-gold = reserved for the richest wares.
     */
    private static String tierColor(ShopOffer.Tier tier) {
        return switch (tier) {
            case BRONZE -> "#a8895a";  // warm bronze
            case SILVER -> "#3d8fd1";  // river blue
            case GOLDEN -> "#e0a82e";  // sun gold
            case NONE   -> "#5f7556";  // ink-soft fallback
        };
    }
}
