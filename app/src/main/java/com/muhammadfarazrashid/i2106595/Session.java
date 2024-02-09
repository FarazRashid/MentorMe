package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;

public class Session {
    private Drawable imageDrawable; // Drawable for the session image
    private String name;
    private String date;
    private String time;
    private String position;

    public Session(Drawable imageDrawable, String name, String date, String time, String position) {
        this.imageDrawable = imageDrawable;
        this.name = name;
        this.date = date;
        this.time = time;
        this.position = position;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPosition() {
        return position;
    }
}
