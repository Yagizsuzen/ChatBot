package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.openai.com/";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<ChatSession> chatList;
    private ChatSession currentChat;
    private MessageAdapter adapter;
    private ApiService apiService;

    private EditText inputMessage;
    private RecyclerView recyclerView;
    private boolean isNewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        gson = new Gson();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        inputMessage = findViewById(R.id.send_text);
        Button sendButton = findViewById(R.id.send_button);
        Button backToListButton = findViewById(R.id.back_to_list_button);
        recyclerView = findViewById(R.id.Recyclerview);

        loadChats();

        String chatId = getIntent().getStringExtra("chat_id");
        isNewChat = getIntent().getBooleanExtra("is_new_chat", false);

        if (chatId == null || chatId.isEmpty()) {
            finish();
            return;
        }

        currentChat = findChatById(chatId);

        if (currentChat == null) {
            if (isNewChat) {
                currentChat = new ChatSession(chatId, "New Chat", new ArrayList<>());
            } else {
                finish();
                return;
            }
        }

        adapter = new MessageAdapter(currentChat.getMessages());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        sendButton.setOnClickListener(v -> {
            String userText = inputMessage.getText().toString().trim();
            if (!userText.isEmpty()) {
                sendMessage(userText);
                inputMessage.setText("");
                inputMessage.clearFocus();
            }
        });

        backToListButton.setOnClickListener(v -> {
            finish();
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void sendMessage(String userText) {
        currentChat.getMessages().add(new Message(userText, true));
        adapter.notifyItemInserted(currentChat.getMessages().size() - 1);
        recyclerView.scrollToPosition(currentChat.getMessages().size() - 1);
        updateChatInListAndSave();
        sendBotReply(userText);
    }

    private void sendBotReply(String userText) {
        List<ChatRequest.MessageData> messages = new ArrayList<>();
        messages.add(new ChatRequest.MessageData("system", "You are a helpful assistant."));

        for (Message message : currentChat.getMessages()) {
            if (message.isUser()) {
                messages.add(new ChatRequest.MessageData("user", message.getContent()));
            } else {
                messages.add(new ChatRequest.MessageData("assistant", message.getContent()));
            }
        }

        ChatRequest request = new ChatRequest("gpt-3.5-turbo", messages);

        apiService.sendMessage("Bearer " + BuildConfig.OPENAI_API_KEY, request)
                .enqueue(new Callback<ChatResponse>() {
                    @Override
                    public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (!response.body().getChoices().isEmpty()) {
                                String botReply = response.body().getChoices().get(0).getMessage().getContent();
                                addBotMessage(botReply.trim());
                            } else {
                                addBotMessage("Bot sent an empty response.");
                            }
                        } else {
                            addBotMessage("Failed to get response. Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ChatResponse> call, Throwable t) {
                        addBotMessage("Error: " + t.getMessage());
                    }
                });
    }

    private void addBotMessage(String botText) {
        new Handler().postDelayed(() -> {
            currentChat.getMessages().add(new Message(botText, false));
            adapter.notifyItemInserted(currentChat.getMessages().size() - 1);
            recyclerView.scrollToPosition(currentChat.getMessages().size() - 1);
            updateChatInListAndSave();
        }, 1000);
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

    private void updateChatInListAndSave() {
        boolean found = false;
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getId().equals(currentChat.getId())) {
                chatList.set(i, currentChat);
                found = true;
                break;
            }
        }
        if (!found) {
            chatList.add(currentChat);
        }
        saveChats();
    }

    private void saveChats() {
        String json = gson.toJson(chatList);
        sharedPreferences.edit().putString("chats", json).apply();
    }

    private ChatSession findChatById(String id) {
        for (ChatSession chat : chatList) {
            if (chat.getId().equals(id)) {
                return chat;
            }
        }
        return null;
    }

    @Override
    public void finish() {
        if (isNewChat && currentChat.getMessages().isEmpty()) {
            setResult(RESULT_CANCELED);
        } else {
            Intent data = new Intent();
            data.putExtra("updated_chat", gson.toJson(currentChat));
            setResult(RESULT_OK, data);
        }
        super.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
