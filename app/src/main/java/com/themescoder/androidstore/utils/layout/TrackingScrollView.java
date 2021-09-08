package com.themescoder.androidstore.utils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

public class TrackingScrollView extends ScrollView {
	public interface OnScrollChangedListener {
		void onScrollChanged(TrackingScrollView source, int l, int t, int oldl, int oldt);
	}

	private OnScrollChangedListener mOnScrollChangedListener;

	public TrackingScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrackingScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		mOnScrollChangedListener = listener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}
	
	   @Override
	    public void requestChildFocus(View child, View focused) {
	        if (focused instanceof ListView)
	           return;
	    super.requestChildFocus(child, focused);
	    }
}