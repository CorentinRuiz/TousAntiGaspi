package edu.poly.tousantigaspi.util.factory;

import java.time.LocalDate;

import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.CodeScannerProduct;
import edu.poly.tousantigaspi.object.ManuallyProduct;



public class ProductFactory extends Factory{

    public static final int MANNUALLY = 0;

    public static final int CODE_SCANNER = 1;

    @Override
    public Product build(String id,int type,Integer quantity,String date,String name) throws Throwable {
       switch (type){
           case MANNUALLY: return new ManuallyProduct(id,name,date,quantity);
           case CODE_SCANNER: return new CodeScannerProduct(id,name, LocalDate.now().toString(),1);
           default: throw new Throwable("can made this type");
       }
    }
}
