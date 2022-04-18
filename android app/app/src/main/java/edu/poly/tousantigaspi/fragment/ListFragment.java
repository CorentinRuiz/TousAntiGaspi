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
import android.widget.PopupWindow;

import java.util.Objects;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.BarCodeScannerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.AddProduct).setOnClickListener(click ->{
            openPopUp(view);
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

    public void openAddProductPopUp(View view){

        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_add_manually_product,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,1100,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,145,355);

    }
}