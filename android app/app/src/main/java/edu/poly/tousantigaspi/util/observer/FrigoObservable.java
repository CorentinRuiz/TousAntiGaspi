package edu.poly.tousantigaspi.util.observer;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.object.Frigo;

public abstract class FrigoObservable {

    public ArrayList<FrigoObserver> obs = new ArrayList<>();

    public void notifyObs(List<Frigo> frigos){
        for(FrigoObserver ob : obs){
            ob.update(frigos);
        }
    }

    public void addObs(FrigoObserver ob){
        obs.add(ob);
    }
}
