package edu.poly.tousantigaspi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.adapter.ProductListAdapter;
import edu.poly.tousantigaspi.controller.Controller;
import edu.poly.tousantigaspi.controller.NameController;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.model.NameModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.observer.FrigoObserver;
import edu.poly.tousantigaspi.viewmodels.CurrentPositionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements FrigoObserver {

    private static NameController nameController;
    private Controller controller;
    private ListView productListView;
    private FrigoAdapter adapter;
    CurrentPositionViewModel currentPositionViewModel;

    public Spinner frigoSpinner;
    public TextView welcome;
    private ProductListAdapter productListAdapter;
    private boolean modelCreated = false;
    FrigoModel model;


    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        model = activity.getFrigoModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        nameController = new NameController(this);
        nameController.getName();
        MainActivity activity = (MainActivity) getActivity();

        model = activity.getFrigoModel();
        controller = activity.getController();

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model.addObs(this);
        model.loadFrigo(UtilsSharedPreference.getStringFromPref(requireContext(),"username"));

        currentPositionViewModel = new ViewModelProvider(requireActivity()).get(CurrentPositionViewModel.class);
        frigoSpinner = view.findViewById(R.id.frigoSpinner);
        productListView = view.findViewById(R.id.list_item_main);

        frigoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long arg3) {
                currentPositionViewModel.setCurrentPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        welcome = view.findViewById(R.id.welcomeMainText);
    }

    public void notifiedForChangeName(NameModel model) {
        String welcomeText = getString(R.string.hello) + "\n" + model.getName() + " !";
        welcome.setText(welcomeText);
    }

    @Override
    public void update(FrigoModel frigoModel) {
        frigoSpinner = getView().findViewById(R.id.frigoSpinner);
        productListView = getView().findViewById(R.id.list_item_main);

        if (!modelCreated) {
            adapter = new FrigoAdapter(requireContext(),frigoModel.getFrigos());
            frigoSpinner.setAdapter(adapter);

            productListAdapter = new ProductListAdapter(requireContext(),frigoModel.getCurrentPastDlcProduct().get(frigoModel.getFrigos().get(currentPositionViewModel.getCurrentPosition().getValue()).getName()));
            productListView.setAdapter(productListAdapter);

            currentPositionViewModel.getCurrentPosition().observe(getViewLifecycleOwner(), position ->{
                productListAdapter.refresh(frigoModel,position,true);
                frigoSpinner.setSelection(position);
                controller.modelHasChanged(model.getFrigos().get(position));

               /* List<Product> currentPastProduct = model.getCurrentPastDlcProduct().get(model.getFrigos().get(position));

                if(currentPastProduct != null && currentPastProduct.isEmpty()){
                   TextView tv = getView().findViewById(R.id.expireSoon);
                   tv.setTextColor(getResources().getColor(R.color.main_grey_text_color));
                   tv.setText(getResources().getString(R.string.no_product_expires_soon));
                    getView().findViewById(R.id.expireSoonBar).setBackground(ContextCompat.getDrawable(requireContext(), R.color.main_grey_text_color));
                }*/
            });

            modelCreated = true;
        }
        else {
            adapter.refresh(frigoModel);
            productListAdapter.refresh(frigoModel,0,true);
        }
    }
}