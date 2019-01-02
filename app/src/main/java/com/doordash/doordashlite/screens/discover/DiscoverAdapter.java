package com.doordash.doordashlite.screens.discover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.doordash.doordashlite.StringUtils;
import com.doordash.doordashlite.R;
import com.doordash.doordashlite.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private final DiscoverItemClickListener listener;

    DiscoverAdapter(@NonNull DiscoverItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover_layout, viewGroup, false);
        return new DiscoverViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder discoverViewHolder, int position) {
        discoverViewHolder.bindView(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    void updateDiscoverList(@NonNull List<Restaurant> restaurants) {
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }

    void updateDiscoverItem(@NonNull Restaurant restaurant) {
        int position = this.restaurants.indexOf(restaurant);
        notifyItemChanged(position);
    }

    class DiscoverViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.discover_item_name)
        TextView itemNameTextView;
        @BindView(R.id.discover_item_description)
        TextView itemDescriptionTextView;
        @BindView(R.id.discover_item_status)
        TextView itemStatusTextView;
        @BindView(R.id.discover_item_image)
        ImageView itemImageView;
        @BindView(R.id.favorite_button)
        ImageButton favoriteStatusImageView;

        @OnClick(R.id.favorite_button)
        void onFavoriteClicked() {
            if (favoriteStatusImageView.isSelected()) {
                listener.onUnfavoriteRestaurantClicked(restaurant);
            } else {
                listener.onFavoriteRestaurantClicked(restaurant);
            }
        }

        @NonNull
        private final Context context;
        private final DiscoverItemClickListener listener;
        private Restaurant restaurant;

        DiscoverViewHolder(@NonNull View itemView, @NonNull DiscoverItemClickListener listener) {
            super(itemView);
            this.context = itemView.getContext();
            this.listener = listener;
            ButterKnife.bind(this, itemView);
        }

        void bindView(@NonNull Restaurant restaurant) {
            this.restaurant = restaurant;

            itemNameTextView.setText(restaurant.name);
            itemDescriptionTextView.setText(StringUtils.getTagString(restaurant.tags));
            itemStatusTextView.setText(restaurant.getStatus());
            favoriteStatusImageView.setSelected(restaurant.isLiked);
            Glide.with(context).load(restaurant.coverImageUrl).into(itemImageView);
        }
    }

    interface DiscoverItemClickListener {
        void onFavoriteRestaurantClicked(@NonNull Restaurant restaurant);

        void onUnfavoriteRestaurantClicked(@NonNull Restaurant restaurant);
    }
}
