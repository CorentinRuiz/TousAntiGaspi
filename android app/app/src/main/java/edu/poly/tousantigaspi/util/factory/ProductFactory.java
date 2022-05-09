package edu.poly.tousantigaspi.util.factory;

import java.time.LocalDate;

import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.CodeScannerProduct;
import edu.poly.tousantigaspi.object.ManuallyProduct;

public class ProductFactory implements AbstractFactory<Product> {

    public static final int MANUALLY = 0;

    public static final int CODE_SCANNER = 1;

    @Override
    public Product build(int type) {
        switch (type) {
            case MANUALLY:
                return new ManuallyProduct();
            case CODE_SCANNER:
                return new CodeScannerProduct(LocalDate.now().toString(), 1);
            default:
                return new ManuallyProduct();
        }
    }
}