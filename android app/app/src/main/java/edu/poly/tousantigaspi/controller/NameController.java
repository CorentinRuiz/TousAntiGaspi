package edu.poly.tousantigaspi.controller;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.model.NameModel;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameController {

    Fragment view;
    GetRequestWithUsername getRequestWithUsername;
    NameModel model;

    public void setModel(NameModel model) {
        this.model = model;
    }

    public NameController(Fragment view) {
        this.view = view;
    }

    public void getName(){
        getRequestWithUsername = new GetRequestWithUsername();
        getRequestWithUsername.setUsername(UtilsSharedPreference.getStringFromPref(view.getContext(),"username"));

        Call<JsonObject> getFrigoResponseCall = ApiClient.getUserService().getName(getRequestWithUsername);


        getFrigoResponseCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    setModel(new NameModel(response.body().get("name").toString().replace("\"","")));

                   if (view instanceof MainFragment) {
                        ((MainFragment) view).notifiedForChangeName(model);
                    }
                }
                else {
                    System.out.println(response.message().toString());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
