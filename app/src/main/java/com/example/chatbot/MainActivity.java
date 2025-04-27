package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<ChatSession> chatList;
    private ChatSessionAdapter adapter;

    private RecyclerView recyclerView;
    private Button newChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = findViewById(R.id.chat_list_recyclerview);
        newChatButton = findViewById(R.id.new_chat_button);

        loadChats();

        adapter = new ChatSessionAdapter(chatList, this::openChat, this::deleteChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        newChatButton.setOnClickListener(v -> startNewChat());
    }

    private void startNewChat() {
        String id = UUID.randomUUID().toString();
        ChatSession newChat = new ChatSession(id, "New Chat", new ArrayList<>());

        chatList.add(newChat);

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chat_id", id);
        intent.putExtra("is_new_chat", true);
        startActivityForResult(intent, 123);
    }

    private void openChat(ChatSession chatSession) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chat_id", chatSession.getId());
        intent.putExtra("is_new_chat", false);
        startActivityForResult(intent, 123);
    }

    private void deleteChat(ChatSession chatSession) {
        chatList.remove(chatSession);
        saveChats();
        adapter.notifyDataSetChanged();
    }

    private void loadChats() {
        String json = sharedPreferences.getString("chats", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<ChatSession>>() {}.getType();
            chatList = gson.fromJson(json, type);
        } else {
            chatList = new ArrayList<>();
        }
    }

    private void saveChats() {
        String json = gson.toJson(chatList);
        sharedPreferences.edit().putString("chats", json).apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK && data != null) {
                String updatedChatJson = data.getStringExtra("updated_chat");
                if (updatedChatJson != null) {
                    ChatSession updatedChat = gson.fromJson(updatedChatJson, ChatSession.class);
                    for (int i = 0; i < chatList.size(); i++) {
                        if (chatList.get(i).getId().equals(updatedChat.getId())) {
                            chatList.set(i, updatedChat);
                            break;
                        }
                    }
                    saveChats();
                    adapter.notifyDataSetChanged();
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (!chatList.isEmpty() && chatList.get(chatList.size() - 1).getMessages().isEmpty()) {
                    chatList.remove(chatList.size() - 1);
                    saveChats();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
