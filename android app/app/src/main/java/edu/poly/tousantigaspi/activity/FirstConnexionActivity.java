package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;

public class FirstConnexionActivity extends AppCompatActivity {

    EditText nameInput;
    String submitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_connexion);

        nameInput = findViewById(R.id.nameInput);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               setName(s.toString());
            }
        });


        findViewById(R.id.nextButton).setOnClickListener(click -> {
            openFirstFrigoActivity(submitName);
        });
    }

    private void setName(String name){
        submitName = name;
    }

    private void openFirstFrigoActivity(String name){
        Intent intent = new Intent(this,AddFirstFrigoActivity.class);
        intent.putExtra("name",name);
        System.out.println(name);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
    }

}