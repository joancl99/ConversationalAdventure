package com.adventure.ui.fx;

import com.adventure.engine.Save;
import com.adventure.model.Classes;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class VictoryController implements BaseController {

    @FXML private Label ornament;
    @FXML private Label title;
    @FXML private Label subtitle;
    @FXML private HBox  divider;
    @FXML private VBox  stats;
    @FXML private VBox  actions;

    @FXML private Label lblClass;
    @FXML private Label lblWins;

    private SceneManager sceneManager;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;

        Classes cls = session.getPlayer().getPlayerClass();
        lblClass.setText(cls.getClassName());
        lblClass.getStyleClass().add("text-" + cls.name().toLowerCase());

        int wins = session.getBattleManager().getWinCounter();
        lblWins.setText(wins + " enemies defeated · The LETHALDEMIGOD has fallen.");

        Save.resetSave();

        // Ornament gets a scale-in halo bloom; everything else just fades.
        playHaloBloom(ornament);
        runStaggeredEntrance(ornament, title, subtitle, divider, stats, actions);
    }

    @FXML
    private void onBackToMenu() {
        sceneManager.showMainMenu();
    }

    private static void playHaloBloom(Node node) {
        node.setScaleX(0.62);
        node.setScaleY(0.62);
        ScaleTransition st = new ScaleTransition(Duration.millis(900), node);
        st.setFromX(0.62); st.setFromY(0.62);
        st.setToX(1.0);    st.setToY(1.0);
        st.play();
    }

    /**
     * Same staggered reveal as GameOver — paired so the two terminal screens
     * have the same temporal cadence (one ends a chapter, the other crowns it).
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
