package com.adventure.ui.fx;

import com.adventure.engine.*;
import com.adventure.enemy.EnemyType;
import com.adventure.event.*;
import com.adventure.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Random;

public class WorldController implements BaseController {

    // ── FXML bindings ──────────────────────────────────────────────────────────
    @FXML private Label       lblClassName;
    @FXML private Label       lblCardClass;
    @FXML private Label       lblHp;
    @FXML private Label       lblAttack;
    @FXML private Label       lblSpeed;
    @FXML private Label       lblCoins;
    @FXML private Label       lblHealPots;
    @FXML private Label       lblDmgPots;
    @FXML private Label       lblProgressText;
    @FXML private ProgressBar progressHp;
    @FXML private ProgressBar progressWins;
    @FXML private ScrollPane  scrollLog;
    @FXML private VBox        logBox;

    // button panels
    @FXML private HBox  paneExploring;
    @FXML private HBox  paneBattle;
    @FXML private HBox  paneChoice;

    // battle buttons whose labels change at runtime
    @FXML private Button btnHealPotion;
    @FXML private Button btnDmgPotion;

    // ── Session references ─────────────────────────────────────────────────────
    private SceneManager  sceneManager;
    private GameSession   session;
    private Player        player;
    private BattleManager battleManager;
    private Potions       potions;
    private Coins         coins;

    // ── State ──────────────────────────────────────────────────────────────────
    private enum WorldState { EXPLORING, BATTLE, LORE_CHOICE }
    private WorldState  state = WorldState.EXPLORING;

    private final Random    rand = new Random();
    private final GameLore  gameLore = new GameLore();

    // battle
    private EnemyType currentEnemy;
    private int       currentEnemyHp;

    // lore
    private LoreEvent pendingLore;

    // ── BaseController ─────────────────────────────────────────────────────────
    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager  = sceneManager;
        this.session       = session;
        this.player        = session.getPlayer();
        this.battleManager = session.getBattleManager();
        this.potions       = session.getPotions();
        this.coins         = session.getCoins();

        refreshPlayerCard();
        refreshWinProgress();
        setState(WorldState.EXPLORING);
        log("▶ You enter the world as a " + player.getPlayerClass().getClassName() + ".", "#6e7681");
        log("Press ADVANCE to move forward.", "#6e7681");
    }

    // ── Explore actions ────────────────────────────────────────────────────────
    @FXML
    private void onAdvance() {
        int roll = rand.nextInt(100);
        if      (roll < 30)  triggerEnemy();
        else if (roll < 50)  triggerChest();
        else if (roll < 65)  triggerCoins();
        else if (roll < 80)  triggerPotions();
        else if (roll < 90)  triggerLore();
        else                 triggerShop();
    }

    @FXML
    private void onInventory() {
        log("── Inventory ──────────────────────────────", "#30363d");
        log("  Heal potions  : " + potions.getHealPotions(), "#3fb950");
        log("  Damage potions: " + potions.getDamagePotions(), "#ff7b72");
        log("  Coins         : " + coins.getCoins(), "#f0c040");
        log("───────────────────────────────────────────", "#30363d");
    }

    @FXML
    private void onSaveQuit() {
        session.save();
        log("Game saved.", "#6e7681");
        sceneManager.showMainMenu();
    }

    // ── Battle actions ─────────────────────────────────────────────────────────
    @FXML
    private void onAttack() {
        // Turn order is recalculated every time the player acts (speeds can change via potions)
        boolean playerFirst = calcPlayerFirst();

        if (!playerFirst) {
            // Enemy is faster — strikes before the player
            log("   " + currentEnemy.getEnemyName() + " moves first!", "#ff7b72");
            performEnemyAttack();
            if (player.getHP() <= 0) { handleGameOver(); return; }
        }

        // Player attacks
        int dmg = player.getAttack();
        currentEnemyHp = battleManager.playerAttacks(player, currentEnemyHp);
        log("⚔  You hit " + currentEnemy.getEnemyName() + " for " + dmg
                + " damage.  [Enemy HP: " + Math.max(0, currentEnemyHp) + "]", "#e6edf3");

        if (currentEnemyHp <= 0) { handleEnemyDefeated(); return; }

        // Player was faster — enemy counter-attacks after
        if (playerFirst) {
            performEnemyAttack();
            if (player.getHP() <= 0) handleGameOver();
        }
    }

    @FXML
    private void onUseHealPotion() {
        PotionUseResult r = battleManager.useHealPotion(player, potions);
        switch (r.getStatus()) {
            case HEALED -> {
                log("💊 Heal potion used. Restored " + r.getValue() + " HP.  [HP: " + player.getHP() + "]", "#3fb950");
                refreshPlayerCard();
                updateBattlePoitionLabels();
                // Using a potion costs the turn — enemy attacks
                performEnemyAttack();
                if (player.getHP() <= 0) { handleGameOver(); return; }
            }
            case ALREADY_FULL_HP -> {
                // Full HP: no turn wasted, player can choose another action
                log("Your HP is already full.", "#6e7681");
                return;
            }
            case NO_HEAL_POTIONS -> {
                // Distracted reaching for a missing potion — enemy attacks
                log("You reach for a heal potion... but have none! You're distracted.", "#ff7b72");
                performEnemyAttack();
                if (player.getHP() <= 0) { handleGameOver(); return; }
            }
            default -> {}
        }
        refreshPlayerCard();
        updateBattlePoitionLabels();
    }

    @FXML
    private void onUseDmgPotion() {
        PotionUseResult r = battleManager.useDamagePotion(player, potions);
        switch (r.getStatus()) {
            case BUFFED -> log("⚗  Damage potion used! Attack +" + r.getValue()
                    + ".  [ATK: " + player.getAttack() + "]", "#bc8cff");
            case NO_DAMAGE_POTIONS -> log("You reach for a damage potion... but have none! You're distracted.", "#ff7b72");
            default -> {}
        }
        // Whether potion worked or not, using it (or failing to) costs the turn
        refreshPlayerCard();
        updateBattlePoitionLabels();
        performEnemyAttack();
        if (player.getHP() <= 0) handleGameOver();
    }

    @FXML
    private void onEscape() {
        if (battleManager.tryEscape()) {
            log("You managed to escape!", "#6e7681");
            setState(WorldState.EXPLORING);
        } else {
            log("Escape failed! " + currentEnemy.getEnemyName() + " blocks your way.", "#ff7b72");
            performEnemyAttack();
            if (player.getHP() <= 0) handleGameOver();
        }
    }

    // ── Lore choice actions ────────────────────────────────────────────────────
    @FXML
    private void onChoiceYes() {
        applyLoreEffect(pendingLore.getEffectOnYes());
        setState(WorldState.EXPLORING);
        pendingLore = null;
    }

    @FXML
    private void onChoiceNo() {
        applyLoreEffect(pendingLore.getEffectOnNo());
        setState(WorldState.EXPLORING);
        pendingLore = null;
    }

    // ── Event triggers ─────────────────────────────────────────────────────────
    private void triggerEnemy() {
        EnemyEncounter encounter = battleManager.selectEnemy();
        currentEnemy   = encounter.getEnemy();
        currentEnemyHp = currentEnemy.getEnemyHP();

        String tierTag = switch (encounter.getTier()) {
            case MINI_BOSS  -> "  ★ MINI-BOSS";
            case FINAL_BOSS -> "  ☠ FINAL BOSS";
            default         -> "";
        };
        String tierColor = switch (encounter.getTier()) {
            case MINI_BOSS  -> "#bc8cff";
            case FINAL_BOSS -> "#cf222e";
            default         -> "#ff7b72";
        };

        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#30363d");
        log("⚠  A " + currentEnemy.getEnemyName() + " appears!" + tierTag, tierColor);
        log("   HP: " + currentEnemyHp + "  |  ATK: " + currentEnemy.getEnemyAttack()
                + "  |  SPD: " + currentEnemy.getEnemyAttackSpeed(), "#6e7681");
        log("   Choose your action.", "#6e7681");

        updateBattlePoitionLabels();
        setState(WorldState.BATTLE);
    }

    private void triggerChest() {
        ChestResult chest = session.getChest().foundChest();
        if (chest.getTier() == ChestResult.Tier.NONE) {
            log("You search the area... nothing here.", "#6e7681");
            return;
        }
        String tierName = chest.getTier().name().charAt(0)
                + chest.getTier().name().substring(1).toLowerCase();
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#30363d");
        log("📦 You found a " + tierName + " Chest!", "#f0c040");
        if (chest.getHealPotions() > 0) {
            potions.addHealPotion(chest.getHealPotions());
            log("   +" + chest.getHealPotions() + " heal potion(s)", "#3fb950");
        }
        if (chest.getDamagePotions() > 0) {
            potions.addDamagePotion(chest.getDamagePotions());
            log("   +" + chest.getDamagePotions() + " damage potion(s)", "#bc8cff");
        }
        if (chest.getCoins() > 0) {
            coins.addCoins(chest.getCoins());
            log("   +" + chest.getCoins() + " coins", "#f0c040");
        }
        refreshPlayerCard();
    }

    private void triggerCoins() {
        CoinEvent event = coins.foundCoins();
        if (event.getTier() == CoinEvent.Tier.NONE) {
            log("You find some loose dirt. Nothing useful.", "#6e7681");
            return;
        }
        log("🪙 You found " + event.getAmount() + " coins!", "#f0c040");
        refreshPlayerCard();
    }

    private void triggerPotions() {
        PotionEvent event = potions.generatePotions();
        switch (event.getType()) {
            case HEAL   -> { log("💊 You found a heal potion!", "#3fb950"); refreshPlayerCard(); }
            case DAMAGE -> { log("⚗  You found a damage potion!", "#bc8cff"); refreshPlayerCard(); }
            case NONE   -> log("You search your surroundings... nothing here.", "#6e7681");
        }
    }

    private void triggerLore() {
        LoreEvent lore = gameLore.generateLore();
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#30363d");

        String speaker = lore.getNpcName() != null
                ? "📜 " + lore.getNpcName() + " says:"
                : "📜";
        log(speaker, "#58a6ff");
        log("   \"" + lore.getMessage() + "\"", "#e6edf3");

        if (lore.requiresChoice()) {
            pendingLore = lore;
            setState(WorldState.LORE_CHOICE);
        } else {
            applyLoreEffect(lore.getEffectOnYes());
        }
    }

    private void triggerShop() {
        ShopOffer.Tier tier  = tierForCoins(coins.getCoins());
        ShopOffer      offer = session.getVillager().refreshOffer(tier);
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#30363d");
        log("🛒 A " + offer.getTierName() + " Merchant appears! Entering shop...", "#f0c040");
        sceneManager.showShop(offer);
    }

    private ShopOffer.Tier tierForCoins(int playerCoins) {
        if (playerCoins <= 60)  return ShopOffer.Tier.BRONZE;
        if (playerCoins <= 200) return ShopOffer.Tier.SILVER;
        return ShopOffer.Tier.GOLDEN;
    }

    // ── Battle turn helpers ────────────────────────────────────────────────────

    /** Recalculated every time the player acts — speeds can change mid-combat via damage potions. */
    private boolean calcPlayerFirst() {
        double pSpeed = player.getAttackSpeed();
        double eSpeed = currentEnemy.getEnemyAttackSpeed();
        if (pSpeed > eSpeed) return true;
        if (pSpeed < eSpeed) return false;
        boolean first = rand.nextBoolean();
        log("   Equal speeds — decided randomly: "
                + (first ? "you go first." : "enemy goes first."), "#f0c040");
        return first;
    }

    private void performEnemyAttack() {
        int before = player.getHP();
        battleManager.enemyAttacks(player, currentEnemy);
        int taken = before - player.getHP();
        log("   " + currentEnemy.getEnemyName() + " hits you for " + taken
                + " damage.  [Your HP: " + Math.max(0, player.getHP()) + "]", "#ff7b72");
        refreshPlayerCard();
    }

    // ── Battle outcome helpers ─────────────────────────────────────────────────
    private void handleEnemyDefeated() {
        battleManager.onEnemyDefeated(coins);
        refreshWinProgress();
        log("✓  " + currentEnemy.getEnemyName() + " defeated!  [Wins: "
                + battleManager.getWinCounter() + "]", "#3fb950");
        log("   +5 coins rewarded.", "#f0c040");
        refreshPlayerCard();

        if (battleManager.getWinCounter() >= 20) {
            log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#f0c040");
            log("🏆 VICTORY! You have defeated the Final Boss!", "#f0c040");
            log("   Navigating to Victory screen...", "#6e7681");
            setState(WorldState.EXPLORING);
            sceneManager.showVictory();
            return;
        }
        setState(WorldState.EXPLORING);
    }

    private void handleGameOver() {
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", "#cf222e");
        log("☠  You have been defeated...", "#cf222e");
        setState(WorldState.EXPLORING);
        sceneManager.showGameOver();
    }

    // ── Lore effect application ────────────────────────────────────────────────
    private void applyLoreEffect(LoreEffect effect) {
        switch (effect.getType()) {
            case HEAL_FULL  -> {
                player.restoreHp();
                log("   You feel fully restored.", "#3fb950");
                refreshPlayerCard();
            }
            case COINS      -> {
                coins.addCoins(effect.getValue());
                log("   +" + effect.getValue() + " coins.", "#f0c040");
                refreshPlayerCard();
            }
            case POTION_HEAL -> {
                potions.addHealPotion(1);
                log("   +1 heal potion.", "#3fb950");
                refreshPlayerCard();
            }
            case POTION_DMG  -> {
                potions.addDamagePotion(1);
                log("   +1 damage potion.", "#bc8cff");
                refreshPlayerCard();
            }
            case DAMAGE      -> {
                player.setHP(Math.max(1, player.getHP() - effect.getValue()));
                log("   You take " + effect.getValue() + " damage.  [HP: " + player.getHP() + "]", "#ff7b72");
                refreshPlayerCard();
            }
            case NONE -> {}
        }
    }

    // ── UI helpers ─────────────────────────────────────────────────────────────
    private void setState(WorldState newState) {
        state = newState;
        paneExploring.setVisible(state == WorldState.EXPLORING);
        paneExploring.setManaged(state == WorldState.EXPLORING);
        paneBattle.setVisible(state == WorldState.BATTLE);
        paneBattle.setManaged(state == WorldState.BATTLE);
        paneChoice.setVisible(state == WorldState.LORE_CHOICE);
        paneChoice.setManaged(state == WorldState.LORE_CHOICE);
    }

    private void refreshPlayerCard() {
        String className  = player.getPlayerClass().getClassName();
        String classColor = classColor(player.getPlayerClass());

        lblClassName.setText(className);
        lblClassName.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: " + classColor + ";");

        lblCardClass.setText(className);
        lblCardClass.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-font-family: Georgia;"
                + " -fx-text-fill: " + classColor + ";");

        lblHp.setText(player.getHP() + " / " + player.getMaxHp());
        lblAttack.setText(String.valueOf(player.getAttack()));
        lblSpeed.setText(String.valueOf(player.getAttackSpeed()));
        lblCoins.setText(String.valueOf(coins.getCoins()));
        lblHealPots.setText("💊 ×" + potions.getHealPotions());
        lblDmgPots.setText("⚗ ×" + potions.getDamagePotions());

        double hpFraction = (double) player.getHP() / player.getMaxHp();
        progressHp.setProgress(Math.max(0.0, hpFraction));
    }

    private void refreshWinProgress() {
        int wins    = battleManager.getWinCounter();
        int goal    = 20;
        double frac = Math.min(1.0, (double) wins / goal);
        progressWins.setProgress(frac);

        String phase = wins < 10  ? "Normal enemies" :
                       wins < 20  ? "Mini-bosses"    : "Final Boss";
        lblProgressText.setText("Wins: " + wins + " / " + goal + "  · " + phase);
    }

    private void updateBattlePoitionLabels() {
        btnHealPotion.setText("💊 HEAL  ×" + potions.getHealPotions());
        btnDmgPotion.setText("⚗ BUFF  ×" + potions.getDamagePotions());
    }

    private void log(String text, String hexColor) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setStyle("-fx-font-size: 13; -fx-text-fill: " + hexColor + ";");
        lbl.setAlignment(Pos.TOP_LEFT);
        logBox.getChildren().add(lbl);
        scrollLog.layout();
        scrollLog.setVvalue(1.0);
    }

    private String classColor(Classes cls) {
        return switch (cls) {
            case WARRIOR -> "#f0c040";
            case MAGE    -> "#58a6ff";
            case ROGUE   -> "#bc8cff";
        };
    }
}
