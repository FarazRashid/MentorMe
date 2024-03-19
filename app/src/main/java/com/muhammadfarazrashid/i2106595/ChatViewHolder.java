package com.muhammadfarazrashid.i2106595;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;


public class ChatViewHolder extends RecyclerView.ViewHolder {
    private TextView messageTextView;
    private TextView timeTextView;
    private ImageView otherPersonImageView; // ImageView for the other person's image
    private ChatMessage chatMessage;

    private ImageView messageImageView;

    public PlayerView videoView;

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

    @OptIn(markerClass = UnstableApi.class) public void bind(ChatMessage chatMessage, String type){

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
                messageTextView.setVisibility(View.GONE);



                // Load the image using Picasso
                Picasso.get().load(chatMessage.getMessageImageUrl()).into(messageImageView);

            }else {
                messageImageView.setVisibility(View.GONE);
                messageTextView.setVisibility(View.VISIBLE);
            }


            if (!Objects.equals(chatMessage.getVideoImageUrl(), "")) {
                videoView.setVisibility(View.VISIBLE);
                messageTextView.setVisibility(View.GONE);

                SimpleExoPlayer exoPlayer = new SimpleExoPlayer.Builder(videoView.getContext()).build();
                MediaItem mediaItem = MediaItem.fromUri(Uri.parse(chatMessage.getVideoImageUrl()));
                exoPlayer.setMediaItem(mediaItem);


                // Set brightness
                videoView.setKeepScreenOn(true);

                videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

                // Prepare the player
                exoPlayer.prepare();

                // Bind the player to the PlayerView
                videoView.setPlayer(exoPlayer);


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

