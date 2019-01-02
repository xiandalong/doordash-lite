package com.doordash.doordashlite.screens.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.doordash.doordashlite.R;
import com.doordash.doordashlite.models.Restaurant;
import com.doordash.doordashlite.preference.DefaultPreferenceManager;

import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements DiscoverContract.View {
    @BindView(R.id.discover_recycler_view)
    RecyclerView discoverRecyclerView;

    private DiscoverAdapter discoverAdapter;
    private DiscoverPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        ButterKnife.bind(this);

        presenter = new DiscoverPresenter(this, new DefaultPreferenceManager(this));

        setTitle(R.string.discover_toolbar_title);
        initDiscoverAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void updateDiscoverList(@NonNull List<Restaurant> restaurants) {
        discoverAdapter.updateDiscoverList(restaurants);
    }

    @Override
    public void updateDiscoverItem(@NonNull Restaurant restaurant) {
        discoverAdapter.updateDiscoverItem(restaurant);
    }

    private void initDiscoverAdapter() {
        discoverAdapter = new DiscoverAdapter(presenter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        discoverRecyclerView.setLayoutManager(linearLayoutManager);
        discoverRecyclerView.setAdapter(discoverAdapter);
        discoverRecyclerView.addOnScrollListener(new InfiniteScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage, int totalItemCount, View view) {
                presenter.onLoadingMore(currentPage);
            }
        });
    }
}
