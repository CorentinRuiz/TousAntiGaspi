package edu.poly.tousantigaspi.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import edu.poly.tousantigaspi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private Button resetBtn;
    private Button shopOnlineBtn;
    private ActivityResultLauncher<Intent> shopOnlineResult;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NotificationManagerCompat notificationManagerCompat;
    private static final String CHANNEL_ID = "channelDefault";
    private static int NOTIF_ID = 123;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        this.createNotificationChannel();
        this.notificationManagerCompat = NotificationManagerCompat.from(this.getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetBtn = view.findViewById(R.id.reset);
        shopOnlineBtn = view.findViewById(R.id.shop_online);

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