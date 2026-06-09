package com.adventure.ui.fx;

/**
 * Hex colours used when writing to {@link GameSession#getSessionLog()} so the
 * History screen renders entries in the Living Meadow palette. Both
 * {@link WorldController} and {@link ShopController} write log entries; keeping
 * the colours here ensures the two never drift apart.
 *
 * Mirrors the Living Meadow palette in {@code theme.css}. Values are tuned a
 * touch deeper than the accent tokens so narration stays readable on the bright
 * surfaces.
 */
public final class LogPalette {
    public static final String NORMAL = "#2a4426";  // deep forest ink — main narration
    public static final String SOFT   = "#5f7556";  // ink-soft        — softer narration
    public static final String RULE   = "#c6d0a4";  // frame-light     — separators
    public static final String HEAL   = "#2f7d33";  // moss-deep       — restoration
    public static final String DAMAGE = "#c8431f";  // dragonfire      — danger / hp loss
    public static final String FATAL  = "#9a2d1a";  // deep dragonfire — final boss / death
    public static final String GOLD   = "#b07d16";  // deep sun gold   — coins, victory
    public static final String BUFF   = "#8a44a8";  // arcane purple   — arcane buff / mini-boss
    public static final String LORE   = "#2f7fc0";  // river blue      — lore speakers / info

    private LogPalette() {}
}
