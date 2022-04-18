package edu.poly.tousantigaspi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.util.ProductListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private ListView productListView;
    private ProductListAdapter adapter;
    private ArrayList<Product> products;


    public MainFragment() {

    }

    public static MainFragment newInstance(String param1, String param2) {
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
        products = new ArrayList<Product>();
        products.add(new Product("Viande Haché","2 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));
        products.add(new Product("Eau pétillante", "40 jours"));

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListView = view.findViewById(R.id.list_item_main);

        adapter = new ProductListAdapter(requireContext(),products);

        productListView.setAdapter(adapter);

    }
}