package com.example.chatbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import java.util.List;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "ChatBotPrefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    
    private final SharedPreferences preferences;

    public SharedPreferencesUtil(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserName(String name) {
        preferences.edit().putString(KEY_USER_NAME, name).apply();
    }

    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, null);
    }

    public boolean isFirstLaunch() {
        boolean isFirst = preferences.getBoolean(KEY_FIRST_LAUNCH, true);
        if (isFirst) {
            preferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply();
        }
        return isFirst;
    }

    public void setDarkModeEnabled(boolean enabled){
        preferences.edit().putBoolean("dark_mode",enabled).apply();
    }

    public boolean isDarkModeEnabled(){
        return preferences.getBoolean("dark_mode",false);
    }



    public static void saveChats(Context context, List<ChatSession> chatList) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = gson.toJson(chatList);
        sharedPreferences.edit().putString("chats", json).apply();
    }
}
