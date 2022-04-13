package edu.poly.tousantigaspi.util;

import edu.poly.tousantigaspi.util.request.LoginRequest;
import edu.poly.tousantigaspi.util.request.RegisterRequest;
import edu.poly.tousantigaspi.util.response.LoginResponse;
import edu.poly.tousantigaspi.util.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user/login")
    Call<String> loginUser(@Body LoginRequest loginRequest);

    @POST("/user/register")
    Call<String> registerUsers(@Body RegisterRequest registerRequest);

}
