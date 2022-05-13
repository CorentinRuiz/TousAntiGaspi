package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.NameRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            addNameToAccount(submitName);
        });
    }

    private void setName(String name){
        submitName = name;
    }

    private void addNameToAccount(String name){

        NameRequest addNameRequest = new NameRequest();
        System.out.println(UtilsSharedPreference.getStringFromPref(this,"username"));
        addNameRequest.setUsername(UtilsSharedPreference.getStringFromPref(this,"username"));
        addNameRequest.setName(name);

        Call<String> getFrigoResponseCall = ApiClient.getUserService().addName(addNameRequest);

        getFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    System.out.println(response.body());
                    openFirstFrigoActivity(name);
                } else {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void openFirstFrigoActivity(String name) {
        Intent intent = new Intent(this, AddFirstFrigoActivity.class);
        intent.putExtra("name", name);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }

    @Override
    public void onBackPressed() { }

}