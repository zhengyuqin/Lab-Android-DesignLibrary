package com.inthecheesefactory.lab.designlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class ScrollableChildFragment extends Fragment implements ScrollableHelper.ScrollableContainer,
		OnRecyclerItemClick {

	List<String> data = new ArrayList<>();
	MyAdapter mMyAdapter;
	RecyclerView mRecyclerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < 20; i++) {
			data.add("Text" + String.valueOf(i));
		}
		mMyAdapter = new MyAdapter(getActivity(), data, this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scrollablefragment, container, false);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.layout_recycler);
		mRecyclerView.setAdapter(mMyAdapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}


	@Override
	public void onItemClick(View view, int position, Object object) {
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).pushFragmentToActivityFragmentManager(new FragmentDetail());
		}
	}

	@Override
	public View getScrollableView() {
		return mRecyclerView;
	}
}
