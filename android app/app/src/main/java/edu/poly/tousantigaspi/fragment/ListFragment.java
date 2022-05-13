package edu.poly.tousantigaspi.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.BarCodeScannerActivity;
import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.adapter.FrigoAdapter;
import edu.poly.tousantigaspi.adapter.ProductListAdapter;
import edu.poly.tousantigaspi.controller.Controller;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.CodeScannerProduct;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.util.DateCalculator;
import edu.poly.tousantigaspi.util.MyNavigationService;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.factory.AbstractFactory;
import edu.poly.tousantigaspi.util.factory.FactoryProvider;
import edu.poly.tousantigaspi.util.factory.ProductFactory;
import edu.poly.tousantigaspi.util.observer.FrigoObserver;
import edu.poly.tousantigaspi.viewmodels.CurrentPositionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements FrigoObserver, LocationListener {

    private static final int LOCATION_REQUEST_CODE = 51; //PASTIS

    Spinner frigoSpinner;
    FrigoAdapter adapter;
    ListView productListView;
    Controller controller;
    CurrentPositionViewModel currentPositionViewModel;

    EditText currentEditTextFrigo;

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
        controller = activity.getController();

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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentPositionViewModel = new ViewModelProvider(requireActivity()).get(CurrentPositionViewModel.class);

        model.addObs(this);
        model.loadFrigo(requireContext(),UtilsSharedPreference.getStringFromPref(requireContext(),"username"));

        frigoSpinner = view.findViewById(R.id.frigoSpinner);
        productListView = view.findViewById(R.id.list_item);

        view.findViewById(R.id.AddProduct).setOnClickListener(click ->{
            openPopUp(view);
        });

        frigoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long arg3) {
                currentPositionViewModel.setCurrentPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        view.findViewById(R.id.frigoSettings).setOnClickListener(click ->{
            openSettingsPopUp(view);
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

    public void openBarCodeScanner() {
        Intent openBarCodeScannerActivity = new Intent(requireContext(), BarCodeScannerActivity.class);
        someActivityResultLauncher.launch(openBarCodeScannerActivity);
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
        if(product != null){
            name.setText(product.getName());
            spinner.setSelection(product.getQuantity());
        }

        viewPopupWindow.findViewById(R.id.submitProduct).setOnClickListener(click -> {
            Spinner quantity = viewPopupWindow.findViewById(R.id.quantityTv);
            EditText date = viewPopupWindow.findViewById(R.id.dateTv);

            String dateString = date.getText().toString();
            dateString = dateString.replace("/","-");
            String dateFormatForApi = dateString;
            dateString = new DateCalculator().calculateDaysRemaining(requireContext(),dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            AbstractFactory<Product> factory = FactoryProvider.getFactory(FactoryProvider.PRODUCT);
            Product newProduct = factory.build(ProductFactory.MANUALLY);
            newProduct.setId("");
            newProduct.setName(name.getText().toString());
            newProduct.setDateRemaining(dateString);
            newProduct.setQuantity(Integer.parseInt(quantity.getSelectedItem().toString()));

            controller.addProduct(selected.getId(), newProduct, dateFormatForApi);
            popupWindow.dismiss();
        });

    }

    @Override
    public void update(FrigoModel frigoModel) {
        frigoSpinner = getView().findViewById(R.id.frigoSpinner);

        if (!modelCreated) {
            System.out.println("load data");
            adapter = new FrigoAdapter(requireContext(),frigoModel.getFrigos());
            productListAdapter = new ProductListAdapter(requireContext(),frigoModel.getFrigos().get(0).getProducts());

            currentPositionViewModel.getCurrentPosition().observe(getViewLifecycleOwner(), position ->{
                productListAdapter.refresh(model,position,false);
                controller.modelHasChanged(model.getFrigo(position));
                frigoSpinner.setSelection(position);
            });

            frigoSpinner.setAdapter(adapter);
            productListView.setAdapter(productListAdapter);
            modelCreated = true;
        }
        else {
            adapter.refresh(model);
            productListAdapter.refresh(model,currentPositionViewModel.getCurrentPosition().getValue(),false);
        }
    }

    public void openSettingsPopUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_settings_product,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,670,240,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.TOP,130,300);

        viewPopupWindow.findViewById(R.id.EditFrigoButton).setOnClickListener(click -> {
            openUpdateFrigoPopUp(view);
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.AddFrigoButton).setOnClickListener(click -> {
            openAddFrigoPopUp(view);
            popupWindow.dismiss();
        });
    }

    public void openUpdateFrigoPopUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.pop_up_modify_fridge,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,800,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        EditText input = viewPopupWindow.findViewById(R.id.frigoEditInput);

        Frigo frigo = (Frigo) frigoSpinner.getSelectedItem();
        input.setText(frigo.getName());
        this.currentEditTextFrigo = input;

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);

        viewPopupWindow.findViewById(R.id.submitEditFrigo).setOnClickListener(click ->{
            controller.editFrigo(input.getText().toString(),frigo.getId());
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.deleteFrigo).setOnClickListener(click ->{
            frigoSpinner.setSelection(0);
            controller.deleteFrigo(frigo.getId());
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.useLocationEditFrigoButton).setOnClickListener(click -> {
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            else{
                requestLocation();
            }
        });

        popupWindow.setOnDismissListener(() -> this.currentEditTextFrigo = null);

    }

    public void openAddFrigoPopUp(View view){
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.pop_up_add_fridge,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,800,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        EditText input = viewPopupWindow.findViewById(R.id.frigoAddInput);
        input.setText("");
        this.currentEditTextFrigo = input;

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);

        viewPopupWindow.findViewById(R.id.submitAddFrigo).setOnClickListener(click -> {
            controller.addFrigo(input.getText().toString());
            popupWindow.dismiss();
        });

        viewPopupWindow.findViewById(R.id.useLocationAddFrigoButton).setOnClickListener(click -> {
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            else{
                requestLocation();
            }
        });

        popupWindow.setOnDismissListener(() -> this.currentEditTextFrigo = null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.length == 0 || (grantResults[0] != PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this.getContext(), getString(R.string.location_permission_refused), Toast.LENGTH_LONG).show();
            }
            else {
                requestLocation();
            }
        }
    }

    public void requestLocation(){
        MyNavigationService.getLocation(this.getContext(), this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(currentEditTextFrigo != null){
            Geocoder geocoder = new Geocoder(this.getContext());
            try {
                this.currentEditTextFrigo.setText(geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}