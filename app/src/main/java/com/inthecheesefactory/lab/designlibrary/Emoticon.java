package com.inthecheesefactory.lab.designlibrary;

import java.io.Serializable;

/**
 * @author zyq 15-12-1
 */
public class Emoticon implements Serializable {

	private String mPhrase;//表情使用的替代文字
	private String mImageName;//表情名称
	private int mResId;
	private int mStart;
	private int mEnd;

	public String getPhrase() {
		return mPhrase;
	}

	public void setPhrase(String phrase) {
		mPhrase = phrase;
	}

	public String getImageName() {
		return mImageName;
	}

	public void setImageName(String imageName) {
		mImageName = imageName;
	}

	public int getResId() {
		return mResId;
	}

	public void setResId(int resId) {
		mResId = resId;
	}

	public int getStart() {
		return mStart;
	}

	public void setStart(int start) {
		mStart = start;
	}

	public int getEnd() {
		return mEnd;
	}

	public void setEnd(int end) {
		mEnd = end;
	}
}