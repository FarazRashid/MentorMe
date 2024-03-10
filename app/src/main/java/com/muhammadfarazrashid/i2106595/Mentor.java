package com.muhammadfarazrashid.i2106595;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private int rating =0;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public static void getMentorById(String mentorId, final OnMentorListener listener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mentorRef = db.getReference().child("Mentors").child(mentorId);

        mentorRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                String name = snapshot.child("name").getValue(String.class);
                String position = snapshot.child("position").getValue(String.class);
                String availability = snapshot.child("availability").getValue(String.class);
                String salary = snapshot.child("salary").getValue(String.class);
                String description = snapshot.child("description").getValue(String.class);

                // Create a new Mentor object with the retrieved fields
                Mentor mentor = new Mentor(mentorId, name, position, availability, salary, description);

                listener.onSuccess(mentor);
            } else {
                listener.onFailure("Mentor not found");
            }
        }).addOnFailureListener(e -> {
            listener.onFailure(e.getMessage());
        });
    }

    public interface OnMentorListener {
        void onSuccess(Mentor mentor);
        void onFailure(String errorMessage);
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
