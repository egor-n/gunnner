package com.gunnner.data;

import android.view.View;
import android.widget.AbsListView;

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    public static final int BUFFER_ITEM_COUNT = 5;

    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;
    private int mLastFirstVisibleItem = 0;
    private int mLastTop = 0;

    public abstract void hideTabs(boolean hide);

    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        View firstChild = view.getChildAt(0);
        int top = (firstChild == null) ? 0 : firstChild.getTop();

        if (firstVisibleItem == mLastFirstVisibleItem) {
            if (top > mLastTop) {
                hideTabs(false);
            } else if (top < mLastTop) {
                hideTabs(true);
            }
        } else {
            if (firstVisibleItem < mLastFirstVisibleItem) {
                hideTabs(false);
            } else {
                hideTabs(true);
            }
        }

        mLastTop = top;
        mLastFirstVisibleItem = firstVisibleItem;

        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
            }
        }

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + BUFFER_ITEM_COUNT)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }
}