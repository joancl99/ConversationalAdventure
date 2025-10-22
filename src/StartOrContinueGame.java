public class StartOrContinueGame
{
    public void startOrContinue() 
    {
        Player player = new Player();

        BattleManager enemyManager = new BattleManager();

        Classes playerClass = Save.loadSavedClass();

        Potions poti = new Potions(0, 0);

        Coins coin = new Coins(0);

        Chest chest = new Chest(poti, coin);

        Villager villager = new Villager();

        Inventory inventory = new Inventory(poti, coin, villager);
        

        if (playerClass == null) 
        {
            playerClass = player.chooseClass();
            Save.saveClass(playerClass);
        } 
        else 
        {
            System.out.println(FontColors.PURPLE +"\nYou are continuing as: " + playerClass.getClassName());
            playerClass.showStats();
        }

        player.playerMovement(playerClass, enemyManager, poti, coin, chest, villager, inventory);
    }
}