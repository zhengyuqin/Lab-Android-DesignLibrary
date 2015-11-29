package com.inthecheesefactory.lab.designlibrary;

import android.animation.ValueAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class SearchViewAnimation implements ISearchBus {

	private static final int ANIMATION_DURATION = 500;
	private Toolbar mToolbar;
	private SearchViewLayout mSearchViewLayout;
	private SearchViewFakeLayout mSearchViewFakeLayout;
	private boolean mIsExpanded;
	private ValueAnimator mAnimation;
	private int toolbarExpandedHeight = 0;

	private List<AnimationCallback> animations = new ArrayList<>();

	public Toolbar getToolbar() {
		return mToolbar;
	}

	public void setToolbar(Toolbar toolbar) {
		mToolbar = toolbar;
	}

	public SearchViewLayout getSearchViewLayout() {
		return mSearchViewLayout;
	}

	public void registerAnimationCallback(AnimationCallback callback) {
		if (animations.contains(callback)) {
			return;
		}
		animations.add(callback);
	}

	public void setSearchViewLayout(SearchViewLayout searchViewLayout) {
		mSearchViewLayout = searchViewLayout;
		mSearchViewLayout.setSearchViewAnimation(this);
	}

	public SearchViewFakeLayout getSearchViewFakeLayout() {
		return mSearchViewFakeLayout;
	}

	public void setSearchViewFakeLayout(SearchViewFakeLayout searchViewFakeLayout) {
		mSearchViewFakeLayout = searchViewFakeLayout;
		mSearchViewFakeLayout.setSearchAnimator(this);
	}

	@Override
	public boolean isToolBarExpand() {
		return mIsExpanded;
	}

	/**
	 * 这里最好能做个转场动画，背景颜色渐变，但不在同一个view
	 */
	@Override
	public void expand() {
		if (mToolbar == null || mSearchViewFakeLayout == null || mSearchViewLayout == null) return;

		toggleToolbar(true);
		updateVisibility(true);

		mIsExpanded = true;

		AnimationUtils.expandFade(mSearchViewLayout, mSearchViewFakeLayout, ANIMATION_DURATION, animations);
		mSearchViewLayout.onExpand();
	}

	@Override
	public void collapse() {
		if (mToolbar == null || mSearchViewFakeLayout == null || mSearchViewLayout == null) return;
		updateVisibility(false);
		for (int i = 0; i < animations.size(); i++) {
			animations.get(i).onAnimationStart(false);
		}
		mSearchViewLayout.setVisibility(View.GONE);
		mSearchViewFakeLayout.setVisibility(View.VISIBLE);
		toggleToolbar(false);
		mIsExpanded = false;

		//	AnimationUtils.fadeIn1(mSearchViewFakeLayout, mSearchViewLayout, ANIMATION_DURATION);
		mSearchViewFakeLayout.onCollapse();
	}


	private void toggleToolbar(boolean expanding) {
		if (mToolbar == null) return;

		mToolbar.clearAnimation();

		if (expanding) {
			toolbarExpandedHeight = mToolbar.getHeight();
		}

		int toValue = expanding ? toolbarExpandedHeight * (-1) : 0;

//		mToolbar.animate().translationY(toValue).setDuration(ANIMATION_DURATION).start();

		AnimationUtils.animateHeight(
				mToolbar,
				expanding ? toolbarExpandedHeight : 0,
				expanding ? 0 : toolbarExpandedHeight,
				ANIMATION_DURATION);
	}

	/**
	 * toolbar 动画完成后,先更新search_view_layout中子控件的可见性
	 */
	private void updateVisibility(boolean expand) {
		if (mSearchViewFakeLayout == null || mSearchViewLayout == null) return;
		mSearchViewLayout.updateVisibility(expand);
		mSearchViewFakeLayout.updateVisibility(expand);
	}


	public interface OnToggleAnimationListener {
		void onFinish(boolean expand);

		void onStart(boolean expand);
	}

	/**
	 * 当fake_layout 长按时，需要 search_layout_edt响应
	 */
	public void performLongClick() {
		if (mSearchViewLayout == null) return;
		mSearchViewLayout.onPerformLongClick();
	}
}
