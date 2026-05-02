package com.adventure;

import java.util.Random;

public class GameLore {

    private final Random random = new Random();

    public LoreEvent generateLore() {
        return buildLoreEvent(random.nextInt(28));
    }

    private LoreEvent buildLoreEvent(int option) {
        switch (option) {
            case 0: return new LoreEvent(
                "Magician Frog",
                "You look like you've had a rough journey. You want to take a break, traveler?",
                LoreEffect.healFull(),
                LoreEffect.none()
            );
            case 1: return new LoreEvent(
                "Farmer",
                "It looks like there are so many monsters around here. Be careful on your travels!",
                LoreEffect.none()
            );
            case 2: return new LoreEvent(
                "Merchant",
                "Greetings, traveler! Are you looking for potions or weapons today? You haven't found the Villager's Shop yet?",
                LoreEffect.none()
            );
            case 3: return new LoreEvent(
                "Traveler",
                "Gold can't save you from what lurks in the dark, but maybe it can help you. He gives you 20 coins!",
                LoreEffect.coins(20)
            );
            case 4: return new LoreEvent(
                "City Guard",
                "You look so weak! You maybe need an upgrade. He gives you a damage potion!",
                LoreEffect.potionDmg()
            );
            case 5: return new LoreEvent(
                "Mysterious Sage",
                "I prepared a magic drink that may be useful for you... Do you drink it?",
                LoreEffect.damage(20),
                LoreEffect.none()
            );
            case 6: return new LoreEvent(
                null,
                "You found a Note: In this magic world, people whisper about a diabolic beast capable of destroying anyone who faces it...",
                LoreEffect.none()
            );
            case 7: return new LoreEvent(
                null,
                "You found a page of a Diary: I heard travelers talking in fear about a diabolic beast that destroys everyone who faces it...",
                LoreEffect.none()
            );
            case 8: return new LoreEvent(
                null,
                "You found a page of a Diary: I saw a hunter burned to ashes... It wasn't a normal Dragon's fire. It was something worse.",
                LoreEffect.none()
            );
            case 9: return new LoreEvent(
                null,
                "You see a dog running toward you. It has something in its mouth... it's a healing potion! You take it from the dog!",
                LoreEffect.potionHeal()
            );
            case 10: return new LoreEvent(null, "All is calm, or so it seems...", LoreEffect.none());
            case 11: return new LoreEvent(null, "The wind whispers... and you believe something is watching you.", LoreEffect.none());
            case 12: return new LoreEvent(null, "Silence... too much silence.", LoreEffect.none());
            case 13: return new LoreEvent(null, "You notice footprints that aren't yours.", LoreEffect.none());
            case 14: return new LoreEvent(null, "All seems peaceful... perhaps too peaceful.", LoreEffect.none());
            case 15: return new LoreEvent(null, "You feel as if someone or something is following you.", LoreEffect.none());
            case 16: return new LoreEvent(null, "A cold breeze sends chills down your spine.", LoreEffect.none());
            case 17: return new LoreEvent(null, "Birds are singing somewhere in the distance.", LoreEffect.none());
            case 18: return new LoreEvent(null, "A sudden silence fills the forest.", LoreEffect.none());
            case 19: return new LoreEvent(null, "You stop for a moment, enjoying the silence of nature.", LoreEffect.none());
            case 20: return new LoreEvent(null, "You hear a river flowing gently nearby.", LoreEffect.none());
            case 21: return new LoreEvent(null, "The forest is so dense... It feels relaxing here.", LoreEffect.none());
            case 22: return new LoreEvent(null, "In the distance, mountains rise majestically, their peaks touching the clouds.", LoreEffect.none());
            case 23: return new LoreEvent(null, "It's freezing here... Maybe you should have brought a jacket.", LoreEffect.none());
            case 24: return new LoreEvent(null, "A butterfly lands on your sword. Don't get any ideas, it's not enchanted.", LoreEffect.none());
            case 25: return new LoreEvent(null, "The path looks dangerous, but hey, you look even more dangerous...", LoreEffect.none());
            case 26: return new LoreEvent(null, "You take a deep breath of fresh air… and immediately sneeze.", LoreEffect.none());
            case 27: return new LoreEvent(null, "Wait, that tree looks familiar... Am I going in circles?", LoreEffect.none());
            default: return new LoreEvent(null, "Nothing happens.", LoreEffect.none());
        }
    }
}
