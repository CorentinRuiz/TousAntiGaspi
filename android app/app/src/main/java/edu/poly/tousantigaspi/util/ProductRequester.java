package edu.poly.tousantigaspi.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProductRequester {

    private String url;
    private final Context context;
    private final RequestQueue queue;
    private JSONObject product;

    public ProductRequester(String barcode, Context context) {
        this.url =  "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
    }


    private void createHttpRequest(){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    assert responseBody != null;
                    JSONObject res = new JSONObject(responseBody.string());
                    setProductName(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            //System.out.println(response);
                            //System.out.println(obj);
                            setProductName(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        queue.add(stringRequest);*/
    }

    public JSONObject getProductName(){
        createHttpRequest();
        return product;
    }

    public void setProductName(JSONObject json) throws JSONException {
        product = json;
    }

}
