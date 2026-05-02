package com.adventure.event;

public class PotionEvent {

    public enum Type { NONE, HEAL, DAMAGE }

    private final Type type;

    private PotionEvent(Type type) { this.type = type; }

    public static PotionEvent none()   { return new PotionEvent(Type.NONE); }
    public static PotionEvent heal()   { return new PotionEvent(Type.HEAL); }
    public static PotionEvent damage() { return new PotionEvent(Type.DAMAGE); }

    public Type getType() { return type; }
}
