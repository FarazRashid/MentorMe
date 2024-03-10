package com.muhammadfarazrashid.i2106595;

public class ReviewItem {

    private String name;
    private String reviewText;
    private float rating;

    public ReviewItem(String name, String reviewText, float rating) {
        this.name = name;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}