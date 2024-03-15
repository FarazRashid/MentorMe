package com.muhammadfarazrashid.i2106595;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class SessionViewHolder extends RecyclerView.ViewHolder {
    ImageView sessionImage;
    TextView sessionName;
    TextView sessionDate;
    TextView sessionTime;
    TextView sessionPosition;

    public SessionViewHolder(@NonNull View itemView) {
        super(itemView);
        sessionImage = itemView.findViewById(R.id.card);
        sessionName = itemView.findViewById(R.id.cardName);
        sessionDate = itemView.findViewById(R.id.cardDate);
        sessionTime = itemView.findViewById(R.id.cardTime);
        sessionPosition = itemView.findViewById(R.id.cardPosition);
    }

    public void bind(Session session) {
        Picasso.get().load(session.getMentor().getprofilePictureUrl()).into(sessionImage);
        sessionName.setText(session.getMentor().getName());
        sessionDate.setText(session.getDate());
        sessionTime.setText(session.getTime());
        sessionPosition.setText(session.getMentor().getPosition());
    }
}
