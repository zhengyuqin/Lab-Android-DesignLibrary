package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TextViewHolder> {

	private List<String> mData;
	private Context mContext;
	private OnRecyclerItemClick mOnRecyclerItemClick;

	public MyAdapter(Context context, List<String> data, OnRecyclerItemClick itemClick) {
		super();
		mData = data;
		mContext = context;
		mOnRecyclerItemClick = itemClick;
	}

	@Override
	public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.lisitem_test_recycler, parent, false);

		return new TextViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TextViewHolder holder, int position) {
		holder.tv.setText(mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView tv;

		public TextViewHolder(View view) {
			super(view);
			tv = (TextView) view.findViewById(R.id.text);
			tv.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mOnRecyclerItemClick != null) {
				mOnRecyclerItemClick.onItemClick(v, getAdapterPosition(), "");
			}
		}
	}
}
