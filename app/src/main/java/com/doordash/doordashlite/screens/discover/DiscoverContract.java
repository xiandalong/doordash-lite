package com.doordash.doordashlite.screens.discover;

import android.support.annotation.NonNull;
import com.doordash.doordashlite.models.Restaurant;

import java.util.List;

class DiscoverContract {
    interface View {
        void updateDiscoverList(@NonNull List<Restaurant> restaurants);
    }

    interface Presenter {
        void onStart();

        void onDestroy();

        void onLoadingMore(int offset);
    }
}
