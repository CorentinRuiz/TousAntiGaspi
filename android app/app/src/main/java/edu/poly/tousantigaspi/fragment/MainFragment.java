package edu.poly.tousantigaspi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.controller.NameController;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.model.NameModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.observer.FrigoObserver;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements FrigoObserver {

    private static NameController nameController;
    private ListView productListView;
    private FrigoAdapter adapter;
    private ArrayList<Product> products;
    public Spinner frigoSpinner;
    public TextView welcome;
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


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model.addObs(this);
        model.loadFrigo(UtilsSharedPreference.getStringFromPref(requireContext(),"username"));

        frigoSpinner = view.findViewById(R.id.frigoSpinner);
        productListView = view.findViewById(R.id.list_item_main);
        welcome = view.findViewById(R.id.welcomeMainText);
    }

    public void notifiedForChangeName(NameModel model) {
        String welcomeText = getString(R.string.hello) + "\n" + model.getName() + " !";
        welcome.setText(welcomeText);
    }

    @Override
    public void update(List<Frigo> frigos) {
        frigoSpinner = getView().findViewById(R.id.frigoSpinner);
        if (!modelCreated) {
            System.out.println("load data");
            adapter = new FrigoAdapter(requireContext(),frigos);
            frigoSpinner.setAdapter(adapter);
            modelCreated = true;
        }
        else {
            adapter.refresh(model);
        }
    }
}