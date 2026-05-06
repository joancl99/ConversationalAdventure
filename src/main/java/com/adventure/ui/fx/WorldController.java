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

    // Aliases for LogPalette so call sites stay short.
    private static final String LOG_NORMAL = LogPalette.NORMAL;
    private static final String LOG_SOFT   = LogPalette.SOFT;
    private static final String LOG_RULE   = LogPalette.RULE;
    private static final String LOG_HEAL   = LogPalette.HEAL;
    private static final String LOG_DAMAGE = LogPalette.DAMAGE;
    private static final String LOG_FATAL  = LogPalette.FATAL;
    private static final String LOG_GOLD   = LogPalette.GOLD;
    private static final String LOG_BUFF   = LogPalette.BUFF;
    private static final String LOG_LORE   = LogPalette.LORE;

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
        log("▶ You enter the world as a " + player.getPlayerClass().getClassName() + ".", LOG_SOFT);
        log("Press ADVANCE to move forward.", LOG_SOFT);
    }

    // ── Explore actions ────────────────────────────────────────────────────────
    @FXML
    private void onAdvance() {
        if (!session.getSessionLog().isEmpty()) {
            session.addSeparatorToLog();
        }
        logBox.getChildren().clear();
        int roll = rand.nextInt(100);
        if      (roll < 30)  triggerEnemy();
        else if (roll < 50)  triggerChest();
        else if (roll < 65)  triggerCoins();
        else if (roll < 80)  triggerPotions();
        else if (roll < 90)  triggerLore();
        else                 triggerShop();
    }

    @FXML
    private void onShowHistory() {
        sceneManager.showHistory();
    }

    @FXML
    private void onInventory() {
        log("── Inventory ──────────────────────────────", LOG_RULE);
        log("  Heal potions  : " + potions.getHealPotions(), LOG_HEAL);
        log("  Damage potions: " + potions.getDamagePotions(), LOG_BUFF);
        log("  Coins         : " + coins.getCoins(), LOG_GOLD);
        log("───────────────────────────────────────────", LOG_RULE);
    }

    @FXML
    private void onSaveQuit() {
        session.save();
        log("Game saved.", LOG_SOFT);
        sceneManager.showMainMenu();
    }

    // ── Battle actions ─────────────────────────────────────────────────────────
    @FXML
    private void onAttack() {
        // Turn order is recalculated every time the player acts (speeds can change via potions)
        boolean playerFirst = calcPlayerFirst();

        if (!playerFirst) {
            // Enemy is faster — strikes before the player
            log("   " + currentEnemy.getEnemyName() + " moves first!", LOG_DAMAGE);
            performEnemyAttack();
            if (player.getHP() <= 0) { handleGameOver(); return; }
        }

        // Player attacks
        int dmg = player.getAttack();
        currentEnemyHp = battleManager.playerAttacks(player, currentEnemyHp);
        log("⚔  You hit " + currentEnemy.getEnemyName() + " for " + dmg
                + " damage.  [Enemy HP: " + Math.max(0, currentEnemyHp) + "]", LOG_NORMAL);

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
                log("💊 Heal potion used. Restored " + r.getValue() + " HP.  [HP: " + player.getHP() + "]", LOG_HEAL);
                refreshPlayerCard();
                updateBattlePoitionLabels();
                // Using a potion costs the turn — enemy attacks
                performEnemyAttack();
                if (player.getHP() <= 0) { handleGameOver(); return; }
            }
            case ALREADY_FULL_HP -> {
                // Full HP: no turn wasted, player can choose another action
                log("Your HP is already full.", LOG_SOFT);
                return;
            }
            case NO_HEAL_POTIONS -> {
                // Distracted reaching for a missing potion — enemy attacks
                log("You reach for a heal potion... but have none! You're distracted.", LOG_DAMAGE);
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
                    + ".  [ATK: " + player.getAttack() + "]", LOG_BUFF);
            case NO_DAMAGE_POTIONS -> log("You reach for a damage potion... but have none! You're distracted.", LOG_DAMAGE);
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
            log("You managed to escape!", LOG_SOFT);
            setState(WorldState.EXPLORING);
        } else {
            log("Escape failed! " + currentEnemy.getEnemyName() + " blocks your way.", LOG_DAMAGE);
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
            case MINI_BOSS  -> LOG_BUFF;
            case FINAL_BOSS -> LOG_FATAL;
            default         -> LOG_DAMAGE;
        };

        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_RULE);
        log("⚠  A " + currentEnemy.getEnemyName() + " appears!" + tierTag, tierColor);
        log("   HP: " + currentEnemyHp + "  |  ATK: " + currentEnemy.getEnemyAttack()
                + "  |  SPD: " + currentEnemy.getEnemyAttackSpeed(), LOG_SOFT);
        log("   Choose your action.", LOG_SOFT);

        updateBattlePoitionLabels();
        setState(WorldState.BATTLE);
    }

    private void triggerChest() {
        ChestResult chest = session.getChest().foundChest();
        if (chest.getTier() == ChestResult.Tier.NONE) {
            log("You search the area... nothing here.", LOG_SOFT);
            return;
        }
        String tierName = chest.getTier().name().charAt(0)
                + chest.getTier().name().substring(1).toLowerCase();
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_RULE);
        log("📦 You found a " + tierName + " Chest!", LOG_GOLD);
        if (chest.getHealPotions() > 0) {
            potions.addHealPotion(chest.getHealPotions());
            log("   +" + chest.getHealPotions() + " heal potion(s)", LOG_HEAL);
        }
        if (chest.getDamagePotions() > 0) {
            potions.addDamagePotion(chest.getDamagePotions());
            log("   +" + chest.getDamagePotions() + " damage potion(s)", LOG_BUFF);
        }
        if (chest.getCoins() > 0) {
            coins.addCoins(chest.getCoins());
            log("   +" + chest.getCoins() + " coins", LOG_GOLD);
        }
        refreshPlayerCard();
    }

    private void triggerCoins() {
        CoinEvent event = coins.foundCoins();
        if (event.getTier() == CoinEvent.Tier.NONE) {
            log("You find some loose dirt. Nothing useful.", LOG_SOFT);
            return;
        }
        log("🪙 You found " + event.getAmount() + " coins!", LOG_GOLD);
        refreshPlayerCard();
    }

    private void triggerPotions() {
        PotionEvent event = potions.generatePotions();
        switch (event.getType()) {
            case HEAL   -> { log("💊 You found a heal potion!", LOG_HEAL); refreshPlayerCard(); }
            case DAMAGE -> { log("⚗  You found a damage potion!", LOG_BUFF); refreshPlayerCard(); }
            case NONE   -> log("You search your surroundings... nothing here.", LOG_SOFT);
        }
    }

    private void triggerLore() {
        LoreEvent lore = gameLore.generateLore();
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_RULE);

        String speaker = lore.getNpcName() != null
                ? "📜 " + lore.getNpcName() + " says:"
                : "📜";
        log(speaker, LOG_LORE);
        log("   \"" + lore.getMessage() + "\"", LOG_NORMAL);

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
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_RULE);
        log("🛒 A " + offer.getTierName() + " Merchant appears! Entering shop...", LOG_GOLD);
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
                + (first ? "you go first." : "enemy goes first."), LOG_GOLD);
        return first;
    }

    private void performEnemyAttack() {
        int before = player.getHP();
        battleManager.enemyAttacks(player, currentEnemy);
        int taken = before - player.getHP();
        log("   " + currentEnemy.getEnemyName() + " hits you for " + taken
                + " damage.  [Your HP: " + Math.max(0, player.getHP()) + "]", LOG_DAMAGE);
        refreshPlayerCard();
    }

    // ── Battle outcome helpers ─────────────────────────────────────────────────
    private void handleEnemyDefeated() {
        battleManager.onEnemyDefeated(coins);
        refreshWinProgress();
        log("✓  " + currentEnemy.getEnemyName() + " defeated!  [Wins: "
                + battleManager.getWinCounter() + "]", LOG_HEAL);
        log("   +5 coins rewarded.", LOG_GOLD);
        refreshPlayerCard();

        if (battleManager.getWinCounter() >= 20) {
            log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_GOLD);
            log("🏆 VICTORY! You have defeated the Final Boss!", LOG_GOLD);
            log("   Navigating to Victory screen...", LOG_SOFT);
            setState(WorldState.EXPLORING);
            sceneManager.showVictory();
            return;
        }
        setState(WorldState.EXPLORING);
    }

    private void handleGameOver() {
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", LOG_FATAL);
        log("☠  You have been defeated...", LOG_FATAL);
        setState(WorldState.EXPLORING);
        sceneManager.showGameOver();
    }

    // ── Lore effect application ────────────────────────────────────────────────
    private void applyLoreEffect(LoreEffect effect) {
        switch (effect.getType()) {
            case HEAL_FULL  -> {
                player.restoreHp();
                log("   You feel fully restored.", LOG_HEAL);
                refreshPlayerCard();
            }
            case COINS      -> {
                coins.addCoins(effect.getValue());
                log("   +" + effect.getValue() + " coins.", LOG_GOLD);
                refreshPlayerCard();
            }
            case POTION_HEAL -> {
                potions.addHealPotion(1);
                log("   +1 heal potion.", LOG_HEAL);
                refreshPlayerCard();
            }
            case POTION_DMG  -> {
                potions.addDamagePotion(1);
                log("   +1 damage potion.", LOG_BUFF);
                refreshPlayerCard();
            }
            case DAMAGE      -> {
                player.setHP(Math.max(1, player.getHP() - effect.getValue()));
                log("   You take " + effect.getValue() + " damage.  [HP: " + player.getHP() + "]", LOG_DAMAGE);
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
        Classes cls = player.getPlayerClass();
        String className   = cls.getClassName();
        String classStyle  = "text-" + cls.name().toLowerCase();

        applyClassStyle(lblClassName, classStyle);
        applyClassStyle(lblCardClass, classStyle);
        lblClassName.setText(className);
        lblCardClass.setText(className);

        lblHp.setText(player.getHP() + " / " + player.getMaxHp());
        lblAttack.setText(String.valueOf(player.getAttack()));
        lblSpeed.setText(String.valueOf(player.getAttackSpeed()));
        lblCoins.setText(String.valueOf(coins.getCoins()));
        lblHealPots.setText("💊 ×" + potions.getHealPotions());
        lblDmgPots.setText("⚗ ×" + potions.getDamagePotions());

        double hpFraction = (double) player.getHP() / player.getMaxHp();
        progressHp.setProgress(Math.max(0.0, hpFraction));
    }

    private static void applyClassStyle(Label lbl, String classStyle) {
        lbl.getStyleClass().removeAll("text-warrior", "text-mage", "text-rogue");
        lbl.getStyleClass().add(classStyle);
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
        session.addToLog(text, hexColor);
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.getStyleClass().add("log-entry");
        lbl.setStyle("-fx-text-fill: " + hexColor + ";");
        lbl.setAlignment(Pos.TOP_LEFT);
        logBox.getChildren().add(lbl);
        scrollLog.layout();
        scrollLog.setVvalue(1.0);
    }
}
