package com.adventure;

import com.adventure.ui.fx.GameSession;
import com.adventure.ui.fx.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        GameSession session = new GameSession();
        SceneManager sceneManager = new SceneManager(stage, session);

        stage.setTitle("RiverLand — The Adventure");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        sceneManager.showMainMenu();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
