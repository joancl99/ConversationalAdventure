package com.adventure.ui.fx;

public record LogEntry(String text, String color, boolean isSeparator) {
    public LogEntry(String text, String color) { this(text, color, false); }
}
