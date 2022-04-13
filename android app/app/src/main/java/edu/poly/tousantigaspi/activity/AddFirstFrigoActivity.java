package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class AddFirstFrigoActivity extends AppCompatActivity {

    TextView nameText;
    Button letsgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_frigo);

        nameText = findViewById(R.id.nameText);

        String name = getIntent().getStringExtra("name");
        nameText.setText(name);

        letsgo = findViewById(R.id.lets_go);

        letsgo.setOnClickListener(click ->{
            openMain();
        });
    }

    public void openMain(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
    }
}