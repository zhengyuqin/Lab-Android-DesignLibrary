package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * @author zyq 15-12-1
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements IBaseAdapter<T> {

	protected static final int TAG_POSITION = 0xFFF11133;
	protected static final int TAG_URL = 0xffff1111;

	protected List<T> mListData;
	protected Context mContext;
	protected LayoutInflater mLayoutInflater;

	public BaseListAdapter(Context context, List<T> objects) {
		mContext = context;
		mListData = objects;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public BaseListAdapter(Context context, T[] list) {
		this(context, Arrays.asList(list));
	}

	protected void bindImageViewWithUrl(ImageView imageView, String imgUrl, int stubResource) {
		try {
			final Object iconTag = imageView.getTag(TAG_URL);
			if (iconTag != null && !iconTag.equals(imgUrl)) {
				imageView.setImageResource(stubResource);
			}
			imageView.setTag(TAG_URL, imgUrl);
		} catch (Exception e) {

		}
	}

	public void onDestroy() {
		if (mListData != null) {
			mListData.clear();
		}
	}

	public void updateData(List<T> newData) {
		mListData.clear();
		mListData.addAll(newData);
	}

	@Override
	public void notifyDataChanged() {
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public T getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
