package com.inthecheesefactory.lab.designlibrary;

import java.util.List;

/**
 * @author zyq 15-12-1
 */
public interface IBaseAdapter<T> {

	public void onDestroy();

	public int getCount();

	public T getItem(int position);

	public void updateData(List<T> newData);

	/**
	 * notifyDataSetChanged
	 */
	public void notifyDataChanged();

}

