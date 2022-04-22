package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView needLogin;
    Button registerButton;
    EditText eUsername, eEmail,ePassword, eCpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        needLogin = findViewById(R.id.needLogin);
        registerButton = findViewById(R.id.registerButton);
        eUsername = findViewById(R.id.usernameRegister);
        eEmail = findViewById(R.id.email);
        ePassword = findViewById(R.id.passwordRegister);
        eCpassword = findViewById(R.id.confirmPassword);

        needLogin.setOnClickListener(click -> {
            openLoginPage();
        });

        registerButton.setOnClickListener(click -> {
            if(TextUtils.isEmpty(eEmail.getText().toString()) || TextUtils.isEmpty(eUsername.getText().toString()) || TextUtils.isEmpty(ePassword.getText().toString())){

                String message = getString(R.string.all_input_required);
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
            else if(!TextUtils.equals(ePassword.getText(),eCpassword.getText())){
                String message = getString(R.string.passwords_not_same);
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }else{
                RegisterRequest request = new RegisterRequest();

                request.setEmail(eEmail.getText().toString());
                request.setUsername(eUsername.getText().toString());
                request.setPassword(ePassword.getText().toString());

                registerUser(request);
            }

        });

    }

    void openLoginPage(){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }

    public void registerUser(RegisterRequest registerRequest){
        Call<String> registerResponseCall = ApiClient.getUserService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body());
                    UtilsSharedPreference.pushStringToPref(getApplicationContext(),"username",registerRequest.getUsername());
                    openFirstConnectionPage();
                }else{
                    String message = getString(R.string.error);

                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    void openFirstConnectionPage(){
        startActivity(new Intent(RegisterActivity.this,FirstConnexionActivity.class));
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
    }
}