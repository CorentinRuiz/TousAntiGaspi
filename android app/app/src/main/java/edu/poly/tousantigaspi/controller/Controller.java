package edu.poly.tousantigaspi.controller;

import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.repositories.FrigoRepository;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;

public class Controller {

    FrigoRepository repository;
    FrigoModel model;

    public Controller(FrigoModel model) {
        this.repository = FrigoRepository.getInstance();
        this.model = model;
    }

    public void editFrigo(String newName, String id){
        model.editFrigo(id,newName);
        repository.editFrigo(newName,id,model);
    }

    public void addProduct(String frigoId, Product product){
        model.addProduct(frigoId,product);
        repository.addProduct(product,frigoId,model);
    }
    public void deleteFrigo(String id){
        model.deleteFrigo(id);
        //repository.editFrigo(newName,id,model);
    }



}
