package edu.poly.tousantigaspi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.MyNavigationService;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.CreateFrigoRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFirstFrigoActivity extends AppCompatActivity implements LocationListener {

    public static int LOCATION_REQUEST_CODE = 69;

    TextView nameText;
    EditText editText;
    Button letsgo;
    ImageButton useLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_frigo);

        nameText = findViewById(R.id.nameText);
        editText = findViewById(R.id.frigoInput);

        String name = getIntent().getStringExtra("name");
        nameText.setText(name);

        useLocation = findViewById(R.id.useLocationFirstFrigoButton);

        useLocation.setOnClickListener(view -> {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            else{
                requestLocation();
            }
        });

        letsgo = findViewById(R.id.lets_go);

        letsgo.setOnClickListener(click ->{
           addFirstFrigo(editText.getText().toString());
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.length == 0 || (grantResults[0] != PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, getString(R.string.location_permission_refused), Toast.LENGTH_LONG).show();
            }
            else {
                requestLocation();
            }
        }
    }

    public void requestLocation(){
        MyNavigationService.getLocation(this, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(this);
        try {
            this.editText.setText(geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        for(Location l : locations){
            this.onLocationChanged(l);
        }
    }

    private void addFirstFrigo(String name){
        CreateFrigoRequest createFrigoRequest = new CreateFrigoRequest();

        createFrigoRequest.setUsername(UtilsSharedPreference.getStringFromPref(this,"username"));
        createFrigoRequest.setName(name);

        Call<String> getFrigoResponseCall = ApiClient.getFrigoService().createFrigo(createFrigoRequest);

        getFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    System.out.println(response.body());
                    openMain();
                } else {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public void openMain(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);

    }

    @Override
    public void onBackPressed() { }
}