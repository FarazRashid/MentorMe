package com.muhammadfarazrashid.i2106595;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    private TextView messageTextView;
    private TextView timeTextView;
    private ImageView otherPersonImageView; // ImageView for the other person's image

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.userChatMessage);
        timeTextView = itemView.findViewById(R.id.userChatTime);
        otherPersonImageView = itemView.findViewById(R.id.otherUserImage); // Initialize ImageView
    }

    public void bind(ChatMessage chatMessage) {
        messageTextView.setText(chatMessage.getMessage());
        timeTextView.setText(chatMessage.getTimeStampFormatted());

        // Check if the message is from the other person and if there's an image available
        if (!chatMessage.isUser() && chatMessage.getOtherPersonImage() != null) {
            if(otherPersonImageView != null) {
                otherPersonImageView.setVisibility(View.VISIBLE);
                Picasso.get().load(chatMessage.getOtherPersonImage()).into(otherPersonImageView);
            }
        } else {
            if(otherPersonImageView != null) {
                otherPersonImageView.setVisibility(View.GONE);
            }
        }
    }
}

