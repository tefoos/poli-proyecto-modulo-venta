package utils;

import java.io.File;


public class FolderUtils {
    
    /**
     * Crea una carpeta si no existe, o la limpia si ya existe.
     * 
     * @param folderPath la ruta de la carpeta a crear o limpiar
     */
    public static void createAndCleanFolder(String folderPath) {
        File folder = new File(folderPath);
        
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            cleanFolder(folder);
        }
    }
    
    /**
     * Limpia el contenido de la carpeta especificada eliminando todos los archivos.
     * Esto asegura que cada ejecución genere archivos completamente nuevos.
     * 
     * @param folder la carpeta que se va a limpiar
     */
    public static void cleanFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }
}