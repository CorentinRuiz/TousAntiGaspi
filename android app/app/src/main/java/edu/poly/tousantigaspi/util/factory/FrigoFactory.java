package edu.poly.tousantigaspi.util.factory;

import edu.poly.tousantigaspi.object.Frigo;

public class FrigoFactory implements AbstractFactory<Frigo> {

    public static final int BASIC = 0;

    @Override
    public Frigo build(int type) {
        switch (type) {
            case BASIC:
                return new Frigo();
            default:
                return new Frigo();
        }
    }
}