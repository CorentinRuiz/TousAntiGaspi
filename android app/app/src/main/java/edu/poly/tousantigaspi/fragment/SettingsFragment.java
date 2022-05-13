package edu.poly.tousantigaspi.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.activity.LoginActivity;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;


public class SettingsFragment extends Fragment {
    private Button resetBtn;
    private Button shopOnlineBtn;
    private ActivityResultLauncher<Intent> shopOnlineResult;
    private Spinner shopSelected;
    private Spinner dlcLimit;
    private SwitchCompat notifySwitch;

    private Map<String,String> allShop;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allShop = new HashMap<>();
        allShop.put("Auchan","https://www.auchan.fr");
        allShop.put("Carrefour","https://www.carrefour.fr/");
        allShop.put("Leclerc","https://www.leclercdrive.fr/");
        allShop.put("Casino","https://www.casino.fr/prehome/courses-en-ligne/accueil");
        allShop.put("Système U","https://www.magasins-u.com/accueil");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetBtn = view.findViewById(R.id.reset);
        shopOnlineBtn = view.findViewById(R.id.shop_online);
        shopSelected = view.findViewById(R.id.ChooseShop);
        dlcLimit = view.findViewById(R.id.DLCLimit);
        notifySwitch = view.findViewById(R.id.notifySwitch);

        notifySwitch.setChecked(UtilsSharedPreference.getBooleanFromPref(requireContext(),"notifyStart",true));

        dlcLimit.setSelection(UtilsSharedPreference.getIntFromPref(requireContext(),"DLClimit",5)-1);

        resetBtn.setOnClickListener(click -> {
            startActivity(new Intent(requireContext(),LoginActivity.class));
            UtilsSharedPreference.pushStringToPref(requireContext(),"username","");
        });


        dlcLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int days = Integer.parseInt(dlcLimit.getSelectedItem().toString().split(" ")[0]);

                UtilsSharedPreference.pushIntToPref(requireContext(),"DLClimit",days);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UtilsSharedPreference.pushBooleanToPref(requireContext(),"notifyStart",isChecked);
            }
        });


        shopOnlineBtn.setOnClickListener(view1 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(allShop.get(shopSelected.getSelectedItem().toString())));
            shopOnlineResult.launch(browserIntent);
        });

        shopOnlineResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_CANCELED || result.getResultCode() == Activity.RESULT_OK) {
                        Toast.makeText(view.getContext(), getString(R.string.you_wont_waste), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}