package com.themescoder.androidstore.utils.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ExpandedListView extends ListView
{
    private ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public ExpandedListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

     @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != 0)
        {
            ListAdapter listAdapter = getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int count = listAdapter.getCount();
            for (int i = 0; i < count; i++) {
                View listItem = listAdapter.getView(i, null, this);
                int maxWidth = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
                listItem.measure(MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = totalHeight + (getDividerHeight() * (listAdapter.getCount() - 1));
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }
}
