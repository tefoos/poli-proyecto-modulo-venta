package models;

/**
 * Clase que representa un vendedor en el sistema
 */
public class Salesman {
    private String firstName;
    private String lastName;
    private int totalRevenue;
    
    public Salesman(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalRevenue = 0;
    }
    
    public void addSale(int amount) {
        this.totalRevenue += amount;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public int getTotalRevenue() { return totalRevenue; }
}
