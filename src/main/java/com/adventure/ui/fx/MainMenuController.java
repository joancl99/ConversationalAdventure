package com.adventure.ui.fx;

import com.adventure.engine.Save;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController implements BaseController {

    @FXML private Button btnContinue;

    private SceneManager sceneManager;

    @FXML
    private void initialize() {
        btnContinue.setDisable(!Save.saveExists());
    }

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void onNewGame() {
        sceneManager.showClassSelect();
    }

    @FXML
    private void onContinue() {
        sceneManager.getSession().loadGame();
        sceneManager.showWorld();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }
}
