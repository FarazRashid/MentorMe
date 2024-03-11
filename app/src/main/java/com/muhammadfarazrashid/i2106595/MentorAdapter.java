package com.muhammadfarazrashid.i2106595;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class MentorAdapter extends RecyclerView.Adapter<MentorViewHolder> {

    private List<MentorItem> mentorItems;

    public MentorAdapter(List<MentorItem> mentorItems) {
        this.mentorItems = mentorItems;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.communitymembercard, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        MentorItem mentorItem = mentorItems.get(position);
        holder.bind(mentorItem);



        Mentor.getMentorById(mentorItem.getId(), new Mentor.OnMentorListener() {
            @Override
            public void onSuccess(Mentor fetchedMentor) {
                // Set mentor when fetched successfully
                mentorItem.setMentor(fetchedMentor);
                Picasso.get().load(mentorItem.getMentor().getprofilePictureUrl()).into(holder.profileImageView);


            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure to fetch mentor details
                Log.e("AllMessagesChat", "Failed to fetch mentor details: " + errorMessage);
            }
        });

        // Set OnClickListener on the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                Mentor mentor = mentorItem.getMentor();

                Intent intent = new Intent(v.getContext(), communityChatActivity.class);
                intent.putExtra("mentor", mentor);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mentorItems.size();
    }
}
