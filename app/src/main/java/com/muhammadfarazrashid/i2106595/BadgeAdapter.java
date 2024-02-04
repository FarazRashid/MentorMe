package com.muhammadfarazrashid.i2106595;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class BadgeViewHolder extends RecyclerView.ViewHolder {
    TextView badgeName;
    CardView cardView;

    public BadgeViewHolder(@NonNull View itemView) {
        super(itemView);
        badgeName = itemView.findViewById(R.id.badgeName);
        cardView = itemView.findViewById(R.id.cardView); // Replace with your actual ID
    }
}
public class BadgeAdapter extends RecyclerView.Adapter<BadgeViewHolder> {

    private List<Badge> badges;
    private int selectedPosition = 0;
    private OnBadgeClickListener onBadgeClickListener;

    public BadgeAdapter(List<Badge> badges) {
        this.badges = badges;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorybadge, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = badges.get(position);

        holder.badgeName.setText(badge.getName());

        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#157177"));
            holder.badgeName.setTextColor(Color.WHITE);
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#BCE1E2"));
            holder.badgeName.setTextColor(Color.parseColor("#2B7F85"));
        }

        holder.cardView.setOnClickListener(view -> {
            if (onBadgeClickListener != null) {
                onBadgeClickListener.onBadgeClick(position);

                // Update selected position
                int previousSelected = selectedPosition;
                selectedPosition = position;

                // Notify item changed to update UI
                notifyItemChanged(previousSelected);
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public void setOnBadgeClickListener(@NotNull Object any) {
        this.onBadgeClickListener = (OnBadgeClickListener) any;
    }

    public interface OnBadgeClickListener {
        void onBadgeClick(int position);
    }
}
