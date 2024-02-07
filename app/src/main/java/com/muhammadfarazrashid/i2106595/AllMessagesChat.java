package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;

public class AllMessagesChat {

    private String userName;
    private int unreadMessages;
    private Drawable otherUserImage;

    public AllMessagesChat(String userName, int unreadMessages, Drawable otherUserImage) {
        this.userName = userName;
        this.unreadMessages = unreadMessages;
        this.otherUserImage = otherUserImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public Drawable getOtherUserImage() {
        return otherUserImage;
    }
}
