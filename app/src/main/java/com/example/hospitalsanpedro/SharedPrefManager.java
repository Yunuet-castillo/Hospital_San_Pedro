package com.example.hospitalsanpedro;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "hospital_pref";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token){
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clear(){
        editor.clear();
        editor.apply();
    }
}
