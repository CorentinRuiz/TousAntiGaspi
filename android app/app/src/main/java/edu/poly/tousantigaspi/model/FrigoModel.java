package edu.poly.tousantigaspi.model;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import edu.poly.tousantigaspi.controller.Controller;
import edu.poly.tousantigaspi.fragment.ListFragment;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.ManuallyProduct;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.repositories.FrigoRepository;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.factory.AbstractFactory;
import edu.poly.tousantigaspi.util.factory.FactoryProvider;
import edu.poly.tousantigaspi.util.factory.FrigoFactory;
import edu.poly.tousantigaspi.util.observer.FrigoObservable;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrigoModel extends FrigoObservable {

    private FrigoRepository repository;
    private ArrayList<Frigo> frigos;
    private HashMap<String,List<Product>> currentPastDlcProduct;
    private Controller controller;

    public FrigoModel() {
        repository = FrigoRepository.getInstance();
        frigos = new ArrayList<>();
        currentPastDlcProduct = new HashMap<>();
    }

    public List<Frigo> getFrigos() {
        return frigos;
    }

    public void addFrigo(String frigoName){
        AbstractFactory<Frigo> factory = FactoryProvider.getFactory(FactoryProvider.FRIGO);
        Frigo newFrigo = factory.build(FrigoFactory.BASIC);
        newFrigo.setId("");
        newFrigo.setName(frigoName);
        newFrigo.setProducts(new ArrayList<>());
        frigos.add(newFrigo);
        notifyObs(this);
    }

    public void editFrigo(String id,String newName){
        currentPastDlcProduct.put(newName, currentPastDlcProduct.remove(this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().getName()));
        this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().setName(newName);
    }

    public void loadFrigo(String username){
       repository.getFrigos(username,this);
    }

    public void loadPastDLCProduct(){
        for(Frigo frigo : this.frigos){
            List<Product> products = frigo.getProducts().stream().filter(Product::isPast).collect(Collectors.toList());
            currentPastDlcProduct.put(frigo.getName(),products);
        }
        controller.modelHasChanged(frigos.get(0));
    }

    public void setFrigos(ArrayList<Frigo> frigos) {
        this.frigos = frigos;
    }

    public Frigo getFrigo(int position){
       return frigos.get(position);
    }

    public HashMap<String, List<Product>> getCurrentPastDlcProduct() {
        return currentPastDlcProduct;
    }

    public void addProduct(String id,Product product){
        if(product.isPast()){
            this.currentPastDlcProduct.get(this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().getName()).add(product);
        }
        this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().getProducts().add(product);
        notifyObs(this);
    }

    public void deleteFrigo(String id){
        currentPastDlcProduct.remove(this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get().getName());
        this.frigos.remove(this.frigos.stream().filter(x -> x.getId().equals(id)).findFirst().get());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
