package edu.poly.tousantigaspi.util;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequester {

    private final static String BASE_URL="http://10.0.2.2:3000";
    private JSONObject response;
    private String url;
    private final Context context;
    private final RequestQueue queue;

    public HttpRequester(String url, Context context) {
        this.url = BASE_URL + url;
        this.context = context;
        this.queue = Volley.newRequestQueue(this.context);
    }

    public void createRequest(int method, Map<String,String> params){
        System.out.println(url);

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj = null;
                        System.out.println(response);
                        try {
                            obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setResponse(obj);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>(params);
                System.out.println(map);
                return map;
            };

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                System.out.println(mStatusCode);
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(stringRequest);
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }

    public JSONObject getResponse() {
        return response;
    }
}
