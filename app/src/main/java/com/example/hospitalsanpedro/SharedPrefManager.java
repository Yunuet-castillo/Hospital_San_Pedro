package com.example.hospitalsanpedro;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // ✅ GUARDAR TOKEN
    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // ✅ OBTENER TOKEN
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // ✅ LIMPIAR SESIÓN
    public void clear() {
        editor.clear();
        editor.apply();
    }
}