package seeder;


public class DataSeeders {

    public static final String[] NAMES = {
            "Juan Carlos",
            "María Fernanda",
            "Carlos Eduardo",
            "Ana Lucía",
            "Luis Fernando",
            "Diana Patricia",
            "José Miguel",
            "Laura Cristina",
            "Andrés Felipe",
            "Sandra Milena",
            "Diego Alejandro",
            "Claudia Marcela",
            "Ricardo Javier",
            "Gloria Elena",
            "Sergio Andrés",
            "Carmen Rosa",
            "Fernando José",
            "Mónica Andrea",
            "Héctor Fabián",
            "Beatriz Elena"

    };


    public static final String[] LASTNAMES = {
            "García Martínez",
            "Rodríguez López",
            "González Pérez",
            "Hernández Silva",
            "López Ramírez",
            "Martínez Gómez",
            "Pérez Vargas",
            "Sánchez Torres",
            "Ramírez Castro",
            "Cruz Morales",
            "Morales Ruiz",
            "Jiménez Ortiz",
            "Ruiz Delgado",
            "Díaz Herrera",
            "Moreno Castillo",
            "Álvarez Ramos",
            "Romero Guerrero",
            "Gutiérrez Medina",
            "Vargas Aguilar",
            "Castillo Vásquez"

    };

    public static final String[] DOCUMENT_TYPE = {
    		"CC",
    		"CE"
    };
    
    public static final Product[] PRODUCTS = {
            new Product("001", "Laptop Dell Inspiron", 2500000),
            new Product("002", "Mouse Logitech MX", 180000),
            new Product("003", "Teclado Mecánico Corsair", 350000),
            new Product("004", "Monitor Samsung 24''", 800000),
            new Product("005", "Auriculares Sony WH", 450000),
            new Product("006", "Tablet iPad Air", 1800000),
            new Product("007", "Smartphone Samsung Galaxy", 1200000),
            new Product("008", "Impresora HP LaserJet", 650000),
            new Product("009", "Disco Duro Externo 1TB", 320000),
            new Product("010", "Memoria USB 64GB", 45000),
            new Product("011", "Cámara Web Logitech", 180000),
            new Product("012", "Parlante Bluetooth JBL", 280000),
            new Product("013", "Cargador Portátil Anker", 120000),
            new Product("014", "Router WiFi TP-Link", 200000),
            new Product("015", "Cable HDMI 2m", 25000),
            new Product("016", "Adaptador USB-C", 35000),
            new Product("017", "SSD Kingston 500GB", 380000),
            new Product("018", "Mousepad Gaming", 45000),
            new Product("019", "Soporte para Laptop", 150000),
            new Product("020", "Hub USB 4 Puertos", 85000),
            new Product("021", "MacBook Pro 13''", 4500000),
            new Product("022", "iPhone 15", 3200000),
            new Product("023", "AirPods Pro", 680000),
            new Product("024", "Smart TV LG 55''", 2100000),
            new Product("025", "PlayStation 5", 2800000),
            new Product("026", "Xbox Series X", 2600000),
            new Product("027", "Nintendo Switch", 1400000),
            new Product("028", "Drone DJI Mini", 1800000),
            new Product("029", "GoPro Hero 12", 1600000),
            new Product("030", "Smartwatch Apple", 1200000),
            new Product("031", "Fitbit Charge 5", 450000),
            new Product("032", "Kindle Paperwhite", 480000),
            new Product("033", "Echo Dot Amazon", 180000),
            new Product("034", "Google Nest Hub", 380000),
            new Product("035", "Ring Video Doorbell", 650000),
            new Product("036", "Chromecast Google", 150000),
            new Product("037", "Fire TV Stick", 120000),
            new Product("038", "Webcam 4K Logitech", 420000),
            new Product("039", "Micrófono Blue Yeti", 520000),
            new Product("040", "Auriculares Gaming Razer", 380000),
            new Product("041", "Silla Gaming Corsair", 850000),
            new Product("042", "Escritorio Gaming", 650000),
            new Product("043", "Lámpara LED RGB", 120000),
            new Product("044", "Ventilador USB", 45000),
            new Product("045", "Cooler para Laptop", 95000),
            new Product("046", "Base Refrigerante", 130000),
            new Product("047", "Protector Surge", 85000),
            new Product("048", "UPS APC 600VA", 320000),
            new Product("049", "Switch Ethernet 8 puertos", 180000),
            new Product("050", "Antena WiFi USB", 65000)
       };

    public static class Product {
    	private String id;
    	private String name;
    	private int price;

        public Product(String id, String name, int price) {
        	this.id = id;
        	this.name = name;
        	this.price = price;
        }

        public String getId() { return id; }
        public String getNombre() { return name; }
        public int getPrecio() { return price; }

        @Override
        public String toString() {
        	return id + ";" + name + ";" + price;
        }

 }


}
