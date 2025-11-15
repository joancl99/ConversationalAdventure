import java.io.*;

public class Save 
{
    private static final String SAVE_FILE = "./saveFile/save.txt";

    // Save
    public static void save(Classes clase, int winCounter, Inventory inventory) 
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
                writer.write(clase.name() + "\n");
                writer.write(clase.getHP() + "\n");
                writer.write(clase.getMaxHp() + "\n");
                writer.write(clase.getAttack() + "\n");
                writer.write(clase.getAttackSpeed() + "\n");
                writer.write(winCounter + "\n");
                writer.write(inventory.serialize());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    // Load
    public static Classes loadSavedClass(BattleManager battleManager, Inventory inventory) 
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

            Classes clase = Classes.valueOf(className);

            if (hpLine != null) clase.setHP(Integer.parseInt(hpLine));
            if (maxHpLine != null) clase.setMaxHp(Integer.parseInt(maxHpLine));
            if (attackLine != null) clase.setAttack(Integer.parseInt(attackLine));
            if (attackSpeedLine != null) clase.setAttackSpeed(Double.parseDouble(attackSpeedLine));
            if (winCounterLine != null && battleManager != null)
                battleManager.winCounter = Integer.parseInt(winCounterLine);

            // read inventory lines
            StringBuilder inventoryData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) 
            {
                inventoryData.append(line).append("\n");
            }

            if (inventory != null) inventory.deserialize(inventoryData.toString().trim());

            return clase;
        } 
        catch (Exception e) 
        {
            System.out.println("Failed to load save file: " + e.getMessage());
            return null;
        }
    }

    // Reset
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
