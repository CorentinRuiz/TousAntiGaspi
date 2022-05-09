package edu.poly.tousantigaspi.util.factory;

public interface AbstractFactory<T> {
    T build(int type);
}
