package com.adventure.ui.fx;

import com.adventure.engine.Save;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController implements BaseController {

    @FXML private Label lblWins;
    @FXML private Label lblClass;

    private SceneManager sceneManager;
    private GameSession  session;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;
        this.session      = session;

        int wins = session.getBattleManager().getWinCounter();
        lblWins.setText("You fell after " + wins + (wins == 1 ? " victory." : " victories."));

        String className  = session.getPlayer().getPlayerClass().getClassName();
        String classColor = classColor(session.getPlayer().getPlayerClass().name());
        lblClass.setText("Playing as: " + className);
        lblClass.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + classColor + ";");

        Save.resetSave();
    }

    @FXML
    private void onNewGame() {
        sceneManager.showClassSelect();
    }

    @FXML
    private void onBackToMenu() {
        sceneManager.showMainMenu();
    }

    private String classColor(String name) {
        return switch (name) {
            case "WARRIOR" -> "#f0c040";
            case "MAGE"    -> "#58a6ff";
            default        -> "#bc8cff";
        };
    }
}
