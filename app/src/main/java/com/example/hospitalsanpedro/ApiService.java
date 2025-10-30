package com.example.hospitalsanpedro;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;
import java.util.Map;

public interface ApiService {
    @POST("register/")
    Call<ResponseBody> register(@Body Map<String, Object> body);

    @POST("login/")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @GET("citas/")
    Call<List<Cita>> getCitas(@Header("Authorization") String token,
                              @Query("date") String date);
    @POST("signos/")
    Call<ResponseBody> postSignos(@Header("Authorization") String token, @Body Map<String, Object> body);
}
