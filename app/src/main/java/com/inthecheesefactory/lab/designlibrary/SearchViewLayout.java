package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author zyq 15-11-29
 */
public class SearchViewLayout extends FrameLayout {

	public static final int ANIMATION_DURATION = 500;
	private static final String TAG = "searchViewLayout";

	private ViewGroup mExpanded;
	private EditText mSearchEditText;
	private View mImgExpandedSearchCancel;
	private TextView mTvExpandSearchCancel;
	private SearchViewAnimation mSearchViewAnimation;
	private SearchListener mSearchListener;

	public SearchViewAnimation getSearchViewAnimation() {
		return mSearchViewAnimation;
	}

	public void setSearchViewAnimation(SearchViewAnimation searchViewAnimation) {
		mSearchViewAnimation = searchViewAnimation;
	}


	public interface SearchListener {
		void onFinished(String searchKeyWord);
	}

	public SearchViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public void setSearchListener(SearchListener searchListener) {
		mSearchListener = searchListener;
	}

	@Override
	protected void onFinishInflate() {
		mExpanded = (ViewGroup) findViewById(R.id.search_expanded_root);
		mSearchEditText = (EditText) mExpanded.findViewById(R.id.search_expanded_edit_text);
		mImgExpandedSearchCancel = mExpanded.findViewById(R.id.search_expanded_img_cancel);
		mTvExpandSearchCancel = (TextView) findViewById(R.id.search_expanded_txt_cancel);

		mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Log.d(TAG, "焦点发生变化");
				if (hasFocus) {
					Utils.showInputMethod(v);
				} else {
					Utils.hideInputMethod(v);
				}
			}
		});

		mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					callSearchListener();
					Utils.hideInputMethod(v);
					return true;
				}
				return false;
			}
		});

		mSearchEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (mSearchEditText.getText().length() > 0) {
					Utils.fadeIn(mImgExpandedSearchCancel, ANIMATION_DURATION);
				} else {
					Utils.fadeOut(mImgExpandedSearchCancel, ANIMATION_DURATION);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mTvExpandSearchCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSearchViewAnimation != null) {
					mSearchViewAnimation.collapse();
				}
			}
		});
		mImgExpandedSearchCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSearchEditText.setText(null);
			}
		});
		super.onFinishInflate();
	}

	private void callSearchListener() {
		Editable editable = mSearchEditText.getText();
		if (editable != null && editable.length() > 0) {
			if (mSearchListener != null) {
				mSearchListener.onFinished(editable.toString());
			}
		}
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		if (mSearchEditTextLayoutListener != null) {
			if (mSearchEditTextLayoutListener.onKey(this, event.getKeyCode(), event)) {
				return true;
			}
		}
		return super.dispatchKeyEventPreIme(event);
	}


	/**
	 * If the search term is empty and the user closes the soft keyboard, close the search UI.
	 */
	private final View.OnKeyListener mSearchEditTextLayoutListener = new View.OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN &&
					mSearchViewAnimation.isToolBarExpand()) {
				Log.d(TAG, "onKey()-->隐藏了焦点");
				boolean keyboardHidden = Utils.hideInputMethod(v);
				if (keyboardHidden) return true;
				if (mSearchViewAnimation != null) {
					mSearchViewAnimation.collapse();
				}
				return true;
			}
			return false;
		}
	};

	public void updateVisibility(boolean isExpand) {
		//mBackButtonView.setVisibility(isExpand ? VISIBLE : GONE);
	}

	/**
	 * 　动画显示searchViewLayout时候子控件变化
	 */
	public void onExpand() {
		if (mSearchEditText != null) {
			mSearchEditText.requestFocus();
		}
	}

	public void onPerformLongClick() {
		mSearchEditText.performLongClick();
	}


}
