package edu.poly.tousantigaspi.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import java.util.stream.Collectors;

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
    private NotificationManagerCompat notificationManagerCompat;
    private static final String CHANNEL_ID = "channelDefault";
    private static int NOTIF_ID = 123;


    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        model = activity.getFrigoModel();
        this.createNotificationChannel();
        this.notificationManagerCompat = NotificationManagerCompat.from(this.getContext());
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

            List<Product> pastProducts = frigoModel.getFrigos().get(currentPositionViewModel.getCurrentPosition().getValue()).getProducts().stream().filter(product -> product.isPast()).collect(Collectors.toList());

            currentPositionViewModel.getCurrentPosition().observe(getViewLifecycleOwner(), position ->{
                productListAdapter.refresh(frigoModel,position,true);
                frigoSpinner.setSelection(position);
                controller.modelHasChanged(model.getFrigos().get(position));

                List<Product> pastProductsRefresh = frigoModel.getFrigos().get(position).getProducts().stream().filter(product -> product.isPast()).collect(Collectors.toList());

                if(!pastProductsRefresh.isEmpty()){
                    sendNotifications(pastProductsRefresh);
                }
            });

            if(!pastProducts.isEmpty()){
                sendNotifications(pastProducts);
            }

            modelCreated = true;
        }
        else {
            adapter.refresh(frigoModel);
            productListAdapter.refresh(frigoModel,0,true);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            Context context = requireContext();
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private void sendNotifications(List<Product> products){
        for(Product product: products){
            Context context = requireContext();

            Notification notification = new NotificationCompat.Builder(context, "channelDefault")
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.burger))
                    .setContentTitle("Produit bientôt périmé")
                    .setContentText("Attention, votre "+ product.getName() + " se périmé dans "+ product.getDateRemaining() + " !")
                    .build();

            notificationManagerCompat.notify(++NOTIF_ID, notification);
        }
    }
}