package com.hsd.musical.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import activity.musical.hsd.com.musical_instrument.MusicalActivity;

public class CHScrollView extends HorizontalScrollView {

	MusicalActivity activity;

	public CHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		activity = (MusicalActivity) context;
	}


	public CHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MusicalActivity) context;
	}

	public CHScrollView(Context context) {
		super(context);
		activity = (MusicalActivity) context;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//进行触摸赋值
		activity.mTouchView = this;
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//当当前的CHSCrollView被触摸时，滑动其它
		if(activity.mTouchView == this) {
			activity.onScrollChanged(l, t, oldl, oldt);
		}else{
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}
}
