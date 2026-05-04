package com.adventure.ui.fx;

import com.adventure.engine.Save;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VictoryController implements BaseController {

    @FXML private Label lblClass;
    @FXML private Label lblWins;

    private SceneManager sceneManager;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;

        String className  = session.getPlayer().getPlayerClass().getClassName();
        String classColor = classColor(session.getPlayer().getPlayerClass().name());
        lblClass.setText(className);
        lblClass.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + classColor + ";");

        int wins = session.getBattleManager().getWinCounter();
        lblWins.setText(wins + " enemies defeated · The LETHALDEMIGOD falls.");

        Save.resetSave();
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
