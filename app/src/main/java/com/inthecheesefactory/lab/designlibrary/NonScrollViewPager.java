package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author zyq 15-11-29
 */
public class NonScrollViewPager extends ViewPager {

	boolean mIsScrollable;

	public NonScrollViewPager(Context context) {
		this(context, null);
	}

	public NonScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mIsScrollable = false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!mIsScrollable) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mIsScrollable) {
			return false;
		}
		return super.onTouchEvent(event);
	}

	public boolean isScrollable() {
		return mIsScrollable;
	}

	public void setIsScrollable(boolean isScrollable) {
		mIsScrollable = isScrollable;
	}
}