package com.adventure.ui.fx;

/**
 * Hex colours used when writing to {@link GameSession#getSessionLog()} so the
 * History screen renders entries in the manuscript palette. Both
 * {@link WorldController} and {@link ShopController} write log entries; keeping
 * the colours here ensures the two never drift apart.
 *
 * Mirrors the manuscript palette in {@code theme.css}.
 */
public final class LogPalette {
    public static final String NORMAL = "#2d4a38";  // ink         — main narration
    public static final String SOFT   = "#5a7160";  // ink-soft    — softer narration
    public static final String RULE   = "#b09c6e";  // frame-light — separators
    public static final String HEAL   = "#3d6845";  // moss-deep   — restoration
    public static final String DAMAGE = "#a84030";  // vermillion  — danger / hp loss
    public static final String FATAL  = "#7a2e23";  // vermillion-soft — final boss / death
    public static final String GOLD   = "#9a7042";  // gold-soft   — coins, victory
    public static final String BUFF   = "#8c5a9c";  // mulberry    — arcane buff / mini-boss
    public static final String LORE   = "#4a6fa5";  // lapis       — lore speakers / info

    private LogPalette() {}
}
