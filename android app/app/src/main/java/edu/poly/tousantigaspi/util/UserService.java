package edu.poly.tousantigaspi.util;

import com.google.gson.JsonObject;

import edu.poly.tousantigaspi.util.request.GetRequestWithUsername;
import edu.poly.tousantigaspi.util.request.LoginRequest;
import edu.poly.tousantigaspi.util.request.NameRequest;
import edu.poly.tousantigaspi.util.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user/login")
    Call<String> loginUser(@Body LoginRequest loginRequest);

    @POST("/user/register")
    Call<String> registerUsers(@Body RegisterRequest registerRequest);

    @POST("/user/add/name")
    Call<String> addName(@Body NameRequest nameRequest);

    @POST("/user/get/name")
    Call<JsonObject> getName(@Body GetRequestWithUsername nameRequest);

}
