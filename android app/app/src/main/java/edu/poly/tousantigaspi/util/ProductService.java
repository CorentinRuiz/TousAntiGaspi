package edu.poly.tousantigaspi.util;

import com.google.gson.JsonArray;

import edu.poly.tousantigaspi.util.request.AddProductRequest;
import edu.poly.tousantigaspi.util.request.GetProductsRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ProductService {

    @POST("/product/add")
    Call<String> addProduct(@Body AddProductRequest createFrigoRequest);

    @POST("/product/get")
    Call<JsonArray> getProduct(@Body GetProductsRequest id);

}
