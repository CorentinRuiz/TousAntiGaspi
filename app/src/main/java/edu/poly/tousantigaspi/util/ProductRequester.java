package edu.poly.tousantigaspi.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductRequester {

    private String url;
    private final Context context;
    private final RequestQueue queue;
    private String productName;

    public ProductRequester(String barcode, Context context) {
        this.url =  "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
    }

    private void createHttpRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
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


        queue.add(stringRequest);
    }

    public String getProductName(){
        createHttpRequest();
        return productName;
    }

    public void setProductName(JSONObject json) throws JSONException {
        if(json == null){
            productName = "";
        }

        assert json != null;
        productName = json.getJSONObject("product").getString("product_name");
    }

}
