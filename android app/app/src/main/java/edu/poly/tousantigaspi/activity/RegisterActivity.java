package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class RegisterActivity extends AppCompatActivity {

    TextView needLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        needLogin = findViewById(R.id.needLogin);

        needLogin.setOnClickListener(click -> {
            openLoginPage();
        });
    }

    void openLoginPage(){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }
}