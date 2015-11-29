package com.inthecheesefactory.lab.designlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class ScrollableFragment extends Fragment implements TabsLayout.onTabSelectedListener {

	private ScrollableLayout mScrollableLayout;
	private TabsLayout mTabsLayout;
	private ViewPager mViewPager;
	private MyPagerAdapter mMyAdapter;
	private List<String> tabsList;
	private List<Fragment> fragments;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragments = new ArrayList<>();
		fragments.add(new ScrollableChildFragment());
		fragments.add(new ScrollableChildFragment());
		fragments.add(new ScrollableChildFragment());
		mMyAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);
		tabsList = new ArrayList<>();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scrollablelayout, container, false);
		mScrollableLayout = (ScrollableLayout) view.findViewById(R.id.sl_fragment_detail);
		mScrollableLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
			@Override
			public void onScroll(int currentY, int maxY) {
				//ViewHelper.setTranslationY(mHeader, (float) (currentY * 0.5));
			}
		});
		mScrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableChildFragment) fragments.get(1));
		mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment_detail_app_cooperation);
		mViewPager.setAdapter(mMyAdapter);
		mViewPager.setCurrentItem(1);
		mViewPager.setOffscreenPageLimit(2);


		mTabsLayout = (TabsLayout) view.findViewById(R.id.tl_fragment_gift_detail);
		tabsList.add("转发：");
		tabsList.add("评论：");
		tabsList.add("赞：");

		mTabsLayout.setTabSelectedListener(this);
		mTabsLayout.setTabs(tabsList);
		mTabsLayout.setViewPager(mViewPager);
		mTabsLayout.setItemSelected(1);
		return view;
	}

	@Override
	public void onTabSelectListener(View view, int position) {

		mScrollableLayout.getHelper().setCurrentScrollableContainer((ScrollableChildFragment)fragments.get(position));
	}
}
