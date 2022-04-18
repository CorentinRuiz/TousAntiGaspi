package edu.poly.tousantigaspi.object;

public class Product {
    private String name;
    private String dateRemaining;

    public Product(String name, String dateRemaining) {
        this.name = name;
        this.dateRemaining = dateRemaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateRemaining() {
        return dateRemaining;
    }

    public void setDateRemaining(String dateRemaining) {
        this.dateRemaining = dateRemaining;
    }
}
