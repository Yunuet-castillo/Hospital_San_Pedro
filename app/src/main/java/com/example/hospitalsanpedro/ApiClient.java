package com.example.hospitalsanpedro;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit(Context context) {
        SharedPrefManager sp = new SharedPrefManager(context);
        String baseUrl = sp.getBaseUrl();

        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalStateException("La IP del servidor no está configurada");
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl + "api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(Context context) {
        return getRetrofit(context).create(ApiService.class);
    }

    // 🔄 Llamar cuando cambie la IP
    public static void reset() {
        retrofit = null;
    }
}
