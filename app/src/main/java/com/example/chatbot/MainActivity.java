package com.example.chatbot;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.chatbot.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Gson gson = new Gson();
    String SHARED_PREF_KEY = "chat_data";

    List<Message> messageList = new ArrayList<>();
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText inputMessage = findViewById(R.id.send_text);
        Button sendButton = findViewById(R.id.send_button);
        RecyclerView recyclerView = findViewById(R.id.Recyclerview);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String json = sharedPreferences.getString(SHARED_PREF_KEY, null);

        if (json != null) {
            Type type = new TypeToken<ArrayList<Message>>() {}.getType();
            List<Message> savedMessages = gson.fromJson(json, type);
            messageList = new ArrayList<>(savedMessages);
        } else {
            messageList = new ArrayList<>();
        }

        adapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String userText = inputMessage.getText().toString().trim();
            if (!userText.isEmpty()){
                messageList.add(new Message(userText,true));
                messageList.add(new Message("My name is johnny BRAVO...", false));

                adapter.notifyDataSetChanged();

                String jsonSave = gson.toJson(messageList);
                sharedPreferences.edit().putString(SHARED_PREF_KEY, jsonSave).apply();

                inputMessage.setText("");
                recyclerView.scrollToPosition(messageList.size() - 1);
            }
        });



    }



}