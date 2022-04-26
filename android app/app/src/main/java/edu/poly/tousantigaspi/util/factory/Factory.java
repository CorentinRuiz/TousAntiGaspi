package edu.poly.tousantigaspi.util.factory;

import edu.poly.tousantigaspi.object.Product;

abstract class Factory {

    public abstract Product build(String id,int type,Integer quantity,String path,String date,String name) throws Throwable;

}
