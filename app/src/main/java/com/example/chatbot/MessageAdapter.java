package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.userMessageText);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.botMessageText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isUser()) {
            return 0; // User message
        } else {
            return 1; // Bot message
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_message, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bot_message, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).messageText.setText(message.getContent());
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).messageText.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
