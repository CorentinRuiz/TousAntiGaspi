package edu.poly.tousantigaspi.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.BarCodeScannerActivity;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.controller.Controller;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.CodeScannerProduct;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.adapter.ProductListAdapter;
import edu.poly.tousantigaspi.object.ManuallyProduct;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.util.DateCalculator;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.observer.FrigoObserver;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements FrigoObserver {

    Spinner frigoSpinner;
    FrigoAdapter adapter;
    ListView productListView;
    Controller controller;

    int currentPosition;
    private boolean modelCreated = false;
    ProductListAdapter productListAdapter;
    FrigoModel model;
    ActivityResultLauncher<Intent> someActivityResultLauncher;


    public ListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        model = activity.getFrigoModel();

         someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();

                            openAddProductPopUp(getView(),data.getExtras().getParcelable("productScanner"));
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new FrigoModel();
        controller = new Controller(model);
        currentPosition = 0;


        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model.addObs(this);
        model.loadFrigo(UtilsSharedPreference.getStringFromPref(requireContext(),"username"));

        frigoSpinner = view.findViewById(R.id.frigoSpinner);
        productListView = view.findViewById(R.id.list_item);

        view.findViewById(R.id.AddProduct).setOnClickListener(click ->{
            openPopUp(view);
        });

        frigoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int postion, long arg3) {
                productListAdapter.refresh(model,postion);
                currentPosition = postion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        view.findViewById(R.id.frigoSettings).setOnClickListener(click ->{
            openUpdateFrigoPopUp(view);
        });


    }

    public void openPopUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_add_product,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,670,240,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,130,400);

        viewPopupWindow.findViewById(R.id.AddProductButton).setOnClickListener(click -> {
            openAddProductPopUp(view,null);
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.AddProductBarCodeButton).setOnClickListener(click -> openBarCodeScanner());
    }

    public void openBarCodeScanner(){
        Intent openBarCodeScannerActivity = new Intent(requireContext(), BarCodeScannerActivity.class);
        someActivityResultLauncher.launch(openBarCodeScannerActivity);
    }

    public void openUpdateFrigoPopUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.pop_up_modify_fridge,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,600,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        EditText input = viewPopupWindow.findViewById(R.id.frigoEditInput);

        Frigo frigo = (Frigo) frigoSpinner.getSelectedItem();
        input.setText(frigo.getName());

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);

        viewPopupWindow.findViewById(R.id.submitEditFrigo).setOnClickListener(click ->{
            controller.editFrigo(input.getText().toString(),frigo.getId());
            popupWindow.dismiss();
        });
    }

    public void openAddProductPopUp(View view, CodeScannerProduct product){

        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_add_manually_product,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,1100,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,145,355);

        Spinner spinner = (Spinner) view.findViewById(R.id.frigoSpinner);
        Frigo selected = (Frigo) spinner.getSelectedItem();

        EditText name = viewPopupWindow.findViewById(R.id.productNameTv);
        System.out.println("PRODUCT:" + product.getName());
        if(product != null){
            name.setText(product.getName());
        }

        viewPopupWindow.findViewById(R.id.submitProduct).setOnClickListener(click -> {
            Spinner quantity = viewPopupWindow.findViewById(R.id.quantityTv);
            EditText date = viewPopupWindow.findViewById(R.id.dateTv);

            String dateString = date.getText().toString();
            dateString = dateString.replace("/","-");
            dateString = new StringBuilder(dateString).reverse().toString();
            dateString = new DateCalculator().calculateDaysRemaining(dateString);

            ManuallyProduct manuallyProduct = new ManuallyProduct("",name.getText().toString(),dateString,Integer.parseInt(quantity.getSelectedItem().toString()));

            controller.addProduct(selected.getId(), manuallyProduct);
            popupWindow.dismiss();
        });

    }

    @Override
    public void update(List<Frigo> frigos) {
        frigoSpinner = getView().findViewById(R.id.frigoSpinner);

        if (!modelCreated) {
            System.out.println("load data");
            adapter = new FrigoAdapter(requireContext(),frigos);
            productListAdapter = new ProductListAdapter(requireContext(),frigos.get(0).getProducts());
            frigoSpinner.setAdapter(adapter);
            productListView.setAdapter(productListAdapter);
            modelCreated = true;
        }
        else {
            adapter.refresh(model);
            productListAdapter.refresh(model,currentPosition);
        }
    }

}