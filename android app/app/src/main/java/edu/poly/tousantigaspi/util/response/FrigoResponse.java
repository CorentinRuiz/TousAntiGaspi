package edu.poly.tousantigaspi.util.response;

import java.util.List;

import edu.poly.tousantigaspi.object.Product;

public class FrigoResponse {

    private String name;
    private List<Product> products;

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
