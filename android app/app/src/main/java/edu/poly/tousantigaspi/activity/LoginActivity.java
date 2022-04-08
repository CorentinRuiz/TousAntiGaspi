
package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class LoginActivity extends AppCompatActivity {

    TextView needRegister;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        needRegister = findViewById(R.id.needRegister);
        connect = findViewById(R.id.connect);

        needRegister.setOnClickListener(click -> {
            openRegisterPage();
        });

        connect.setOnClickListener(click ->{
            connectToApp();
        });

        needRegister.setMovementMethod(LinkMovementMethod.getInstance());

    }

    void openRegisterPage(){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
    }

    void connectToApp(){
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }

}