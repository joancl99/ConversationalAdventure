package com.adventure.ui.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WorldController implements BaseController {

    @FXML private Label lblClass;
    @FXML private Label lblStats;

    private SceneManager sceneManager;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;
        lblClass.setText("Playing as: " + session.getPlayer().getPlayerClass().getClassName());
        lblStats.setText("HP: " + session.getPlayer().getHP()
                + "  |  ATK: " + session.getPlayer().getAttack()
                + "  |  SPD: " + session.getPlayer().getAttackSpeed());
    }

    @FXML
    private void onBackToMenu() {
        sceneManager.showMainMenu();
    }
}
