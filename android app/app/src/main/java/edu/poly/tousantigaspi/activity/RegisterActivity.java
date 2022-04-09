package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class RegisterActivity extends AppCompatActivity {

    TextView needLogin;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        needLogin = findViewById(R.id.needLogin);
        registerButton = findViewById(R.id.registerButton);

        needLogin.setOnClickListener(click -> {
            openLoginPage();
        });

        registerButton.setOnClickListener(click -> {
            openFirstConnectionPage();
        });

    }

    void openLoginPage(){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }

    void openFirstConnectionPage(){
        startActivity(new Intent(this,FirstConnexionActivity.class));
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
    }
}