package com.muhammadfarazrashid.i2106595;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllMessagesViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView otherUserImageView;
    private TextView userNameTextView;
    private TextView unreadMessagesTextView;
    private View view;

    public AllMessagesViewHolder(@NonNull View itemView) {
        super(itemView);
        otherUserImageView = itemView.findViewById(R.id.otherUserImage);
        userNameTextView = itemView.findViewById(R.id.userName);
        unreadMessagesTextView = itemView.findViewById(R.id.unreadMessages);
        view = itemView.findViewById(R.id.view);
    }

    public void bind(AllMessagesChat chatMessage) {
        userNameTextView.setText(chatMessage.getUserName());
        int unreadMessages = chatMessage.getUnreadMessages();
        if (unreadMessages > 0) {
            unreadMessagesTextView.setTextColor(Color.BLACK);
            unreadMessagesTextView.setText(unreadMessages + " new message");
        } else {
            unreadMessagesTextView.setTextColor(Color.GRAY);
            unreadMessagesTextView.setText("No new messages");
        }
    }
}
