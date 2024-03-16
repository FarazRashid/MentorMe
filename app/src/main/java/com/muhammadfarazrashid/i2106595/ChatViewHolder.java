package com.muhammadfarazrashid.i2106595;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;


public class ChatViewHolder extends RecyclerView.ViewHolder {
    private TextView messageTextView;
    private TextView timeTextView;
    private ImageView otherPersonImageView; // ImageView for the other person's image
    private ChatMessage chatMessage;

    private ImageView messageImageView;

    public VideoView videoView;

    private VoicePlayerView voicePlayerView;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.userChatMessage);
        timeTextView = itemView.findViewById(R.id.userChatTime);
        otherPersonImageView = itemView.findViewById(R.id.otherUserImage); // Initialize ImageView
        messageImageView = itemView.findViewById(R.id.messageImageView);
        videoView = itemView.findViewById(R.id.messageVideoView);
        voicePlayerView = itemView.findViewById(R.id.voicePlayerView);
    }

    public void bind(ChatMessage chatMessage, String type){

        if(Objects.equals(type, "message")) {

            messageTextView.setText(chatMessage.getMessage());
            timeTextView.setText(chatMessage.getTimeStampFormatted());

            // Check if the message is from the other person and if there's an image available
            if (!chatMessage.isUser() && chatMessage.getOtherPersonImage() != null) {
                if (otherPersonImageView != null) {
                    otherPersonImageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(chatMessage.getOtherPersonImage()).into(otherPersonImageView);
                }
            } else {
                if (otherPersonImageView != null) {
                    otherPersonImageView.setVisibility(View.GONE);
                }
            }

            Log.d("ChatViewHolder", "bind: " + chatMessage.getMessageImageUrl());

            if (!Objects.equals(chatMessage.getMessageImageUrl(), "")) {
                messageImageView.setVisibility(View.VISIBLE);
                Picasso.get().load(chatMessage.getMessageImageUrl()).into(messageImageView);
            } else if (messageImageView != null) {
                messageImageView.setVisibility(View.GONE);
            }

            if (!Objects.equals(chatMessage.getVideoImageUrl(), "")) {
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse(chatMessage.getVideoImageUrl()));
                videoView.setMediaController(new MediaController(itemView.getContext()));
                videoView.start();
            } else if (videoView != null) {
                videoView.setVisibility(View.GONE);
            }

            Log.d("ChatViewHolder", "bind: " + chatMessage.getVideoImageUrl());

        }
        else if(Objects.equals(type, "voice")) {

            if (!Objects.equals(chatMessage.getVoiceMemoUrl(), "")) {
                voicePlayerView.setAudio(chatMessage.getVoiceMemoUrl());
                voicePlayerView.setEnableVirtualizer(true);
            }

            Log.d("ChatViewHolder", "bind: " + chatMessage.getVoiceMemoUrl());

        }

        else if(Objects.equals(type, "voice_other_user"))
        {
            if (!Objects.equals(chatMessage.getVoiceMemoUrl(), "")) {
                voicePlayerView.setAudio(chatMessage.getVoiceMemoUrl());
                voicePlayerView.setEnableVirtualizer(true);

            }

            Log.d("ChatViewHolder", "bind: " + chatMessage.getVoiceMemoUrl());

            // Check if the message is from the other person and if there's an image available
            if (!chatMessage.isUser() && chatMessage.getOtherPersonImage() != null) {
                if (otherPersonImageView != null) {
                    otherPersonImageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(chatMessage.getOtherPersonImage()).into(otherPersonImageView);
                }
            } else {
                if (otherPersonImageView != null) {
                    otherPersonImageView.setVisibility(View.GONE);
                }
            }

        }


    }
}

