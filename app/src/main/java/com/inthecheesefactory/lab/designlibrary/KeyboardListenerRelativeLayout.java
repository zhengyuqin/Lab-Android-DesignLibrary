package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author zyq 15-12-1
 */
public class KeyboardListenerRelativeLayout extends RelativeLayout implements View.OnClickListener {

	public final static String TAG = "keyboardListener";
	public final static int EMOTICON_SHOW = 0;
	public final static int KEY_SHOW = 1;
	public boolean mTypeEmoticonShow = false;
	public boolean mTypeKeyBoardShow = true;
	private OnSoftKeyBoardChangeListener mListener;
	private OnKeyBoardListener mOnKeyBoradListener;
	private FrameLayout mFrameLayout;
	private TextView mTvSend;
	private EditText mEdtComment;
	private ImageView mImgEmoticon;
	private View mTotalView;

	private Runnable mShowImeRunnable = new Runnable() {
		@Override
		public void run() {
			Util_System_Keyboard.showSoftKeyboard(getContext(), mEdtComment);
		}
	};

	private Runnable mHideImeRunnable = new Runnable() {
		@Override
		public void run() {
			Util_System_Keyboard.hideSoftKeyboard(getContext(), mEdtComment);
		}
	};

	public KeyboardListenerRelativeLayout(Context context) {
		super(context);
	}

	public KeyboardListenerRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyboardListenerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mFrameLayout = (FrameLayout) findViewById(R.id.comment_emoticon);
		int keyboardHeight = getInt(getContext(), "settingfile", "height", -1);
		if (keyboardHeight > 150) {
			ViewGroup.LayoutParams lp = mFrameLayout.getLayoutParams();
			lp.height = keyboardHeight;
			mFrameLayout.setLayoutParams(lp);
		}

		mTvSend = (TextView) findViewById(R.id.tv_gift_comment_send);
		mEdtComment = (EditText) findViewById(R.id.edt_comment);
		mImgEmoticon = (ImageView) findViewById(R.id.img_emoji);

		mEdtComment.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Log.d(TAG, "焦点发生变化");
				if (hasFocus) {

				} else {

				}
			}
		});

		mEdtComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				return false;
			}
		});

		mEdtComment.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});


	}

	public void setListener(OnSoftKeyBoardChangeListener listener) {
		mListener = listener;
	}

	public void setOnKeyBoradListener(OnKeyBoardListener listener) {
		mOnKeyBoradListener = listener;
	}
//	@Override
//	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		super.onSizeChanged(w, h, oldw, oldh);
//		if (mListener != null) {
//			mListener.onSoftKeyBoardChange(oldh > h ? KEY_SHOW : S);
//		}
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.img_emoji:
				if (!mTypeEmoticonShow) {
					mImgEmoticon.setBackgroundResource(R.drawable.emoji_0x0f602);
					hideKeyBoard();
					showEmotion();
				} else {
					mImgEmoticon.setBackgroundResource(R.drawable.emoji_0x0f601);
					hideEmotion();
					showKeyBoard();
				}
				break;
			case R.id.tv_gift_comment_send:
				String comment = mEdtComment.getText().toString();
				if (!TextUtils.isEmpty(comment)) {
					if (mOnKeyBoradListener != null) {
						mOnKeyBoradListener.onCommentSend(comment);
					}
				} else {
					//Util_Toast.toast("评论不能为空");
				}
				break;
		}
	}

	public interface OnSoftKeyBoardChangeListener {
		public void onSoftKeyBoardChange(final int status);
	}

	public interface OnKeyBoardListener {
		public void onCommentSend(String text);
	}

	public void hideKeyBoard() {
		mTypeKeyBoardShow = false;
		mTypeEmoticonShow = false;
		if (mEdtComment != null) {
			removeCallbacks(mShowImeRunnable);
			post(mHideImeRunnable);
		}
	}

	public void showKeyBoard() {
		mTypeKeyBoardShow = true;
		mTypeEmoticonShow = false;
		if (mEdtComment != null) {
			removeCallbacks(mHideImeRunnable);
			mEdtComment.requestFocus();
			post(mShowImeRunnable);
		}
	}

	public void showEmotion() {
		mTypeKeyBoardShow = false;
		mTypeEmoticonShow = true;
		if (mEdtComment != null) {
			mEdtComment.requestFocus();
		}
		mFrameLayout.setVisibility(View.VISIBLE);
	}

	public void hideEmotion() {
		mTypeEmoticonShow = false;
		mTypeKeyBoardShow = false;
		mFrameLayout.setVisibility(View.GONE);
	}


	public static int getInt(Context context, String file, String key, int defValue) {
		try {
			if (context == null) {
				return defValue;
			}
			final SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
			return sp.getInt(key, defValue);
		} catch (Throwable e) {

		}
		return defValue;
	}

	public static void putInt(Context context, String file, String key, int value) {
		try {
			if (context == null) {
				return;
			}
			SharedPreferences sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
			sp.edit().putInt(key, value).commit();
		} catch (Throwable e) {

		}
	}
}

