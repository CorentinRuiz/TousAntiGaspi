package edu.poly.tousantigaspi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.BarCodeScannerActivity;
import edu.poly.tousantigaspi.controller.FrigoController;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.ProductWithoutPic;
import edu.poly.tousantigaspi.util.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.util.adapter.ProductListAdapter;
import edu.poly.tousantigaspi.util.factory.ProductFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    Spinner frigoSpinner;
    FrigoController controller;
    ListView productListView;
    ProductListAdapter adapter;
    private ArrayList<Product> products;

    public ListFragment() {

    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controller = new FrigoController(this);
        controller.loadData();

        products = new ArrayList<>();

        try {
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));
            products.add(new ProductFactory().build(ProductFactory.WITHOUT_PIC,1,"","20 jours","Viande haché"));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        adapter = new ProductListAdapter(requireContext(),products);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productListView = view.findViewById(R.id.list_item);
        adapter = new ProductListAdapter(requireContext(),products);
        productListView.setAdapter(adapter);

        view.findViewById(R.id.AddProduct).setOnClickListener(click ->{
            openPopUp(view);
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
            openAddProductPopUp(view);
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.AddProductBarCodeButton).setOnClickListener(click -> openBarCodeScanner());

    }

    public void openBarCodeScanner(){
        Intent openBarCodeScannerActivity = new Intent(requireContext(), BarCodeScannerActivity.class);
        startActivity(openBarCodeScannerActivity);
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
            controller.editFrigos(input.getText().toString(),frigo.getName());
            popupWindow.dismiss();
        });
    }

    public void openAddProductPopUp(View view){

        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_add_manually_product,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,1100,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,145,355);

    }

    public void notifiedForChangeUI(FrigoModel model){
        frigoSpinner = getView().findViewById(R.id.frigoSpinner);
        frigoSpinner.setAdapter(new FrigoAdapter(requireContext(), model));

        frigoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int postion, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        FrigoAdapter adapter = new FrigoAdapter(requireContext(),model);
        frigoSpinner.setAdapter(adapter);
    }
}