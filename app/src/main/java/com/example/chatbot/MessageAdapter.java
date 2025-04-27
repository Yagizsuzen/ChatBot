package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    List<Message> messageList;

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public UserViewHolder(android.view.View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.userMessageText);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public BotViewHolder(android.view.View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.botMessageText);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_message, parent, false);
            return new UserViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bot_message,parent,false);
            return new BotViewHolder(view);
        }
    }


    public MessageAdapter(List<Message> messageList){
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getIsUser()){
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        Message message = messageList.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).messageText.setText(message.getContent());
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).messageText.setText(message.getContent());
        }
    }

}
