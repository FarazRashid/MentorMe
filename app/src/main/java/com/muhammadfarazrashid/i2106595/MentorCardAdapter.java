package com.muhammadfarazrashid.i2106595;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MentorCardAdapter extends RecyclerView.Adapter<MentorCardAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Mentor> data;
    private final int layoutResourceId; // Resource ID of the layout
    private OnItemClickListener listener; // Listener variable

    public MentorCardAdapter(Context context, List<Mentor> data, int layoutResourceId) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(layoutResourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mentor mentor = data.get(position);
        holder.mentorName.setText(mentor.getName());
        holder.mentorPosition.setText(mentor.getPosition());
        holder.mentorAvailability.setText(mentor.getAvailability());
        holder.mentorSalary.setText(mentor.getSalary());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Set item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mentorName, mentorPosition, mentorAvailability, mentorSalary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.cardName);
            mentorPosition = itemView.findViewById(R.id.cardPosition);
            mentorAvailability = itemView.findViewById(R.id.cardAvailability);
            mentorSalary = itemView.findViewById(R.id.cardSalary);

            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
