package com.adventure.ui.fx;

import com.adventure.event.EnemyEncounter;
import com.adventure.event.ShopOffer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    private final Stage stage;
    private final GameSession session;
    private Scene worldScene;

    public SceneManager(Stage stage, GameSession session) {
        this.stage = stage;
        this.session = session;
    }

    public GameSession getSession() { return session; }

    public void showMainMenu()    { loadScene("main-menu.fxml"); }
    public void showClassSelect() { loadScene("class-select.fxml"); }
    public void showGameOver()    { loadScene("game-over.fxml"); }
    public void showVictory()     { loadScene("victory.fxml"); }
    public void showHistory()     { loadScene("history.fxml"); }

    public void showWorld() {
        try {
            FXMLLoader loader = fxmlLoader("world.fxml");
            Parent root = loader.load();
            BaseController controller = loader.getController();
            if (controller != null) controller.init(this, session);
            worldScene = new Scene(root, 800, 600);
            stage.setScene(worldScene);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: world.fxml", e);
        }
    }

    public void returnToWorld() {
        if (worldScene != null) stage.setScene(worldScene);
    }

    public void showShop(ShopOffer offer) {
        try {
            FXMLLoader loader = fxmlLoader("shop.fxml");
            Parent root = loader.load();
            ShopController controller = loader.getController();
            controller.initShop(this, session, offer);
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: shop.fxml", e);
        }
    }

    public void showBattle(EnemyEncounter encounter) {
        try {
            FXMLLoader loader = fxmlLoader("battle.fxml");
            Parent root = loader.load();
            BattleController controller = loader.getController();
            controller.initBattle(this, session, encounter);
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: battle.fxml", e);
        }
    }

    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = fxmlLoader(fxmlFile);
            Parent root = loader.load();
            BaseController controller = loader.getController();
            if (controller != null) controller.init(this, session);
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: " + fxmlFile, e);
        }
    }

    private FXMLLoader fxmlLoader(String fxmlFile) {
        return new FXMLLoader(
            SceneManager.class.getResource("/com/adventure/ui/fx/" + fxmlFile)
        );
    }
}
