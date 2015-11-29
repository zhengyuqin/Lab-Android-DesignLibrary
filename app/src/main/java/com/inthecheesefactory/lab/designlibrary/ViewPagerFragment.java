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
public class ViewPagerFragment extends Fragment {

	private ViewPager mViewPager;
	private List<Fragment> childFragments;
	private MyPagerAdapter mMyPagerAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).showHeaderView(true);
		}
		childFragments = new ArrayList<>();
		childFragments.add(new Fragment1());
		childFragments.add(new Fragment2());
		childFragments.add(new FragmentDetail());
		mMyPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), childFragments);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.viewpager, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(mMyPagerAdapter);
		return view;
	}

}
