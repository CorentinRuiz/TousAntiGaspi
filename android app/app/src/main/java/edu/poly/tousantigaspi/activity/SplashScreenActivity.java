package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.poly.tousantigaspi.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        redirectionToActivity();
    }

    private void redirectionToActivity(){
        Intent openLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(openLoginActivity);
    }
}