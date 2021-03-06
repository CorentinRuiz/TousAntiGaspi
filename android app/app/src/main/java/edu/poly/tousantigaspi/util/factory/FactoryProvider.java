package edu.poly.tousantigaspi.util.factory;

public class FactoryProvider {
    public static final int PRODUCT = 0;
    public static final int FRIGO = 1;

    public static AbstractFactory getFactory(int typeOfFactory) {
        switch (typeOfFactory) {
            case PRODUCT:
                return new ProductFactory();
            case FRIGO:
                return new FrigoFactory();
            default:
                return new ProductFactory();
        }
    }
}
