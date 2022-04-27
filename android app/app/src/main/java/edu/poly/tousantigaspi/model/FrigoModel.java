package edu.poly.tousantigaspi.model;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import edu.poly.tousantigaspi.fragment.ListFragment;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.repositories.FrigoRepository;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrigoModel {

    private FrigoRepository repository;
    private ArrayList<Frigo> frigos;
    private Fragment view;

    public FrigoModel(Fragment fragment) {
        repository = FrigoRepository.getInstance();
        frigos = new ArrayList<>();
        view = fragment;
    }

    public List<Frigo> getFrigos() {
        return frigos;
    }

    public void addFrigo(Frigo frigo){
        frigos.add(frigo);
    }

    public void editFrigo(String id,String newName){
        this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().setName(newName);
    }

    public void loadFrigo(String username){
       repository.getFrigos(username,this);
    }

    public void loadPastDLCProduct(String username){

    }

    public void setFrigos(ArrayList<Frigo> frigos) {
        this.frigos = frigos;
    }

    public Frigo getFrigo(int position){
       return frigos.get(position);
    }

    public void addProduct(String id,Product product){
        this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().getProducts().add(product);
        notifyViewChange();
    }


    public void notifyViewChange(){
        if(view instanceof MainFragment){
            ((MainFragment) view).update(frigos);
        }
        else if(view instanceof ListFragment){
            ((ListFragment) view).update(frigos);
        }
    }


}
