package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatSessionAdapter extends RecyclerView.Adapter<ChatSessionAdapter.ChatSessionViewHolder> {

    private List<ChatSession> chatList;
    private OnChatClickListener clickListener;
    private OnChatDeleteListener deleteListener;

    public interface OnChatClickListener {
        void onChatClick(ChatSession chatSession);
    }

    public interface OnChatDeleteListener {
        void onChatDelete(ChatSession chatSession);
    }

    public ChatSessionAdapter(List<ChatSession> chatList, OnChatClickListener clickListener, OnChatDeleteListener deleteListener) {
        this.chatList = chatList;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ChatSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_session, parent, false);
        return new ChatSessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatSessionViewHolder holder, int position) {
        ChatSession chatSession = chatList.get(position);
        holder.chatTitle.setText(chatSession.getTitle());

        holder.itemView.setOnClickListener(v -> clickListener.onChatClick(chatSession));
        holder.deleteButton.setOnClickListener(v -> deleteListener.onChatDelete(chatSession));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ChatSessionViewHolder extends RecyclerView.ViewHolder {
        TextView chatTitle;
        Button deleteButton;

        public ChatSessionViewHolder(@NonNull View itemView) {
            super(itemView);
            chatTitle = itemView.findViewById(R.id.chat_title);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
