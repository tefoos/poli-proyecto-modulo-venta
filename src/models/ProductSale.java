package models;

/**
 * Clase auxiliar para manejar productos con cantidad vendida
 */
public class ProductSale {
    private Product product;
    private int quantitySold;
    
    public ProductSale(Product product, int quantitySold) {
        this.product = product;
        this.quantitySold = quantitySold;
    }
    
    public Product getProduct() { return product; }
    public int getQuantitySold() { return quantitySold; }
}