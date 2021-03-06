package edu.poly.tousantigaspi.object;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Product  {

    private String id;
    private String name;
    private String dateRemaining;
    private Integer quantity;

    public Product(String id,String name, String dateRemaining,Integer quantity) {
        this.id = id;
        this.name = name;
        this.dateRemaining = dateRemaining;
        this.quantity = quantity;
    }

    public Product() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPast(Integer dlcLimit){
        return Integer.parseInt(this.dateRemaining.split(" ")[0]) < dlcLimit;
    }
}
