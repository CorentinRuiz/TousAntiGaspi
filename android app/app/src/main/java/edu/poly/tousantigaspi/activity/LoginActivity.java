
package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView needRegister;
    EditText username;

    EditText password;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        needRegister = findViewById(R.id.needRegister);
        connect = findViewById(R.id.connect);

        needRegister.setOnClickListener(click -> {
            openRegisterPage();
        });

        connect.setOnClickListener(click ->{
           LoginRequest loginRequest = new LoginRequest();

           loginRequest.setPassword(password.getText().toString());
           loginRequest.setUsername(username.getText().toString());
           loginUser(loginRequest);
        });

        needRegister.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void loginUser(LoginRequest loginRequest){
        Call<String> loginResponseCall = ApiClient.getUserService().loginUser(loginRequest);

        loginResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    UtilsSharedPreference.pushStringToPref(getApplicationContext(),"username",loginRequest.getUsername());
                    openMainActivity();
                }else if(response.code() == 404) {
                    Toast.makeText(LoginActivity.this, getString(R.string.user_not_found), Toast.LENGTH_LONG).show();
                }
                else if(response.code() == 401){
                    Toast.makeText(LoginActivity.this,getString(R.string.wrong_password),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,getString(R.string.error),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    void openRegisterPage(){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
    }

    void openMainActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }

    @Override
    public void onBackPressed() { }

}