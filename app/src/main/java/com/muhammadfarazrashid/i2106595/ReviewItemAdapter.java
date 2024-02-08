package com.muhammadfarazrashid.i2106595;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

class ReviewItemAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<ReviewItem> reviewItems;

    public ReviewItemAdapter(List<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewscard, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewItem reviewItem = reviewItems.get(position);
        holder.bind(reviewItem);
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}
