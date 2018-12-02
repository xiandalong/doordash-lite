package com.doordash.doordashlite.screens.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5;

    private LinearLayoutManager layoutManager;

    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    InfiniteScrollListener(@NonNull RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = (LinearLayoutManager) layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();
        if (totalItemCount < previousTotalItemCount) {
            currentPage = 0;
            previousTotalItemCount = totalItemCount;
            loading = totalItemCount == 0;
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }

    abstract public void onLoadMore(int currentPage, int totalItemCount, View view);
}
