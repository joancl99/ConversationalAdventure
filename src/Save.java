import java.io.*;

public class Save 
{
    private static final String SAVE_FILE = "./saveFile/save.txt";

    // Guarda el nombre de la clase del jugador en el archivo
    public static void saveClass(Classes clase) {
        try {
            File file = new File(SAVE_FILE);
            // Crear la carpeta si no existe
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(clase.name());
            }
        } catch (IOException e) {
            System.out.println("Error saving class.");
        }
    }

    // Carga la clase guardada desde el archivo, si existe
    public static Classes loadSavedClass() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Classes.valueOf(line); // Convierte el string a enum
        } catch (Exception e) {
            System.out.println("Failed to load save file.");
            return null;
        }
    }

    // Elimina el archivo de guardado (opcional)
    public static void resetSave() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
