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

    private void setStars(float rating) {
        ImageView[] stars = {star1, star2, star3, star4, star5};

        star1.setImageResource(R.drawable.emptyyellowstar);
        star2.setImageResource(R.drawable.emptyyellowstar);
        star3.setImageResource(R.drawable.emptyyellowstar);
        star4.setImageResource(R.drawable.emptyyellowstar);
        star5.setImageResource(R.drawable.emptyyellowstar);

        if(rating==0.5)
            star1.setImageResource(R.drawable.halfstar);
        else if(rating==1)
            star1.setImageResource(R.drawable.star);
        else if(rating==1.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.halfstar);
        }
        else if(rating==2) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
        }
        else if(rating==2.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.halfstar);
        }
        else if(rating==3) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
        }
        else if(rating==3.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.halfstar);
        }
        else if(rating==4) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
        }
        else if(rating==4.5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.halfstar);
        }
        else if(rating==5) {
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star5.setImageResource(R.drawable.star);
        }
    }

}