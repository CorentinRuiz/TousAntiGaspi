package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.CreateFrigoRequest;
import edu.poly.tousantigaspi.util.request.NameRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFirstFrigoActivity extends AppCompatActivity {

    TextView nameText;
    EditText editText;
    Button letsgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_frigo);

        nameText = findViewById(R.id.nameText);
        editText = findViewById(R.id.frigoInput);

        String name = getIntent().getStringExtra("name");
        nameText.setText(name);

        letsgo = findViewById(R.id.lets_go);

        letsgo.setOnClickListener(click ->{
           addFirstFrigo(editText.getText().toString());
        });
    }

    private void addFirstFrigo(String name){
        CreateFrigoRequest createFrigoRequest = new CreateFrigoRequest();

        createFrigoRequest.setUsername(UtilsSharedPreference.getStringFromPref(this,"username"));
        createFrigoRequest.setName(name);

        Call<String> getFrigoResponseCall = ApiClient.getFrigoService().createFrigo(createFrigoRequest);

        getFrigoResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    System.out.println(response.body());
                    openMain();
                } else {
                    System.out.println(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public void openMain(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);

    }
}