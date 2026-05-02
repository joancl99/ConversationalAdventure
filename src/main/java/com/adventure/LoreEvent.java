package com.adventure;

public class LoreEvent {

    private final String npcName;
    private final String message;
    private final boolean requiresChoice;
    private final LoreEffect effectOnYes;
    private final LoreEffect effectOnNo;

    /** Non-interactive event: just display message and apply effect. */
    public LoreEvent(String npcName, String message, LoreEffect effect) {
        this.npcName = npcName;
        this.message = message;
        this.requiresChoice = false;
        this.effectOnYes = effect;
        this.effectOnNo = LoreEffect.none();
    }

    /** Interactive event: display message, ask Y/N, apply matching effect. */
    public LoreEvent(String npcName, String message, LoreEffect effectOnYes, LoreEffect effectOnNo) {
        this.npcName = npcName;
        this.message = message;
        this.requiresChoice = true;
        this.effectOnYes = effectOnYes;
        this.effectOnNo = effectOnNo;
    }

    public String getNpcName()         { return npcName; }
    public String getMessage()         { return message; }
    public boolean requiresChoice()    { return requiresChoice; }
    public LoreEffect getEffectOnYes() { return effectOnYes; }
    public LoreEffect getEffectOnNo()  { return effectOnNo; }
}
