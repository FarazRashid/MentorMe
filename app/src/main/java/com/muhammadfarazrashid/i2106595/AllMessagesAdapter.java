package com.muhammadfarazrashid.i2106595;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllMessagesAdapter extends RecyclerView.Adapter<AllMessagesAdapter.ViewHolder> {

    private List<AllMessagesChat> chatMessages;
    private Context context;

    public AllMessagesAdapter(List<AllMessagesChat> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chatcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllMessagesChat message = chatMessages.get(position);

        holder.userName.setText(message.getUserName());
        int unreadMessageCount = message.getUnreadMessages();
        holder.unreadMessages.setText(unreadMessageCount + " new message");

        // Set text color based on the number of unread messages
        if (unreadMessageCount == 0) {
            holder.unreadMessages.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            //make color red
            holder.unreadMessages.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        // Set other user image
        Drawable otherPersonImage = message.getOtherUserImage();
        if (otherPersonImage != null) {
            holder.otherUserImage.setImageDrawable(otherPersonImage);
        }

        // Set OnClickListener on the card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                // For example, you can open a new activity
                Intent intent = new Intent(context, MentorChatActivity.class);
                // Add any extra data if needed
                // intent.putExtra("key", value);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView otherUserImage;
        TextView userName;
        TextView unreadMessages;
        View dividerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherUserImage = itemView.findViewById(R.id.otherUserImage);
            userName = itemView.findViewById(R.id.userName);
            unreadMessages = itemView.findViewById(R.id.unreadMessages);
            dividerView = itemView.findViewById(R.id.view);
        }
    }
}
