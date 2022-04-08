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
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        //TODO Verify connection
        if(pref.getInt("nbAppStart",0) == 0){
            Intent openLoginActivity = new Intent(this, LoginRegisterActivity.class);
            startActivity(openLoginActivity);
        }else{
            Intent openMainActivity = new Intent(this, MainActivity.class);
            startActivity(openMainActivity);
        }
    }
}