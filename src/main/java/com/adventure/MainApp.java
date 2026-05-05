package com.adventure;

import com.adventure.ui.fx.GameSession;
import com.adventure.ui.fx.SceneManager;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final String[] CUSTOM_FONTS = {
        "/com/adventure/ui/fx/fonts/Cinzel-Regular.ttf",
        "/com/adventure/ui/fx/fonts/Cinzel-Bold.ttf",
        "/com/adventure/ui/fx/fonts/EBGaramond-Regular.ttf",
        "/com/adventure/ui/fx/fonts/EBGaramond-Italic.ttf",
        "/com/adventure/ui/fx/fonts/JetBrainsMono-Regular.ttf",
    };

    @Override
    public void start(Stage stage) {
        loadCustomFonts();

        GameSession session = new GameSession();
        SceneManager sceneManager = new SceneManager(stage, session);

        stage.setTitle("RIVERLAND");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        sceneManager.showMainMenu();
        stage.show();
    }

    private void loadCustomFonts() {
        for (String path : CUSTOM_FONTS) {
            var url = MainApp.class.getResource(path);
            if (url != null) Font.loadFont(url.toExternalForm(), 12);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
