package edu.poly.tousantigaspi.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.repositories.FrigoRepository;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.viewmodels.CurrentPositionViewModel;

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

    public void addFrigo(String frigoName){
        model.addFrigo(frigoName);
        repository.addFrigo(context,frigoName,UtilsSharedPreference.getStringFromPref(context,"username"),model);
    }

    public void addProduct(String frigoId, Product product, String date){
        model.addProduct(frigoId,product);
        repository.addProduct(product,frigoId,model,date);
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

    public void modelHasChanged(Frigo frigo){
        TextView tv = ((MainActivity) context).findViewById(R.id.expireSoon);
        View bar = ((MainActivity) context).findViewById(R.id.expireSoonBar);

        if(tv != null){
            if(model.getCurrentPastDlcProduct().get(frigo.getName()).isEmpty()){
                tv.setText(R.string.no_product_expired);
                tv.setTextColor(context.getResources().getColor(R.color.main_grey_text_color));
                bar.setBackground(ContextCompat.getDrawable(context, R.color.main_grey_text_color));
            }
            else{

                tv.setText(R.string.product_expired_soon);
                tv.setTextColor(context.getResources().getColor(R.color.red));
                bar.setBackground(ContextCompat.getDrawable(context, R.color.red));
            }
        }
    }



}
