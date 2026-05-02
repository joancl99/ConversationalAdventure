package com.adventure.ui.fx;

import com.adventure.model.Classes;
import javafx.fxml.FXML;

public class ClassSelectController implements BaseController {

    private SceneManager sceneManager;
    private GameSession session;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;
        this.session = session;
    }

    @FXML
    private void onSelectWarrior() {
        session.newGame(Classes.WARRIOR);
        sceneManager.showWorld();
    }

    @FXML
    private void onSelectMage() {
        session.newGame(Classes.MAGE);
        sceneManager.showWorld();
    }

    @FXML
    private void onSelectRogue() {
        session.newGame(Classes.ROGUE);
        sceneManager.showWorld();
    }

    @FXML
    private void onBack() {
        sceneManager.showMainMenu();
    }
}
