package edu.poly.tousantigaspi.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.poly.tousantigaspi.util.request.CreateFrigoRequest;
import edu.poly.tousantigaspi.util.request.DeleteFrigoRequest;
import edu.poly.tousantigaspi.util.request.EditFrigoRequest;
import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface FrigoService {

    @POST("/frigo/create")
    Call<String> createFrigo(@Body CreateFrigoRequest createFrigoRequest);

    @POST("/frigo/get")
    Call<JsonArray> getFrigo(@Body GetRequestWithUsername username);

    @PUT("/frigo/edit")
    Call<String> editFrigo(@Body EditFrigoRequest username);

    @POST("/frigo/delete")
    Call<String> deleteFrigo(@Body DeleteFrigoRequest request);
}
