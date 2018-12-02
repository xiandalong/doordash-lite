package com.doordash.doordashlite.screens.discover;

import android.support.annotation.NonNull;
import com.doordash.doordashlite.models.Restaurant;
import com.doordash.doordashlite.network.RetrofitClient;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import timber.log.Timber;

import java.util.List;

class DiscoverPresenter implements DiscoverContract.Presenter {
    @NonNull
    private final DiscoverContract.View view;

    @NonNull
    private final CompositeDisposable disposables = new CompositeDisposable();

    @NonNull
    private PublishProcessor<Integer> publishProcessor = PublishProcessor.create();

    DiscoverPresenter(@NonNull DiscoverContract.View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        subscribePublishProcessor();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    @Override
    public void onLoadingMore(int offset) {
        publishProcessor.onNext(offset);
    }

    private void subscribePublishProcessor() {
        Disposable disposable = publishProcessor.onBackpressureDrop()
                .concatMap((Function<Integer, Publisher<List<Restaurant>>>) this::getRestaurants)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::updateDiscoverList, Timber::e);
        disposables.add(disposable);
        publishProcessor.onNext(0);
    }

    @NonNull
    private Flowable<List<Restaurant>> getRestaurants(int offset) {
        String lat = String.valueOf(37.422740);
        String lng = String.valueOf(-122.139956);
        String limit = String.valueOf(20);
        return RetrofitClient.getDoorDashApi().getRestaurantsByLocation(lat, lng, String.valueOf(offset), limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
