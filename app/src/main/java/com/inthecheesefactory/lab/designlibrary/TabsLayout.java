package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class TabsLayout extends RelativeLayout {

	private final Rect mRect;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private LayoutInflater mInflater;
	private List<String> mTabs;
	private onTabSelectedListener listener;


	{
		mRect = new Rect();
	}

	public TabsLayout(Context context) {
		super(context);
		init(context, null);
	}

	public TabsLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attributeSet) {
		mInflater = LayoutInflater.from(context);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	}

	public void setViewPager(ViewPager viewPager) {
		if (mPagerAdapter != null) {
			removeAllViews();
		}
		mPager = viewPager;
		mPagerAdapter = mPager.getAdapter();
		populateViews();
		setItemSelected(1);
	}

	private void populateViews() {
		final int count = mPagerAdapter != null ? mPagerAdapter.getCount() : 0;
		if (count < 0) {
			return;
		}

		CheckedTextView transmit = new CheckedTextView(getContext());
		RelativeLayout.LayoutParams transmit_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		transmit_Params.setMargins(22, 22, 18, 22);
		transmit_Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		transmit_Params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		transmit.setLayoutParams(transmit_Params);
		transmit.setTextColor(Color.parseColor("#a0a0a0"));
		transmit.setText(getTab(0));
		transmit.setTextSize(12);
		transmit.setId(1);
		setTabClick(transmit, 0);


		View dividerView = new View(getContext());
		RelativeLayout.LayoutParams divider_Params = new RelativeLayout.LayoutParams(8,
				26);
		divider_Params.addRule(RelativeLayout.RIGHT_OF, transmit.getId());
		divider_Params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		dividerView.setLayoutParams(divider_Params);
		dividerView.setBackgroundColor(0xc9d4de);
		dividerView.setId(2);


		CheckedTextView comment = new CheckedTextView(getContext());
		RelativeLayout.LayoutParams comment_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		comment_Params.setMargins(18, 22, 22, 22);
		comment_Params.addRule(RelativeLayout.RIGHT_OF, dividerView.getId());
		comment_Params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		comment.setLayoutParams(comment_Params);
		comment.setTextColor(Color.parseColor("#28d4a9"));
		comment.setText(getTab(1));
		comment.setTextSize(12);
		comment.setId(3);
		setTabClick(comment, 1);

		TextView like = new TextView(getContext());
		RelativeLayout.LayoutParams like_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		like_Params.setMargins(0, 22, 22, 22);
		like_Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		like_Params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		like_Params.addRule(RelativeLayout.ALIGN_BASELINE, transmit.getId());
		like.setLayoutParams(like_Params);
		like.setTextColor(Color.parseColor("#a0a0a0"));
		like.setTextSize(12);
		like.setText(getTab(2));
		setTabClick(like, 2);

		addView(transmit, transmit_Params);
		addView(dividerView, divider_Params);
		addView(comment, comment_Params);
		addView(like, like_Params);
	}

	private void setTabClick(final View view, final int position) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < getChildCount(); i++) {
					View view = getChildAt(i);
					if (view != v && view instanceof TextView) {
						((TextView) view).setTextColor(Color.parseColor("#a0a0a0"));
					}
				}
				if (v instanceof TextView) {
					((TextView) v).setTextColor(Color.parseColor("#28d4a9"));
				}
				mPager.setCurrentItem(position);
				if (listener != null) {
					listener.onTabSelectListener(view, position);
				}
			}
		});
	}

	public void setTabSelectedListener(onTabSelectedListener listener) {
		this.listener = listener;
	}

	public interface onTabSelectedListener {

		void onTabSelectListener(View view, final int position);
	}

	public void setItemSelected(int position) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			if (view instanceof CheckedTextView) {
				CheckedTextView ctv = (CheckedTextView) view;
				if (i > 1) {
					ctv.setChecked((i - 1) == position);
				} else {
					ctv.setChecked(i == position);
				}
			}
		}
	}

	private String getTab(int position) {
		if (mTabs != null) {
			return mTabs.get(position);
		}
		return "";
	}

	private CheckedTextView createTransmitView() {
		return (CheckedTextView) mInflater.inflate(R.layout.view_tab_transmit_item, null);
	}

	private CheckedTextView createCommentView() {
		return (CheckedTextView) mInflater.inflate(R.layout.view_tab_comments_item, null);
	}

	private TextView createLikeView() {
		return (TextView) mInflater.inflate(R.layout.view_tab_like_item, null);
	}

	private ImageView createDividerView() {
		return (ImageView) mInflater.inflate(R.layout.view_tab_divider_item, null);
	}


	public void setTabs(List<String> tabs) {
		mTabs = tabs;
	}

	public int dip2px(float dpValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
