package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class AddFirstFrigoActivity extends AppCompatActivity {

    TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_frigo);

        nameText = findViewById(R.id.nameText);
        String name = getIntent().getStringExtra("name");
        System.out.println(name);

        nameText.setText(name);
    }

}