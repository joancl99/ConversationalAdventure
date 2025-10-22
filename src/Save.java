import java.io.*;

public class Save 
{
    private static final String SAVE_FILE = "./saveFile/save.txt";

    //Save the player class name in the file.
    public static void saveClass(Classes clase) 
    {
        try 
        {
            File file = new File(SAVE_FILE);
            // Crear la carpeta si no existe
            if (file.getParentFile() != null) 
            {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) 
            {
                writer.write(clase.name());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving class.");
        }
    }

    //Load the saved class from the file, if it exists.
    public static Classes loadSavedClass() 
    {
        File file = new File(SAVE_FILE);
        
        if (!file.exists()) 
        {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line = reader.readLine();
            return Classes.valueOf(line); //Convert the string to an enum
        } 
        catch (Exception e) 
        {
            System.out.println("Failed to load save file.");
            return null;
        }
    }

    //Delete the save file
    public static void resetSave() 
    {
        File file = new File(SAVE_FILE);

        if (file.exists()) 
        {
            file.delete();
        }
    }
}
