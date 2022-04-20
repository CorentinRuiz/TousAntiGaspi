package edu.poly.tousantigaspi.object;

public class ProductWithPic extends Product{

    public String pathToPic;

    public ProductWithPic(String name, String dateRemaining,String path,Integer quantity) {
        super(name, dateRemaining,quantity);
        this.pathToPic = path;
    }
}
