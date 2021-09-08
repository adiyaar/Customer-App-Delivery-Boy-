package com.themescoder.androidstore.customs;

import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


/**
 * AppBarFlingScrollBehavior implements Fling behavior for Appbar and RecyclerView
 */

public final class AppBarFlingScrollBehavior extends AppBarLayout.Behavior {

    //keep scroll listener map, the custom scroll listener also keep the current scroll Y position.
    private Map<RecyclerView, RecyclerViewScrollListener> scrollListenerMap = new HashMap<>();


    public AppBarFlingScrollBehavior() {
    }

    public AppBarFlingScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param velocityX
     * @param velocityY
     * @param coordinatorLayout
     * @param child The child that attached the behavior (AppBarLayout)
     * @param target The scrolling target e.g. a recyclerView or NestedScrollView
     * @param consumed The fling should be consumed by the scrolling target or not
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        if (target instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) target;

            if (scrollListenerMap.get(recyclerView) == null) {
                RecyclerViewScrollListener recyclerViewScrollListener = new RecyclerViewScrollListener(coordinatorLayout, child, this);
                scrollListenerMap.put(recyclerView, recyclerViewScrollListener);
                recyclerView.addOnScrollListener(recyclerViewScrollListener);
            }

            scrollListenerMap.get(recyclerView).setVelocity(velocityY);

            //recyclerView only consume the fling when it's not scrolled to the top
            consumed = scrollListenerMap.get(recyclerView).getScrolledY() > 0;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);

    }


    private static class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        private int scrolledY;
        private float velocity;
        private boolean dragging;
        private WeakReference<AppBarLayout> childRef;
        private WeakReference<CoordinatorLayout> coordinatorLayoutRef;
        private WeakReference<AppBarFlingScrollBehavior> behaviorWeakReference;

        public RecyclerViewScrollListener(CoordinatorLayout coordinatorLayout, AppBarLayout child, AppBarFlingScrollBehavior barBehavior) {
            childRef = new WeakReference<AppBarLayout>(child);
            coordinatorLayoutRef = new WeakReference<CoordinatorLayout>(coordinatorLayout);
            behaviorWeakReference = new WeakReference<AppBarFlingScrollBehavior>(barBehavior);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            dragging = newState == RecyclerView.SCROLL_STATE_DRAGGING;
        }

        public void setVelocity(float velocity) {
            this.velocity = velocity;
        }

        public int getScrolledY() {
            return scrolledY;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            scrolledY += dy;

            if (scrolledY <= 0 && !dragging && childRef.get() != null && coordinatorLayoutRef.get() != null && behaviorWeakReference.get() != null) {
                //manually trigger the fling when it's scrolled at the top
                behaviorWeakReference.get().onNestedFling(coordinatorLayoutRef.get(), childRef.get(), recyclerView, 0, velocity, false);
            }
        }

    }
    
}

