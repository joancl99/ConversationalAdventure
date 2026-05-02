import java.io.*;

public class Save
{
    private static final String SAVE_FILE = "./saveFile/save.txt";

    public static void save(Player player, int winCounter, Inventory inventory)
    {
        try
        {
            File file = new File(SAVE_FILE);
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writer.write(player.getPlayerClass().name() + "\n");
                writer.write(player.getHP() + "\n");
                writer.write(player.getMaxHp() + "\n");
                writer.write(player.getAttack() + "\n");
                writer.write(player.getAttackSpeed() + "\n");
                writer.write(winCounter + "\n");
                writer.write(inventory.serialize());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static Player loadSavedPlayer(BattleManager battleManager, Inventory inventory)
    {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String className = reader.readLine();
            String hpLine = reader.readLine();
            String maxHpLine = reader.readLine();
            String attackLine = reader.readLine();
            String attackSpeedLine = reader.readLine();
            String winCounterLine = reader.readLine();

            Classes playerClass = Classes.valueOf(className);
            Player player = new Player(playerClass);

            if (hpLine != null) player.setHP(Integer.parseInt(hpLine));
            if (maxHpLine != null) player.setMaxHp(Integer.parseInt(maxHpLine));
            if (attackLine != null) player.setAttack(Integer.parseInt(attackLine));
            if (attackSpeedLine != null) player.setAttackSpeed(Double.parseDouble(attackSpeedLine));
            if (winCounterLine != null && battleManager != null)
                battleManager.setWinCounter(Integer.parseInt(winCounterLine));

            StringBuilder inventoryData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
            {
                inventoryData.append(line).append("\n");
            }

            if (inventory != null) inventory.deserialize(inventoryData.toString().trim());

            return player;
        }
        catch (Exception e)
        {
            System.out.println("Failed to load save file: " + e.getMessage());
            return null;
        }
    }

    public static void resetSave()
    {
        File file = new File(SAVE_FILE);
        if (file.exists())
        {
            if (file.delete())
            {
                System.out.println("\nYour save has been deleted and your progress has been reset.");
            }
            else
            {
                System.out.println("Failed to delete save file.");
            }
        }
    }
}
