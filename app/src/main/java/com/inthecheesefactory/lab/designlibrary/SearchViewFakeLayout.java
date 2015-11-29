package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author zyq 15-11-29
 */
public class SearchViewFakeLayout extends FrameLayout implements SearchViewAnimation.OnToggleAnimationListener, View
		.OnClickListener {

	private View mCollasped;
	private View mCollapedBox;
	private SearchViewAnimation mSearchAnimator;

	public SearchViewFakeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setSearchAnimator(SearchViewAnimation searchAnimator) {
		mSearchAnimator = searchAnimator;
	}

	@Override
	protected void onFinishInflate() {
		mCollasped = (ViewGroup) findViewById(R.id.ly_search_collapse);
		mCollapedBox = findViewById(R.id.ly_search_collapse_box);
		mCollapedBox.setOnClickListener(this);
		mCollasped.setOnClickListener(this);
		mCollapedBox.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mCollapedBox.performClick();
				if (mSearchAnimator != null) {
					mSearchAnimator.performLongClick();
				}
				return false;
			}
		});
		super.onFinishInflate();
	}

	public void updateVisibility(boolean isExpand) {
		//	mCollapedBox.setVisibility(isExpand ? View.VISIBLE : View.GONE);
	}

	/**
	 * 　动画显示searchViewFakeLayout时候子控件变化
	 */
	public void onCollapse() {

	}

	@Override
	public void onFinish(boolean expand) {

	}

	@Override
	public void onStart(boolean expand) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ly_search_collapse:
			case R.id.ly_search_collapse_box:
				if (mSearchAnimator != null) {
					if (!mSearchAnimator.isToolBarExpand()) {
						mSearchAnimator.expand();
					}
				}
				break;
		}
	}
}
