package edu.poly.tousantigaspi.model;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.object.Frigo;

public class FrigoModel {
    List<Frigo> frigos;

    public FrigoModel(List<Frigo> frigos) {
        this.frigos = frigos;
    }

    public List<Frigo> getFrigos() {
        return frigos;
    }

    public void addFrigo(Frigo frigo){
        frigos.add(frigo);
    }

    public Frigo getFrigo(int position){
       return frigos.get(position);
    }
}
