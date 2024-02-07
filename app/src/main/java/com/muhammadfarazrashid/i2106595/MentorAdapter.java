package com.muhammadfarazrashid.i2106595;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        // Set OnClickListener on the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                // For example, you can open a new activity
                Intent intent = new Intent(v.getContext(), communityChatActivity.class);
                // Add any extra data if needed
                // intent.putExtra("key", value);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mentorItems.size();
    }
}
