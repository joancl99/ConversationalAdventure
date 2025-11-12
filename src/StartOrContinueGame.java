public class StartOrContinueGame {
    public void startOrContinue() {
        Player player = new Player();
        BattleManager enemyManager = new BattleManager();
        Potions poti = new Potions(0, 0);
        Coins coin = new Coins(0);
        Chest chest = new Chest(poti, coin);
        Villager villager = new Villager();
        Inventory inventory = new Inventory(poti, coin, villager);

        // Cargar clase + stats + inventario + contador de victorias
        Classes playerClass = Save.loadSavedClass(enemyManager, inventory);

        if (playerClass == null) {
            playerClass = player.chooseClass();
            playerClass.restoreHp();
            Save.save(playerClass, enemyManager.winCounter, inventory);
        } else {
            System.out.println(FontColors.PURPLE + "\nYou are continuing as: " + playerClass.getClassName());
            System.out.println("Current HP: " + playerClass.getHP() + "/" + playerClass.getMaxHp());
            playerClass.showStats();
            inventory.objectsInInventory(); // mostrar inventario cargado
        }

        // Movimiento del jugador en el juego
        player.playerMovement(playerClass, enemyManager, poti, coin, chest, villager, inventory);

        // Guardar al final del turno
        Save.save(playerClass, enemyManager.winCounter, inventory);
    }
}
