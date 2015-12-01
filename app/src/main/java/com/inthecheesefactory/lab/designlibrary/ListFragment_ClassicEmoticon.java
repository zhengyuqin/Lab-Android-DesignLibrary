package com.inthecheesefactory.lab.designlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq 15-12-1
 */
public class ListFragment_ClassicEmoticon extends Fragment implements AdapterView.OnItemClickListener {

	private List<Emoticon> mEmoticonList;
	private GridView mGridView;
	private EmoticonAdapter_Classic mAdapter;
	private OnClickEmoticonListener mListener;

	public static ListFragment_ClassicEmoticon newInstance() {
		ListFragment_ClassicEmoticon fragment = new ListFragment_ClassicEmoticon();
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initEmoticonList();
		mAdapter = new EmoticonAdapter_Classic(getActivity(), mEmoticonList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_gv_emoticon_classic, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mGridView = (GridView) view.findViewById(R.id.gv_essay_detail_emoticon);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		removeListener();
		mEmoticonList.clear();
		mEmoticonList = null;
	}

	private void initEmoticonList() {
		mEmoticonList = new ArrayList<Emoticon>();
		final List<String> resNames = new ArrayList<String>();
		final Field[] drawables = com.inthecheesefactory.lab.designlibrary.R.drawable.class.getFields();
		for (Field f : drawables) {
			try {
				final String name = f.getName();
				if (name.contains("emoji_0x1") || name.contains("emoji_0x2") || name.contains("emoji_0x0")) {
					final String[] nameAndValue = name.split("_");
					resNames.add(name);
					final Emoticon emoticon = new Emoticon();
					emoticon.setImageName(name);
					emoticon.setPhrase("[" + nameAndValue[1] + "]");
					emoticon.setResId(EmoticonUtils.getResourceByReflect(name));
					mEmoticonList.add(emoticon);
				}
			} catch (Exception e) {

			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mListener != null) {
			final Emoticon emoticon = mEmoticonList.get(position);
			mListener.onClickEmoticons(emoticon);
		}
	}

	public void setListener(OnClickEmoticonListener listener) {
		mListener = listener;
	}


	public void removeListener() {
		mListener = null;
	}

	public interface OnClickEmoticonListener {
		public void onClickEmoticons(Emoticon emoticon);
	}
}
