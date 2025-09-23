package seeder;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import utils.FolderUtils;

public class FileGenerator {
	
	private static final Random random = new Random();
	private static final String DATA_FOLDER = "data";
	
	
	
	/**
	* Genera todos los archivos de prueba necesarios para el sistema.
	* 
	* Crea archivos de productos, vendedores y sus respectivas ventas.
	*/
	public static void generateTestData() {
		try {
			FolderUtils.createAndCleanFolder(DATA_FOLDER);
			
			System.out.println("Generando archivos de prueba...");
			
			createProductsFile(50);
			System.out.println("Productos generados");
			
			List<SalesmanData> salesmenList = createSalesManInfoFile(10);
			System.out.println("Vendedores generados");
			
			for (SalesmanData salesman : salesmenList) {
	            int salesCount = random.nextInt(15) + 1;
	            createSalesMenFile(salesCount, salesman.getName(), salesman.getId());
			}
			System.out.println("Archivos de ventas generados");
			
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
	* @return lista con los datos de los vendedores generados
	*/
	public static List<SalesmanData> createSalesManInfoFile(int salesmanCount) {
		String filename = DATA_FOLDER +  "/vendedores.txt";
		List<SalesmanData> salesmenList = new ArrayList<>();
		
		try (FileWriter writer = new FileWriter(filename)){
			for (int i = 0; i < salesmanCount; i++) {
				
				String documentType = DataSeeders.DOCUMENT_TYPE[random.nextInt(DataSeeders.DOCUMENT_TYPE.length)];
				long id = generateRandomIdNumber();
				String name = DataSeeders.NAMES[random.nextInt(DataSeeders.NAMES.length)];
				String lastName = DataSeeders.LASTNAMES[random.nextInt(DataSeeders.LASTNAMES.length)];
				
				writer.write(documentType + ";" + id + ";" + name + ";" + lastName + "\n");
				salesmenList.add(new SalesmanData(id, name, lastName));
			}
		} catch (IOException e) {
            throw new RuntimeException("Error al crear archivo de vendedores: " + e.getMessage(), e);
        }
        
        return salesmenList;
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
	* Clase interna para almacenar datos de vendedores temporalmente
	*/
	private static class SalesmanData {
		private long id;
		private String name;
		public SalesmanData(long id, String name, String lastName) {
			this.id = id;
			this.name = name;
		}
		
		public long getId() { return id; }
		public String getName() { return name; }
	}
}