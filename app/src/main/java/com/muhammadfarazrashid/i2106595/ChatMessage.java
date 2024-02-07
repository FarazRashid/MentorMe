package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;

public class ChatMessage {
    private String message;
    private String time;
    private boolean isUser; // true if the message is sent by the user, false otherwise
    private Drawable otherPersonImage; // Image for the other person

    public ChatMessage(String message, String time, boolean isUser, Drawable otherPersonImage) {
        this.message = message;
        this.time = time;
        this.isUser = isUser;
        this.otherPersonImage = otherPersonImage;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isUser() {
        return isUser;
    }

    public Drawable getOtherPersonImage() {
        return otherPersonImage;
    }
}
