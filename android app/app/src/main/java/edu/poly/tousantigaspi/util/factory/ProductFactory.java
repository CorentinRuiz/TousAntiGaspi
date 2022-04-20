package edu.poly.tousantigaspi.util.factory;

import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.ProductWithPic;
import edu.poly.tousantigaspi.object.ProductWithoutPic;



public class ProductFactory extends Factory{

    public static final int WITHOUT_PIC = 0;

    public static final int WITH_PIC = 1;

    @Override
    public Product build(int type,Integer quantity,String path,String date,String name) throws Throwable {
       switch (type){
           case WITHOUT_PIC: return new ProductWithoutPic(name,date,quantity);
           case WITH_PIC: return new ProductWithPic(name,date,path,quantity);
           default: throw new Throwable("can made this type");
       }
    }
}
