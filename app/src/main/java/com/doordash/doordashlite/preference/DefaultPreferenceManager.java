package com.doordash.doordashlite.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class DefaultPreferenceManager implements PreferenceManager {
    private static final String RESTAURANT_STATE_KEY = "restaurant_state_key";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DefaultPreferenceManager(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(RESTAURANT_STATE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public boolean getRestaurantStateById(long businessId) {
        return sharedPreferences.getBoolean(String.valueOf(businessId), false);
    }

    @Override
    public void setRestaurantState(long businessId, boolean isLiked) {
        String key = String.valueOf(businessId);
        if (isLiked) {
            editor.putBoolean(String.valueOf(businessId), true).apply();
        } else {
            editor.remove(key).apply();
        }
    }
}
