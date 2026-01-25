    package com.example.hospitalsanpedro;

    import android.content.Context;
    import android.content.SharedPreferences;

    public class SharedPrefManager {

        private static final String SHARED_PREF_NAME = "hospital_pref";
        private static final String KEY_TOKEN = "token";
        private static final String KEY_SERVER_IP = "server_ip";
        private static final String KEY_BASE_URL = "base_url";

        // AGREGAR ESTAS CONSTANTES PARA MANEJAR EL LOGIN
        private static final String KEY_IS_LOGGED_IN = "is_logged_in";
        private static final String KEY_USER_ID = "user_id";
        private static final String KEY_USER_NAME = "user_name";
        private static final String KEY_USER_EMAIL = "user_email";
        private static final String KEY_USER_ROLE = "user_role";

        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;

        public SharedPrefManager(Context context) {
            sharedPreferences = context.getSharedPreferences(
                    SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
            );
            editor = sharedPreferences.edit();
        }

        public void saveToken(String token) {
            editor.putString(KEY_TOKEN, token);
            editor.apply();
        }

        public String getToken() {
            return sharedPreferences.getString(KEY_TOKEN, null);
        }

        public void saveServerIp(String ip) {
            editor.putString(KEY_SERVER_IP, ip);
            editor.apply();
        }

        public String getServerIp() {
            return sharedPreferences.getString(
                    KEY_SERVER_IP,
                    ""
            );
        }

        public void saveBaseUrl(String baseUrl) {
            editor.putString(KEY_BASE_URL, baseUrl);
            editor.apply();
        }

        public String getBaseUrl() {
            String ip = getServerIp();
            if (ip.isEmpty()) {
                return "";
            }
            return "http://" + ip + ":8000/";
        }

        // NUEVOS MÉTODOS PARA MANEJAR EL LOGIN
        public void setLoggedIn(boolean isLoggedIn) {
            editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
            editor.apply();
        }

        public boolean isLoggedIn() {
            return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
        }

        public void saveUserId(int userId) {
            editor.putInt(KEY_USER_ID, userId);
            editor.apply();
        }

        public int getUserId() {
            return sharedPreferences.getInt(KEY_USER_ID, -1);
        }

        public void saveUserName(String userName) {
            editor.putString(KEY_USER_NAME, userName);
            editor.apply();
        }

        public String getUserName() {
            return sharedPreferences.getString(KEY_USER_NAME, "");
        }

        public void saveUserEmail(String userEmail) {
            editor.putString(KEY_USER_EMAIL, userEmail);
            editor.apply();
        }

        public String getUserEmail() {
            return sharedPreferences.getString(KEY_USER_EMAIL, "");
        }

        public void saveUserRole(String userRole) {
            editor.putString(KEY_USER_ROLE, userRole);
            editor.apply();
        }

        public String getUserRole() {
            return sharedPreferences.getString(KEY_USER_ROLE, "");
        }

        public void logout() {
            // Limpiar solo datos de usuario, mantener configuración del servidor
            editor.remove(KEY_TOKEN);
            editor.remove(KEY_USER_ID);
            editor.remove(KEY_USER_NAME);
            editor.remove(KEY_USER_EMAIL);
            editor.remove(KEY_USER_ROLE);
            editor.putBoolean(KEY_IS_LOGGED_IN, false);
            editor.apply();
        }

        public void clear() {
            editor.clear();
            editor.apply();
        }
    }