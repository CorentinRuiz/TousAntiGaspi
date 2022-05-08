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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import edu.poly.tousantigaspi.R;


public class SettingsFragment extends Fragment {
    private Button resetBtn;
    private Button shopOnlineBtn;
    private ActivityResultLauncher<Intent> shopOnlineResult;
    Spinner language;

    private NotificationManagerCompat notificationManagerCompat;
    private static final String CHANNEL_ID = "channelDefault";
    private static int NOTIF_ID = 123;



    public SettingsFragment() {

    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.createNotificationChannel();
        this.notificationManagerCompat = NotificationManagerCompat.from(this.getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetBtn = view.findViewById(R.id.reset);
        shopOnlineBtn = view.findViewById(R.id.shop_online);
        language = view.findViewById(R.id.languageTv);

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Locale locale = new Locale(language.getSelectedItem().toString());
                Locale.setDefault(locale);
                Resources resources = requireActivity().getResources();
                Configuration config = resources.getConfiguration();
                config.setLocale(locale);
                resources.updateConfiguration(config, resources.getDisplayMetrics());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resetBtn.setOnClickListener(view1 -> Toast.makeText(view.getContext(), getString(R.string.unavailable), Toast.LENGTH_LONG).show());

        shopOnlineBtn.setOnClickListener(view1 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.auchan.fr"));
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

        Button btnNotif = view.findViewById(R.id.buttonNotif);
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = requireContext();

                Notification notification = new NotificationCompat.Builder(context, "channelDefault")
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.burger))
                        .setContentTitle("Produit bientôt périmé")
                        .setContentText("Attention, votre hamburger se périme dans 3 jours !")
                        .build();

                notificationManagerCompat.notify(++NOTIF_ID, notification);
            }
        });
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}