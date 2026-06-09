package com.adventure.ui.fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.List;

public class HistoryController implements BaseController {

    @FXML private ScrollPane scrollHistory;
    @FXML private VBox       historyBox;
    @FXML private Label      lblEntryCount;

    private SceneManager sceneManager;

    @Override
    public void init(SceneManager sceneManager, GameSession session) {
        this.sceneManager = sceneManager;

        List<LogEntry> log = session.getSessionLog();

        if (log.isEmpty()) {
            Label empty = new Label("No hay eventos registrados todavía.\nAvanza en el mundo para comenzar tu aventura.");
            empty.getStyleClass().add("empty-state");
            empty.setWrapText(true);
            historyBox.getChildren().add(empty);
        } else {
            for (LogEntry entry : log) {
                renderEntry(entry);
            }
        }

        long events = log.stream().filter(e -> !e.isSeparator()).count();
        lblEntryCount.setText(events + " entradas registradas");

        Platform.runLater(() -> scrollHistory.setVvalue(1.0));
    }

    @FXML
    private void onBack() {
        sceneManager.returnToWorld();
    }

    private void renderEntry(LogEntry entry) {
        if (entry.isSeparator()) {
            // Quiet rule between events — frame-faint, the palette's softest line.
            historyBox.getChildren().add(buildSeparator("#dfe6c6"));
            return;
        }

        String text = entry.text();

        // Heavy separator lines (━━━) → colored 1px rule using the entry's own ink.
        if (!text.isBlank() && text.chars().allMatch(c -> c == '━' || c == ' ')) {
            historyBox.getChildren().add(buildSeparator(entry.color()));
            return;
        }

        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setAlignment(Pos.TOP_LEFT);
        lbl.getStyleClass().add("log-entry");
        lbl.setStyle("-fx-text-fill: " + entry.color() + "; -fx-line-spacing: 2;");
        historyBox.getChildren().add(lbl);
    }

    private HBox buildSeparator(String color) {
        Region line = new Region();
        line.setPrefHeight(1);
        line.setMaxWidth(Double.MAX_VALUE);
        line.setStyle("-fx-background-color: " + color + ";");
        HBox.setHgrow(line, Priority.ALWAYS);

        HBox row = new HBox(line);
        VBox.setMargin(row, new Insets(10, 0, 10, 0));
        return row;
    }
}
