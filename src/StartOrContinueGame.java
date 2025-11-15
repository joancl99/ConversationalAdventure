public class StartOrContinueGame 
{
    public void startOrContinue() 
    {
        Player player = new Player();
        BattleManager enemyManager = new BattleManager();
        Potions poti = new Potions(0, 0);
        Coins coin = new Coins(0);
        Chest chest = new Chest(poti, coin);
        Villager villager = new Villager();
        Inventory inventory = new Inventory(poti, coin, villager);

        // load class + stats + inventory + winCounter
        Classes playerClass = Save.loadSavedClass(enemyManager, inventory);

        if (playerClass == null) 
        {
            playerClass = player.chooseClass();
            playerClass.restoreHp();
            Save.save(playerClass, enemyManager.winCounter, inventory);
        } 
        else 
        {
            System.out.println(FontColors.PURPLE + "\nYou are continuing as: " + FontColors.CYAN + FontColors.BOLD + playerClass.getClassName() + FontColors.RESET);
            System.out.println(FontColors.PURPLE + "Current HP: " + FontColors.WHITE + playerClass.getHP() + FontColors.PURPLE + "/" + FontColors.WHITE +playerClass.getMaxHp());
            playerClass.showStats();
            inventory.objectsInInventory(); // show inventory
        }

        player.playerMovement(playerClass, enemyManager, poti, coin, chest, villager, inventory);
        Save.save(playerClass, enemyManager.winCounter, inventory);
    }
}
