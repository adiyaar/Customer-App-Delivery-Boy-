package com.themescoder.androidstore.utils.layout;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

public class StaggeredGridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;
    private boolean headerItemNoSpacing;

    public StaggeredGridSpacingItemDecoration(int spacing, boolean headerItemNoSpacing) {
        this.spacing = spacing;
        this.headerItemNoSpacing = headerItemNoSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

        if (layoutParams.isFullSpan()) {
            if (headerItemNoSpacing)
                outRect.set(0, 0, 0, 0);
            else {
                view.setPadding(spacing, spacing, spacing, spacing);
                //outRect.top = spacing;
                //outRect.bottom = spacing;
                //outRect.left = spacing;
                //outRect.right = spacing;
            }
        } else {
            int spanIndex = layoutParams.getSpanIndex();
            int layoutPosition = layoutParams.getViewLayoutPosition();
            int itemCount = parent.getAdapter().getItemCount();

            boolean leftEdge = spanIndex == 0;
            boolean rightEdge = spanIndex == (spanCount - 1);

            boolean topEdge = spanIndex < spanCount;
            boolean bottomEdge = layoutPosition >= (itemCount - spanCount);

            int halfSpacing = spacing / 2;

            outRect.set(
                    leftEdge ? spacing : halfSpacing,
                    topEdge ? spacing : halfSpacing,
                    rightEdge ? spacing : halfSpacing,
                    bottomEdge ? spacing : 0
            );
        }
    }
}