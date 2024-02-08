package com.muhammadfarazrashid.i2106595;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView reviewTextView;
    private ImageView star1, star2, star3, star4, star5;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.cardName);
        reviewTextView = itemView.findViewById(R.id.cardAvailability);
        star1 = itemView.findViewById(R.id.star1);
        star2 = itemView.findViewById(R.id.star2);
        star3 = itemView.findViewById(R.id.star3);
        star4 = itemView.findViewById(R.id.star4);
        star5 = itemView.findViewById(R.id.star5);
    }

    public void bind(ReviewItem reviewItem) {
        nameTextView.setText(reviewItem.getName());
        reviewTextView.setText(reviewItem.getReviewText());
        setStars(reviewItem.getRating());
    }

    private void setStars(int rating) {
        ImageView[] stars = {star1, star2, star3, star4, star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImageResource(R.drawable.star);
            } else if (i == rating && rating % 2 != 0) {
                stars[i].setImageResource(R.drawable.halfstar);
            } else {
                stars[i].setImageResource(R.drawable.emptyyellowstar);
            }
        }
    }

}