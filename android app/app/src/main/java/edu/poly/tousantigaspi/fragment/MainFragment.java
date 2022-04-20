package edu.poly.tousantigaspi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.controller.FrigoController;
import edu.poly.tousantigaspi.controller.NameController;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.model.NameModel;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.object.ProductWithoutPic;
import edu.poly.tousantigaspi.util.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.util.adapter.ProductListAdapter;
import edu.poly.tousantigaspi.util.factory.ProductFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static FrigoController controller;
    private static NameController nameController;

    private ListView productListView;
    private ProductListAdapter adapter;
    private ArrayList<Product> products;
    public Spinner frigoSpinner;
    public TextView welcome;


    public MainFragment() {

    }

    public static MainFragment newInstance(FrigoController frigoController) {
        controller = frigoController;
        MainFragment fragment = new MainFragment();
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
        nameController = new NameController(this);

        nameController.getName();
        controller.loadData();

        products = new ArrayList<Product>();

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

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListView = view.findViewById(R.id.list_item_main);
        welcome = view.findViewById(R.id.welcomeMainText);
        adapter = new ProductListAdapter(requireContext(),products);
        productListView.setAdapter(adapter);
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

    public void notifiedForChangeName(NameModel model) {
        String welcomeText = "Bonjour\n" + model.getName() + " !";
        welcome.setText(welcomeText);
    }
}