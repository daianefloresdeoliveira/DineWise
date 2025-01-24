package com.example.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.Restaurant;
import com.example.finalproject.views.client.RestaurantInfoActivity;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final Context context;
    private final List<Restaurant> restaurants;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Restaurant restaurant = restaurants.get(position);
        holder.restaurantName.setText(restaurant.getName());
        Glide.with(context).load(restaurant.getImage()).into(holder.restaurantImage);

        //To see the menu of each restaurant when click, but there is a bug
        //Can only see the menu of the Burger Station Restaurant
        //set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantInfoActivity.class);
            intent.putExtra("restaurantId", restaurant.getRestaurantId()); // Pass ID
            intent.putExtra("restaurantName", restaurant.getName());
            intent.putExtra("restaurantImage", restaurant.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImage;
        TextView restaurantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            restaurantName = itemView.findViewById(R.id.restaurantName);
        }
    }
}
