package edu.poly.tousantigaspi.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.repositories.FrigoRepository;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;

public class Controller {

    FrigoRepository repository;
    FrigoModel model;
    Context context;

    public Controller(FrigoModel model,Context context) {
        this.repository = FrigoRepository.getInstance();
        this.model = model;
        this.context = context;
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
        if(model.getFrigos().size() > 1){
            model.deleteFrigo(id);
            repository.deleteFrigo(id);
        }
        else{
            Toast.makeText(context,"You can't delete your last fridge",Toast.LENGTH_SHORT).show();
        }
    }

    public void modelHasChanged(){
        if(model.getCurrentPastDlcProduct().size() > 0){
            TextView tv = ((MainActivity) context).findViewById(R.id.expireSoon);

            View bar = ((MainActivity) context).findViewById(R.id.expireSoonBar);

            if(tv != null){
                tv.setText("Product expired or expire soon  ⚠");
                tv.setTextColor(context.getResources().getColor(R.color.red));
                bar.setBackground(ContextCompat.getDrawable(context, R.color.red));
            }
        }
    }



}
