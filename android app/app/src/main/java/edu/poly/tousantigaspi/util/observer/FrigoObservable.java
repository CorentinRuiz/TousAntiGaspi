package edu.poly.tousantigaspi.util.observer;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;

public abstract class FrigoObservable {

    public ArrayList<FrigoObserver> obs = new ArrayList<>();

    public void notifyObs(FrigoModel model){
        for(FrigoObserver ob : obs){
            ob.update(model);
        }
    }

    public void addObs(FrigoObserver ob){
        obs.add(ob);
    }
}
