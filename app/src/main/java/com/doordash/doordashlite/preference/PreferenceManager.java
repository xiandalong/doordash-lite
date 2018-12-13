package com.doordash.doordashlite.preference;

public interface PreferenceManager {
    boolean getRestaurantStateById(long businessId);

    void setRestaurantState(long businessId, boolean isLiked);
}
