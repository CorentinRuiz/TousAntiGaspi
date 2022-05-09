package edu.poly.tousantigaspi.object;

import java.util.List;

public class Frigo {

    private String id;
    private String name;
    private List<Product> products;

    public Frigo(String id,String name,List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Frigo() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
