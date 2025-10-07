import java.io.*;
import java.util.*;

import models.Product;
import models.ProductSale;
import models.Salesman;
import seeder.FileGenerator;
import utils.FolderUtils;


public class Main {
    
    private static final String DATA_FOLDER = "data";
    private static final String REPORTS_FOLDER = "reportes";
    private static Map<String, Product> products = new HashMap<>();
    private static Map<Long, Salesman> salesmen = new HashMap<>();
    private static Map<String, Integer> salesByProduct = new HashMap<>();
    private static int errorsFound = 0;
    
    /**
     * Método principal que ejecuta el procesamiento de archivos y generación de reportes.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            //System.out.println("=== GENERANDO DATOS DE PRUEBA ===");
            //FileGenerator.generateTestData();
            
            System.out.println("\n=== PROCESANDO ARCHIVOS ===");
            
           FolderUtils.createAndCleanFolder(REPORTS_FOLDER);
            
            loadProducts();
            loadSalesmen();
            processSalesFiles();
            generateSalesmenReport();
            generateProductsReport();
            
            System.out.println("\n=== PROCESO COMPLETADO EXITOSAMENTE ===");
            if (errorsFound > 0) {
                System.out.println("ADVERTENCIA: Se encontraron " + errorsFound + 
                                 " errores durante el procesamiento");
                System.out.println("Los datos erróneos fueron ignorados.");
            }
            System.out.println("Reportes generados en la carpeta '" + REPORTS_FOLDER + "/'");
            
        } catch (Exception e) {
            System.err.println("Error procesando archivos: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
    /**
     * Carga la información de productos desde el archivo productos.txt
     * 
     * @throws IOException si hay error leyendo el archivo
     */
    private static void loadProducts() throws IOException {
        String filename = DATA_FOLDER + "/productos.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    int price = Integer.parseInt(parts[2]);
                    
                    products.put(id, new Product(id, name, price));
                    salesByProduct.put(id, 0);
                }
            }
        }
        System.out.println("Productos cargados: " + products.size());
    }
    
    /**
     * Carga la información de vendedores desde el archivo vendedores.txt
     * 
     * @throws IOException si hay error leyendo el archivo
     */
    private static void loadSalesmen() throws IOException {
        String filename = DATA_FOLDER + "/vendedores.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    long documentNumber = Long.parseLong(parts[1]);
                    String firstName = parts[2];
                    String lastName = parts[3];
                    
                    salesmen.put(documentNumber, new Salesman(firstName, lastName));
                }
            }
        }
        System.out.println("Vendedores cargados: " + salesmen.size());
    }
    
    /**
     * Procesa todos los archivos de ventas individuales de vendedores
     * 
     * @throws IOException si hay error procesando los archivos
     */
    private static void processSalesFiles() throws IOException {
        File folder = new File(DATA_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.startsWith("vendedor_") && name.endsWith(".txt"));
        
        if (files == null) {
            throw new IOException("No se encontraron archivos de vendedores");
        }
        
        for (File file : files) {
            processSalesmanFile(file);
        }
        
        System.out.println("Archivos de ventas procesados: " + files.length);
    }
    
    /**
     * Procesa un archivo individual de ventas de un vendedor
     * 
     * @param file el archivo a procesar
     * @throws IOException si hay error leyendo el archivo
     */
    private static void processSalesmanFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            if (firstLine == null) return;
            
            String[] parts = firstLine.split(";");
            if (parts.length < 2) {
                System.err.println("Error en archivo " + file.getName() + ": formato de encabezado incorrecto");
                errorsFound++;
                return;
            }
            
            long salesmanId;
            try {
                salesmanId = Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                System.err.println("Error en archivo " + file.getName() + ": ID de vendedor inválido");
                errorsFound++;
                return;
            }
            
            Salesman salesman = salesmen.get(salesmanId);
            if (salesman == null) {
                salesman = new Salesman("Vendedor", "Desconocido");
                salesmen.put(salesmanId, salesman);
            }
            
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] saleParts = line.split(";");
                if (saleParts.length < 2) {
                    System.err.println("Error en " + file.getName() + " línea " + lineNumber + ": formato incorrecto");
                    errorsFound++;
                    continue;
                }
                
                String productId = saleParts[0].trim();
                
                Product product = products.get(productId);
                if (product == null) {
                    System.err.println("Error en " + file.getName() + " línea " + lineNumber + 
                                     ": producto con ID '" + productId + "' no existe");
                    errorsFound++;
                    continue;
                }
                
                int quantity;
                try {
                    quantity = Integer.parseInt(saleParts[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Error en " + file.getName() + " línea " + lineNumber + 
                                     ": cantidad inválida");
                    errorsFound++;
                    continue;
                }
                
                if (quantity < 0) {
                    System.err.println("Error en " + file.getName() + " línea " + lineNumber + 
                                     ": cantidad negativa (" + quantity + ")");
                    errorsFound++;
                    continue;
                }
                
                int saleAmount = product.getPrice() * quantity;
                salesman.addSale(saleAmount);
                
                salesByProduct.put(productId, salesByProduct.get(productId) + quantity);
            }
        }
    }
    
    /**
     * Genera el reporte de vendedores ordenado por dinero recaudado (mayor a menor)
     * 
     * @throws IOException si hay error escribiendo el archivo
     */
    private static void generateSalesmenReport() throws IOException {
        String filename = REPORTS_FOLDER + "/reporte_vendedores.csv";
        
        List<Salesman> sortedSalesmen = new ArrayList<>(salesmen.values());
        sortedSalesmen.sort((s1, s2) -> Integer.compare(s2.getTotalRevenue(), s1.getTotalRevenue()));
        
        try (FileWriter writer = new FileWriter(filename)) {
            for (Salesman salesman : sortedSalesmen) {
                writer.write(salesman.getFullName() + ";" + salesman.getTotalRevenue() + "\n");
            }
        }
        
        System.out.println("Reporte de vendedores generado: " + filename);
    }
    
    /**
     * Genera el reporte de productos ordenado por cantidad vendida (mayor a menor)
     * 
     * @throws IOException si hay error escribiendo el archivo
     */
    private static void generateProductsReport() throws IOException {
        String filename = REPORTS_FOLDER + "/reporte_productos.csv";
        
        List<ProductSale> productSales = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : salesByProduct.entrySet()) {
            Product product = products.get(entry.getKey());
            if (product != null && entry.getValue() > 0) {
                productSales.add(new ProductSale(product, entry.getValue()));
            }
        }
        
        productSales.sort((p1, p2) -> Integer.compare(p2.getQuantitySold(), p1.getQuantitySold()));
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("NombreProducto;PrecioUnitario;CantidadVendida\n");
            for (ProductSale ps : productSales) {
                writer.write(ps.getProduct().getName() + ";" + 
                           ps.getProduct().getPrice() + ";" + 
                           ps.getQuantitySold() + "\n");
            }
        }
        
        System.out.println("Reporte de productos generado: " + filename);
    }
    
}