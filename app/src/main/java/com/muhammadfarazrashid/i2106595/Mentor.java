package com.muhammadfarazrashid.i2106595;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.net.Uri;

public class Mentor implements Parcelable {

    private String id;
    private String name;
    private String position;
    private String availability;
    private String salary;
    private String description;
    private boolean isFavorite = false;

    public Mentor(String id, String name, String position, String availability, String salary, String description) {
        this.id = id;
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

    public Mentor() {
        // Empty constructor
    }


    protected Mentor(Parcel in) {
        id = in.readString();
        name = in.readString();
        position = in.readString();
        availability = in.readString();
        salary = in.readString();
        description = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Mentor> CREATOR = new Creator<Mentor>() {
        @Override
        public Mentor createFromParcel(Parcel in) {
            return new Mentor(in);
        }

        @Override
        public Mentor[] newArray(int size) {
            return new Mentor[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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


    // Define a listener interface for callback
    public interface OnImageUrlListener {
        void onSuccess(String imageUrl);
        void onFailure(String errorMessage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(position);
        dest.writeString(availability);
        dest.writeString(salary);
        dest.writeString(description);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
