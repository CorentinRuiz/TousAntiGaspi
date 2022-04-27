package edu.poly.tousantigaspi.util.factory;

public interface AbstractFactory<T> {
    T build(String id, int type, Integer quantity, String date, String name);
}
