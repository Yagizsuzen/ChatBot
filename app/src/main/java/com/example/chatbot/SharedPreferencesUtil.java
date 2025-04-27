package com.example.chatbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import java.util.List;

public class SharedPreferencesUtil {
    public static void saveChats(Context context, List<ChatSession> chatList) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = gson.toJson(chatList);
        sharedPreferences.edit().putString("chats", json).apply();
    }
}
