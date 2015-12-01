package com.inthecheesefactory.lab.designlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zyq 15-11-30
 */
public class SearchFakeFragment extends Fragment {

	private SearchViewFakeLayout mSearchViewHeader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.searchfakelayout, container, false);

		SearchViewLayout realSearchHeader = (SearchViewLayout) ((MainActivity) getActivity()).getSearchViewLayout();
		final Toolbar toolbar = ((MainActivity) getActivity()).getToolbar();
		mSearchViewHeader =(SearchViewFakeLayout) view.findViewById(R.id.search_view_container);
		SearchViewAnimation animation = new SearchViewAnimation();
		animation.setSearchViewFakeLayout(mSearchViewHeader);
		animation.setSearchViewLayout(realSearchHeader);
		animation.setToolbar(toolbar);
		animation.registerAnimationCallback((MainActivity) getActivity());
		animation.registerAnimationCallback(new AnimationCallback() {
			@Override
			public void onAnimationEnd(boolean expand) {
				if (expand) {
					/**
					 * recyclerView hide item 没什么效果，只能设置height;
					 */
					ViewGroup.LayoutParams lp = mSearchViewHeader.getLayoutParams();
					lp.height = 0;
					mSearchViewHeader.setLayoutParams(lp);
				}
			}

			@Override
			public void onAnimationCancel(boolean expand) {

			}

			@Override
			public void onAnimationStart(boolean expand) {
				if (!expand) {
					ViewGroup.LayoutParams lp = mSearchViewHeader.getLayoutParams();
					lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
					mSearchViewHeader.setLayoutParams(lp);
				}
			}
		});
		return view;
	}
}
