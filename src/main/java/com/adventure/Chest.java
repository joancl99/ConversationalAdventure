package com.adventure;

import java.util.Random;

public class Chest {

    private final Random rand = new Random();

    public ChestResult foundChest() {
        if (rand.nextInt(100) < 60) return ChestResult.none();

        int roll = rand.nextInt(100);
        if (roll < 40)      return ChestResult.normal();
        else if (roll < 70) return ChestResult.silver();
        else if (roll < 95) return ChestResult.golden();
        else                return ChestResult.platinum();
    }
}
