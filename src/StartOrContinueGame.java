public class StartOrContinueGame
{
    public void startOrContinue() 
    {
        Player player = new Player();

        EnemyManager enemyManager = new EnemyManager();

        Classes playerClass = Save.loadSavedClass();

        Potions poti = new Potions(1, 0, 0, 0);

        Coins coin = new Coins(5);

        Chest chest = new Chest(poti, coin);
        

        if (playerClass == null) 
        {
            playerClass = player.chooseClass();
            Save.saveClass(playerClass);
        } 
        else 
        {
            System.out.println(FontColors.PURPLE +"\nYou are continuing as: " + playerClass);
            playerClass.showStats();
        }

        player.playerMovement(playerClass, enemyManager, poti, coin, chest);
    }
}