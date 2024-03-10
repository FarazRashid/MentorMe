package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MentorViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImageView;
    RelativeLayout mentorAvailabilityIndicator;
    View availabilityIndicator;

    public MentorViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageView = itemView.findViewById(R.id.profileImageView);
        mentorAvailabilityIndicator = itemView.findViewById(R.id.mentorAvailabilityIndicator);
        availabilityIndicator = itemView.findViewById(R.id.availabilityIndicator);
    }

    public void bind(MentorItem mentorItem) {

        // Set mentor availability indicator background tint
        if (mentorItem.isMentorAvailable()) {
            availabilityIndicator.setBackgroundTintList(itemView.getResources().getColorStateList(R.color.available_color));
        } else {
            availabilityIndicator.setBackgroundTintList(itemView.getResources().getColorStateList(R.color.not_available_color));
        }
    }
}
