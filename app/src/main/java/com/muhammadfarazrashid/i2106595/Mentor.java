package com.muhammadfarazrashid.i2106595;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Mentor {

    private String id;
    private String name;
    private String position;
    private String availability;
    private String salary;

    private String description;
    private boolean isFavorite=false;


    public Mentor(String id, String name, String position, String availability, String salary,String description) {
        this.name = name;
        this.position = position;
        this.availability = availability;
        this.salary = salary;
        this.description = description;

    }

    public Mentor(String name, String position, String availability, String salary) {
        this.name = name;
        this.position = position;
        this.availability = availability;
        this.salary = salary;
        this.description = "No description available";

    }
    //no argument constructor
    public Mentor() {
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getAvailability() {
        return availability;
    }

    public String getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void getImageUrl(String mentorId, final OnImageUrlListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference filePath = storage.getReference().child("mentor_profile_images").child(mentorId + ".jpg");
        Log.d("Mentor", "getImageUrl: " + filePath.getPath());
        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Image URL retrieved successfully
                String imageUrl = uri.toString();
                listener.onSuccess(imageUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to retrieve image URL
                listener.onFailure(e.getMessage());
            }
        });
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // Define a listener interface for callback
    public interface OnImageUrlListener {
        void onSuccess(String imageUrl);
        void onFailure(String errorMessage);
    }


}
