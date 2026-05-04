package com.adventure.ui.fx;

import com.adventure.engine.Villager;
import com.adventure.event.PurchaseResult;
import com.adventure.event.ShopOffer;
import com.adventure.item.ItemType;
import com.adventure.model.*;
import javafx.fxml.FXML;
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
        PurchaseResult result = villager.buyPotion("heal", potions, coins);
        if (result.isSuccess()) {
            showFeedback("💊 Heal potion purchased!", "#3fb950");
        } else {
            showFeedback("Not enough coins. Need " + potions.getPotionPrice() + "c.", "#cf222e");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    @FXML
    private void onBuyDmg() {
        PurchaseResult result = villager.buyPotion("damage", potions, coins);
        if (result.isSuccess()) {
            showFeedback("⚗  Damage potion purchased!", "#bc8cff");
        } else {
            showFeedback("Not enough coins. Need " + potions.getPotionPrice() + "c.", "#cf222e");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    @FXML
    private void onLeave() {
        sceneManager.showWorld();
    }

    // ── Item cards ─────────────────────────────────────────────────────────────
    private void refreshItemsSection() {
        itemsSection.getChildren().clear();

        offer = villager.refreshOffer(offer.getTier());
        java.util.List<ItemType> items = offer.getAvailableItems();

        if (items.isEmpty()) {
            Label lbl = new Label("All items from this merchant have been purchased.");
            lbl.setStyle("-fx-font-size: 13; -fx-text-fill: #6e7681; -fx-font-style: italic;");
            itemsSection.getChildren().add(lbl);
            return;
        }

        Label header = new Label("ITEMS");
        header.setStyle("-fx-font-size: 11; -fx-text-fill: #6e7681; -fx-padding: 0 0 10 0;");
        itemsSection.getChildren().add(header);

        HBox cards = new HBox(16);
        cards.setAlignment(Pos.TOP_LEFT);
        for (ItemType item : items) {
            cards.getChildren().add(buildItemCard(item));
        }
        itemsSection.getChildren().add(cards);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #30363d;");
        VBox.setMargin(sep, new javafx.geometry.Insets(16, 0, 0, 0));
        itemsSection.getChildren().add(sep);
    }

    private VBox buildItemCard(ItemType item) {
        VBox card = new VBox(10);
        card.setPrefWidth(200);
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
            "-fx-background-color: #161b22;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: " + tierAccentColor() + ";" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;" +
            "-fx-padding: 16;"
        );

        Label name = new Label(item.getItemName());
        name.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #e6edf3;");
        name.setWrapText(true);
        name.setMaxWidth(168);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #30363d;");

        VBox stats = new VBox(5);
        if (item.getItemHP() != 0)
            stats.getChildren().add(statRow("HP",    "+" + item.getItemHP()));
        if (item.getItemAttack() != 0)
            stats.getChildren().add(statRow("ATK",   "+" + item.getItemAttack()));
        if (item.getItemAttackSpeed() != 0)
            stats.getChildren().add(statRow("SPD",   "+" + item.getItemAttackSpeed()));

        boolean canAfford = coins.getCoins() >= item.getItemPrice();
        Button buyBtn = new Button("BUY  —  " + item.getItemPrice() + " coins");
        buyBtn.setPrefWidth(168);
        buyBtn.setPrefHeight(36);
        buyBtn.setDisable(!canAfford);
        buyBtn.setStyle(
            "-fx-font-size: 12; -fx-font-weight: bold;" +
            "-fx-background-color: " + (canAfford ? "#1f6feb" : "#30363d") + ";" +
            "-fx-text-fill: " + (canAfford ? "#ffffff" : "#6e7681") + ";" +
            "-fx-background-radius: 6; -fx-cursor: " + (canAfford ? "hand" : "default") + ";"
        );
        buyBtn.setOnAction(e -> onBuyItem(item));

        card.getChildren().addAll(name, sep, stats, buyBtn);
        return card;
    }

    private HBox statRow(String label, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 12; -fx-text-fill: #6e7681;");
        lbl.setPrefWidth(40);
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-fill: #e6edf3;");
        row.getChildren().addAll(lbl, val);
        return row;
    }

    private void onBuyItem(ItemType item) {
        PurchaseResult result = villager.buyItem(player, item, coins);
        if (result.isSuccess()) {
            showFeedback("✓ " + item.getItemName() + " purchased! Stats upgraded.", "#3fb950");
            refreshItemsSection();
        } else {
            showFeedback("Not enough coins. Need " + item.getItemPrice() + "c.", "#cf222e");
        }
        refreshPotionButtons();
        refreshStatus();
    }

    // ── UI helpers ─────────────────────────────────────────────────────────────
    private void applyTierStyle() {
        String tierName  = offer.getTierName();
        String bg        = tierBgColor();
        String textColor = offer.getTier() == ShopOffer.Tier.BRONZE ? "#0d1117" : "#0d1117";

        lblTierBadge.setText(tierName.toUpperCase());
        lblTierBadge.setStyle(
            "-fx-font-size: 11; -fx-font-weight: bold;" +
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-padding: 3 8; -fx-background-radius: 4;"
        );
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

    private void showFeedback(String msg, String hexColor) {
        lblFeedback.setText(msg);
        lblFeedback.setStyle("-fx-font-size: 13; -fx-text-fill: " + hexColor + "; -fx-padding: 16 0 0 0;");
    }

    private String tierBgColor() {
        return switch (offer.getTier()) {
            case BRONZE -> "#f0c040";
            case SILVER -> "#58a6ff";
            case GOLDEN -> "#bc8cff";
            default     -> "#30363d";
        };
    }

    private String tierAccentColor() {
        return switch (offer.getTier()) {
            case BRONZE -> "#f0c040";
            case SILVER -> "#58a6ff";
            case GOLDEN -> "#bc8cff";
            default     -> "#30363d";
        };
    }
}
