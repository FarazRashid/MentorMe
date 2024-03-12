package com.muhammadfarazrashid.i2106595;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    public List<ChatMessage> chatMessages;
    private Context context;

    private onMessageClickListener listener;

    public ChatAdapter(List<ChatMessage> chatMessages, Context context, onMessageClickListener listener) {
        this.chatMessages = chatMessages;
        this.context = context;
        this.listener = listener;
    }

    // Method to add a new message to the chat adapter
    public void addMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) { // User message layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userchat, parent, false);
        } else { // Other person's message layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otheruserchat, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(chatMessages.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Show options for editing/deleting message
                listener.onMessageClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).isUser() ? 0 : 1;
    }

    //delete message

    public void editMessage(int position, String newMessage) {
        chatMessages.get(position).setMessage(newMessage);
        notifyItemChanged(position);

    }

    public void deleteMessage(int position) {
        chatMessages.remove(position);
        notifyItemRemoved(position);

    }

    public void removeMessage(String id){
        for (int i = 0; i < chatMessages.size(); i++) {
            if (chatMessages.get(i).getId().equals(id)) {
                chatMessages.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void editMessage(String id, String newMessage) {
        for (int i = 0; i < chatMessages.size(); i++) {
            if (chatMessages.get(i).getId().equals(id)) {
                chatMessages.get(i).setMessage(newMessage);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @NotNull
    public ChatMessage getMessage(@NotNull String messageId) {
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getId().equals(messageId)) {
                return chatMessage;
            }
        }
        return null;
    }

    public interface onMessageClickListener {

        void onMessageClick(int position);
    }



}

