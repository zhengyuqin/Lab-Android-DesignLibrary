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
public class ChildViewPagerFragment extends Fragment {

	private List<Fragment> mFragments;
	private ViewPager mViewPager;
	private MyPagerAdapter mMyPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFragments = new ArrayList<>();
		mFragments.add(new SearchFakeFragment());
		mFragments.add(new FragmentDetail());
		mMyPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.childviewpagerfragment, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.child_viewpager);
		mViewPager.setAdapter(mMyPagerAdapter);
		return view;
	}


}
