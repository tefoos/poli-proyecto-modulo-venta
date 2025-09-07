package seeder;

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
	
	private static final Random random = new Random();
	private static final String DATA_FOLDER = "data";
	
	
	public static void main(String[] args) {
		try {
			createAndCleanDataFolder();
			
			System.out.println("Generando archivos....");
			
			createProductsFile(50);
			System.out.println("Productos Generados Exitosamente...");
			
			createSalesManInfoFile(10);
			System.out.println("Informacion de Vendedores Generada Exitosamente...");
			
			for (int i = 1; i <= 10; i++) {
				String name = DataSeeders.NAMES[random.nextInt(DataSeeders.NAMES.length)];
				long id = generateRandomIdNumber();
	            int salesCount = random.nextInt(15) + 1;
	            
	            createSalesMenFile(salesCount, name, id);
			}
			
		} catch (Exception e) {
	        System.err.println("Error generando archivos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/**
	* Crea un archivo con información de productos disponibles.
	* 
	* Genera un archivo de texto con los datos de productos incluyendo ID, nombre y precio.
	* Utiliza los productos predefinidos en DataSeeders hasta el límite solicitado.
	* 
	* @param productsCount número de productos a incluir en el archivo
	*/
	public static void createProductsFile(int productsCount) {
		String filename = DATA_FOLDER + "/productos.txt";
		
		try (FileWriter writer = new FileWriter(filename)){
			
			int maxProducts = Math.min(productsCount, DataSeeders.PRODUCTS.length);
			
			for (int i = 0; i < maxProducts; i++) {
				
				DataSeeders.Product product = DataSeeders.PRODUCTS[i];
				writer.write(product.toString() + "\n");
			}
		} catch (IOException e) {
			throw new RuntimeException("Error al crear archivo de productos: " + e.getMessage(), e);
		}
	}
	
	/**
	* Crea un archivo con información pseudoaleatoria de vendedores.
	* 
	* Genera un archivo de texto con datos de vendedores incluyendo tipo de documento,
	* número de identificación, nombres y apellidos extraídos de listas predefinidas.
	* 
	* @param salesmanCount número de vendedores a generar en el archivo
	*/
	public static void createSalesManInfoFile(int salesmanCount) {
		String filename = DATA_FOLDER +  "/vendedores.txt";
		try (FileWriter writer = new FileWriter(filename)){
			for (int i = 0; i < salesmanCount; i++) {
				
				String documentType = DataSeeders.DOCUMENT_TYPE[random.nextInt(DataSeeders.DOCUMENT_TYPE.length)];
				long id = generateRandomIdNumber();
				String name = DataSeeders.NAMES[random.nextInt(DataSeeders.NAMES.length)];
				String lastName = DataSeeders.LASTNAMES[random.nextInt(DataSeeders.LASTNAMES.length)];
				
				writer.write(documentType + ";" + id + ";" + name + ";" + lastName + "\n");
			}
		} catch (IOException e) {
            throw new RuntimeException("Error al crear archivo de vendedores: " + e.getMessage(), e);
        }
	}
	
	/**
	* Crea un archivo de ventas pseudoaleatorio para un vendedor específico.
	* 
	* El archivo contiene la información del vendedor en la primera línea y luego
	* una lista de productos vendidos con sus respectivas cantidades.
	* 
	* @param randomSalesCount número de productos vendidos a generar
	* @param name nombre del vendedor (usado para referencia)
	* @param id número de identificación del vendedor
	*/
	public static void createSalesMenFile(int randomSalesCount, String name, long id) {
		String filename = DATA_FOLDER + "/vendedor_" + id + ".txt";
		
		try (FileWriter writer = new FileWriter(filename)) {
			
			String documentType = DataSeeders.DOCUMENT_TYPE[random.nextInt(DataSeeders.DOCUMENT_TYPE.length)];
			writer.write(documentType + ";" + id + "\n");
			
			for (int i = 0; i < randomSalesCount; i++) {
				DataSeeders.Product product = DataSeeders.PRODUCTS[random.nextInt(DataSeeders.PRODUCTS.length)];
				int quantitySold = random.nextInt(20) + 1;
				writer.write(product.getId() + ";" + quantitySold + ";\n");
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Error al crear archivo de ventas para vendedor " + id + ": " + e.getMessage(), e);
		}
	}
	
	/**
	* Genera un número de identificación aleatorio para un vendedor entre 10,000,000 y 9,999,999,999.
	* 
	* @return ID del vendedor válido de 8 a 10 dígitos
	*/
	private static long generateRandomIdNumber() {
		return 10_000_000L + (long)(random.nextDouble() * 9_989_999_999L);
	}
	
	/**
	* Crea la carpeta de datos si no existe, o la limpia si ya existe.
	* Garantiza que exista una carpeta sin datos para generar archivos nuevos.
	*/
	private static void createAndCleanDataFolder() {
		File folder = new File(DATA_FOLDER);
		
		if(!folder.exists()) {
			folder.mkdirs();
		} else {
			cleanDataFolder(folder);
		}
	}
	
	 /**
	 * Limpia el contenido de la carpeta especificada eliminando todos los archivos.
	 * Esto asegura que cada ejecución del programa genere archivos completamente nuevos.
	 * 
	 * @param folder la carpeta que se va a limpiar
	 */
	private static void cleanDataFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null && files.length > 0) {
			for (File file: files) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
	}
}
