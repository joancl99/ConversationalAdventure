package com.adventure.ui.fx;

import com.adventure.engine.Save;
import com.adventure.model.Classes;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameOverController implements BaseController {

    @FXML private Label ornament;
    @FXML private Label title;
    @FXML private Label subtitle;
    @FXML private HBox  divider;
    @FXML private VBox  stats;
    @FXML private VBox  actions;

    @FXML private Label lblWins;
    @FXML private Label lblClass;

    private SceneManager sceneManager;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;

        int wins = session.getBattleManager().getWinCounter();
        lblWins.setText("You fell after " + wins + (wins == 1 ? " victory." : " victories."));

        Classes cls = session.getPlayer().getPlayerClass();
        lblClass.setText(cls.getClassName());
        lblClass.getStyleClass().add("text-" + cls.name().toLowerCase());

        Save.resetSave();

        runStaggeredEntrance(ornament, title, subtitle, divider, stats, actions);
    }

    @FXML
    private void onNewGame() {
        sceneManager.showClassSelect();
    }

    @FXML
    private void onBackToMenu() {
        sceneManager.showMainMenu();
    }

    /**
     * Staggered fade-in — each node enters 220ms after the previous one.
     * Total reveal ≈ 1.7s, slow enough to read as a manuscript being written
     * onto the page rather than a UI animation.
     */
    private static void runStaggeredEntrance(Node... nodes) {
        int delay = 0;
        int step  = 220;
        for (Node n : nodes) {
            n.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(620), n);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setDelay(Duration.millis(delay));
            ft.play();
            delay += step;
        }
    }
}
