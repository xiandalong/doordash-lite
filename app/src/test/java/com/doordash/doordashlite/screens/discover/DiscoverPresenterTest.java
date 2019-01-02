package com.doordash.doordashlite.screens.discover;

import android.support.annotation.NonNull;
import com.doordash.doordashlite.models.Restaurant;
import com.doordash.doordashlite.network.DoorDashApi;
import com.doordash.doordashlite.preference.PreferenceManager;
import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class DiscoverPresenterTest {

    private static final TestScheduler testScheduler = new TestScheduler();

    private PublishProcessor<Integer> publishProcessor = PublishProcessor.create();

    @Mock
    private DiscoverContract.View view;
    @Mock
    private DoorDashApi doorDashApi;
    @Mock
    private PreferenceManager preferenceManager;

    private DiscoverPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> testScheduler);
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> testScheduler);
        RxJavaPlugins.setIoSchedulerHandler(s -> testScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(s -> testScheduler);
        presenter = new DiscoverPresenter(view, preferenceManager);
    }

    @Test
    public void fetchDiscoverList() {
        publishProcessorEvent();
        when(doorDashApi.getRestaurantsByLocation("1", "1", "1", "1")).thenReturn(Flowable.just(mockRestaurants()));

        presenter.onStart();
        testScheduler.triggerActions();

        verify(view).updateDiscoverList(any());

        presenter.onLoadingMore(anyInt());
        testScheduler.triggerActions();

        verify(view, times(2)).updateDiscoverList(any());
    }

    @Test
    public void favoriteRestaurant() {
        Restaurant mockRestaurant = mockRestaurant();

        presenter.onFavoriteRestaurantClicked(mockRestaurant);

        verify(preferenceManager).setRestaurantState(mockRestaurant.businessId, true);
        verify(view).updateDiscoverItem(any());
    }

    @Test
    public void unFavoriteRestaurant() {
        Restaurant mockRestaurant = mockRestaurant();
        presenter.onUnfavoriteRestaurantClicked(mockRestaurant);

        verify(preferenceManager).setRestaurantState(mockRestaurant.businessId, false);
        verify(view).updateDiscoverItem(any());
    }

    private void publishProcessorEvent() {
        publishProcessor.onNext(1);
    }

    private List<Restaurant> mockRestaurants() {
        Restaurant restaurant = new Restaurant();
        restaurant.name = "test";
        restaurant.status = "status";
        restaurant.statusType = "type";
        restaurant.tags = Arrays.asList("tag1", "tag2");
        return Collections.singletonList(restaurant);
    }

    @NonNull
    private Restaurant mockRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.businessId = 12;
        return restaurant;
    }
}
