# ConversationalAdventure — Project Guide (AGENTS.md)

The committed source of truth for this project. (A local `CLAUDE.md` mirrors it for the Claude Code agent but is gitignored.)

## What this project is
A Java **text adventure** game, originally console-based, now presented as a polished **JavaFX app**. It stays a text/console experience — turn-based combat, RPG progression, save/load, shop, random events — but wrapped in a UI with real personality so it's a pleasure to play. Maven + JavaFX 17.

The console version lives on the `main` branch and is the **gameplay source of truth**. The `GUI-migration` branch re-presents it visually. The two branches are independent and never merge.

## How to run
- **JavaFX (`GUI-migration`):** `mvn javafx:run`
- **Console (`main`):** `javac src/*.java -d out` then `java -cp out Main`

## Core directives (do not break)
1. **Game-logic parity with `main`.** The GUI must keep the *exact* combat/game logic of the console version (Joan perfected it). Check any combat change against `git show main:src/BattleManager.java`. Verified identical: damage formulas, turn order, potion values (heal +100 capped at maxHp, dmg +50), escape 65%, rewards (+1 win / +5 coins), progression thresholds, and all enemy / miniboss / final-boss stats. **One accepted divergence:** a *failed* escape always lets the enemy attack in the GUI (console only attacks if the player would act first) — kept intentionally.
2. **It stays a text/console game.** Personality comes from palette + motion + sound + narration, **not** from turning it into a graphical RPG. Imagery is capped at **Level 1**: subtle ambient backdrops per zone behind the text (visual-novel style), never sprite-based mechanics.
3. **Purposeful changes only** — no cosmetic cleanup without a reason.

## Architecture

### JavaFX layer (active)
```
Main (launcher) → MainApp → SceneManager → MainMenuController
                                         → ClassSelectController
                                         → WorldController  (combat inline)
                                         → HistoryController (session log viewer)
                                         → ShopController
                                         → GameOverController
                                         → VictoryController
```
- `GameSession` — all runtime state (Player, BattleManager, Potions, Coins, Chest, Villager, Inventory, sessionLog).
- `SceneManager` — loads FXML scenes, calls `controller.init(sceneManager, session)`; stores `worldScene` to return from History without re-init.
- `BaseController` — interface `init(SceneManager, GameSession)`.
- `LogEntry` — record `text`, `color`, `isSeparator`.

### Engine / model (pure logic, no I/O)
| Class | Role |
|---|---|
| `Player` | Runtime player state (hp, maxHp, attack, attackSpeed) |
| `BattleManager` | Combat calculations, enemy selection, win counter |
| `GameLore` | Random NPC encounter texts → `LoreEvent` |
| `Chest` / `Villager` | Chest drops → `ChestResult`; shop → `ShopOffer` / `PurchaseResult` |
| `Save` | Gson JSON I/O to `./saveFile/save.json` |
| `Classes` (enum) | Immutable class defs — WARRIOR / MAGE / ROGUE |
| `Enemies` / `MiniBosses` / `FinalBoss` (enums) | Enemy tiers (stats identical to `main`) |
| Result objects | `EnemyEncounter`, `ChestResult`, `LoreEvent`, `LoreEffect`, `CoinEvent`, `PotionEvent`, `PotionUseResult`, `ShopOffer`, `PurchaseResult` |

### Progression thresholds
- `winCounter < 10` → normal Enemies · `10–19` → MiniBosses · `>= 20` → FinalBoss (LETHALDEMIGOD, 550 HP / 150 ATK) → Victory.

## Screens
| Screen | Controller | FXML | Notes |
|---|---|---|---|
| Main menu | `MainMenuController` | `main-menu.fxml` | New Game / Continue / Exit |
| Class select | `ClassSelectController` | `class-select.fxml` | 3 cards: WARRIOR / MAGE / ROGUE |
| World | `WorldController` | `world.fxml` | 6 event types, inline combat, 3 button states |
| History | `HistoryController` | `history.fxml` | Full session log, auto-scrolls to bottom |
| Shop | `ShopController` | `shop.fxml` | Buy potions + items. Tier by coins. LEAVE → World |
| Game Over | `GameOverController` | `game-over.fxml` | Shows wins + class, resets save |
| Victory | `VictoryController` | `victory.fxml` | Shows class + wins, resets save |

### WorldController
- **6 event types** (30% enemy / 20% chest / 15% coins / 15% potions / 10% lore / 10% shop).
- **3 button states:** `EXPLORING` (ADVANCE · INVENTORY · history · SAVE & QUIT), `BATTLE` (ATTACK · HEAL · BUFF · ESCAPE), `LORE_CHOICE` (YES · NO).
- **Inline combat:** `calcPlayerFirst()` recalculated every action; potions cost the turn; failed escape always triggers an enemy attack (the one accepted divergence). Combat math is identical to `main`.
- **Text reveal:** narration is typed out character by character (typewriter, `REVEAL_MS_PER_CHAR = 12`) via a queued reveal so lines appear in order; decorative `━━━` rules appear instantly; a second action click (`flushReveal()`) snaps the line still typing; `clearLog()` on ADVANCE cancels pending reveals.
- Every `log()` call also writes to `GameSession.sessionLog` (shown in full on the History screen).

### Shop
- Uses `initShop(SceneManager, GameSession, ShopOffer)` (not `BaseController`). Tier by coins: ≤60 BRONZE, ≤200 SILVER, >200 GOLDEN. Item cards built in `buildItemCard()`; `refreshOffer()` after each purchase. All purchases logged to `sessionLog`.

## Visual direction — "Living Meadow"
Riverland is a **living, colourful fantasy world** — river, grass, meadows, rocks, dragons and beasts; you're a no-name hero who advances to earn your place. Bright and inviting, in the spirit of early-gen Pokémon (LeafGreen) — **not** dark or apocalyptic. (Supersedes the earlier muted "Illuminated Manuscript" theme, which felt like a dusty old book.)

- **Theme:** `src/main/resources/com/adventure/ui/fx/styles/theme.css` — lookup-colour tokens drive every screen from one place.
- **Palette:** outdoor-daylight background (sky-blue → meadow-green), bright near-white panels, meadow green (hero accent), river blue, sun gold (coins), dragonfire red (attack), arcane purple (buff). Rounded buttons/panels with soft shadows.
- **Log colours:** `LogPalette.java` (read by `WorldController` + `ShopController`, tuned for readability on bright surfaces).
- **Fonts (bundled, loaded in `MainApp`):** Cinzel (display), EB Garamond (body), JetBrains Mono (stats).

### Revival roadmap
- **A. Direction** ✅ — palette + mood locked.
- **B. New skin** ✅ — Living Meadow palette, rounded UI, divider fix, log colours.
- **C. Ambient backdrops (Level 1)** — subtle per-zone background images behind the text; needs AI-generated art. ← **next**
- **D. Motion** — typewriter reveal ✅; pending: transitions, HP-bar / combat feedback, ambient motion.
- **E. Sound** — ambient music + SFX.
- **F. Journey framing** — sense of travel through Riverland (map / routes / locations).

## Save file format (`saveFile/save.json`)
Gson 2.11.0, pretty-printed JSON via `Save.SaveData`:
```json
{
  "playerClass": "MAGE", "hp": 80, "maxHp": 100, "attack": 30, "attackSpeed": 1.5,
  "winCounter": 7, "healPotions": 2, "damagePotions": 1, "coins": 45,
  "itemsBought": ["BRUTAL_THIEF_KNIFE", "DEAD_MANS_ARMOR"]
}
```

## Refactor history (summary)
The engine was hardened in three rounds before the GUI work: immutable `Classes` enum with a dedicated `Player` for runtime state; encapsulated fields (`winCounter`, potions, coins); `GameResult` enum replacing `System.exit()` in business logic; a single injected `Scanner` for the console layer. The GUI migration then moved all I/O out of the engine so model/engine classes are pure logic.

## User context
Joan built this as a Java learning project and is bringing it to professional quality step by step. Current goal: a JavaFX version that is **visually alive and full of personality** while staying a text/console game with the console's exact combat logic. Changes should be purposeful. Joan knows Java OOP and works in Spanish.
