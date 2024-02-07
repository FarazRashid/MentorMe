package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;

public class MentorItem {

    private Drawable profileImageUrl;
    private boolean isMentorAvailable;

    public MentorItem(Drawable profileImageUrl, boolean isMentorAvailable) {
        this.profileImageUrl = profileImageUrl;
        this.isMentorAvailable = isMentorAvailable;
    }

    public Drawable getProfileImageUrl() {
        return profileImageUrl;
    }

    public boolean isMentorAvailable() {
        return isMentorAvailable;
    }
}
