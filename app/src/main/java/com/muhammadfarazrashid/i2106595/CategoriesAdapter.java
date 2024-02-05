package com.muhammadfarazrashid.i2106595;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class Category{
    String name;
    int image;

    public Category(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<Category> categoriesList;
    private final OnCategoryClickListener onCategoryClickListener;

    public CategoriesAdapter(List<Category> categoriesList, OnCategoryClickListener onCategoryClickListener) {
        this.categoriesList = categoriesList;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoriescard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoriesList.get(holder.getAdapterPosition());

        // Set category name and image
        holder.categoryName.setText(category.getName());
        holder.categoryImage.setImageResource(category.getImage());

        // Set click listener for the whole card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClickListener.onCategoryClick(holder.getAdapterPosition());
            }
        });

        // Set click listener for the arrow button
        holder.arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClickListener.onCategoryClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        ImageButton arrowButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            arrowButton = itemView.findViewById(R.id.categoryButton);
        }
    }

    // Interface to define the onCategoryClick method
    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }
}

