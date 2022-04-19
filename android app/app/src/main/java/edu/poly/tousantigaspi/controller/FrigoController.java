package edu.poly.tousantigaspi.controller;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.fragment.ListFragment;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.util.ApiClient;
import edu.poly.tousantigaspi.util.UtilsSharedPreference;
import edu.poly.tousantigaspi.util.request.EditFrigoRequest;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FrigoController {

    public FrigoModel model;
    GetRequestWithUsername getRequestWithUsername;
    Fragment view;

    public interface GetDataCallback {
        void onGetMapData(JsonObject json);
        void onError(String message);
    }

    public FrigoController(MainFragment fragment) {
        this.view = fragment;
    }

    public FrigoController(ListFragment fragment) {
        this.view = fragment;
    }

    public void addFrigo(String name){
        if (!name.equals("")) {
            Frigo newFrigo = new Frigo(name,new ArrayList<Product>());
            model.addFrigo(newFrigo);
        }
    }

    public void setModel(FrigoModel model) {
        this.model = model;
    }


    private void getFrigos(final String username, final GetDataCallback getDataCallback){
        getRequestWithUsername = new GetRequestWithUsername();
        getRequestWithUsername.setUsername(username);

        Call<JsonObject> getFrigoResponseCall = ApiClient.getFrigoService().getFrigo(getRequestWithUsername);


        getFrigoResponseCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    getDataCallback.onGetMapData(response.body());
                } else {
                    getDataCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getDataCallback.onError(t.getMessage());
            }
        });
    }

    public void loadData() {
        getFrigos(UtilsSharedPreference.getStringFromPref(view.getContext(), "username"), new GetDataCallback() {
            @Override
            public void onGetMapData(JsonObject json) {
                JsonArray frigo_array = json.getAsJsonArray("frigo");
                List<Frigo> frigos = new ArrayList<>();
                frigo_array.forEach(x -> frigos.add(new Frigo(x.getAsJsonObject().get("name").toString().replace("\"", ""), new ArrayList<Product>())));
                setModel(new FrigoModel(frigos));

                if (view instanceof MainFragment) {
                    ((MainFragment) view).notifiedForChangeUI(model);
                } else if (view instanceof ListFragment) {
                    ((ListFragment) view).notifiedForChangeUI(model);
                }
            }

            @Override
            public void onError(String message) {
                System.out.println(message);
            }
        });
    }

        public void editFrigos(String newName, String frigoName){
            EditFrigoRequest editFrigoRequest = new EditFrigoRequest();
            editFrigoRequest.setUsername(UtilsSharedPreference.getStringFromPref(view.getContext(), "username"));

            editFrigoRequest.setFrigoName(frigoName);
            editFrigoRequest.setName(newName);

            Call<String> request = ApiClient.getFrigoService().editFrigo(editFrigoRequest);


            request.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        loadData();
                    } else {
                        System.out.println(response.message().toString());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }

}
