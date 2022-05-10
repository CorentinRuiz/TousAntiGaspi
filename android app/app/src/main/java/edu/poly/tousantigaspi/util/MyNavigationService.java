package edu.poly.tousantigaspi.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import edu.poly.tousantigaspi.R;

public class MyNavigationService {

    public static void getLocation(Context context, LocationListener locationListener){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, R.string.permission_not_granted, Toast.LENGTH_LONG).show();
            return;
        }


        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        }
        else{
            Toast.makeText(context, R.string.gps_not_enabled, Toast.LENGTH_LONG).show();
        }
    }
}
