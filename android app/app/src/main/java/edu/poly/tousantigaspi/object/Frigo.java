package edu.poly.tousantigaspi.object;

import java.util.List;

public class Frigo {

    private String name;
    private List<Product> products;

    public Frigo(String name,List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
