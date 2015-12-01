package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @author zyq 15-12-1
 */
public class EmoticonAdapter_Classic extends BaseListAdapter<Emoticon> {

	public EmoticonAdapter_Classic(Context context, List<Emoticon> objects) {
		super(context, objects);
	}

	@Override
	public void onDestroy() {
		if (mListData != null) {
			mListData.clear();
		}
		mListData = null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.griditem_emoticon_classic, null);
			holder = new ViewHolder();
			holder.emo = (ImageView) convertView.findViewById(R.id.emoticonIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Emoticon emoticon = mListData.get(position);
		holder.emo.setImageResource(emoticon.getResId());

		return convertView;
	}

	static class ViewHolder {
		ImageView emo;
	}
}