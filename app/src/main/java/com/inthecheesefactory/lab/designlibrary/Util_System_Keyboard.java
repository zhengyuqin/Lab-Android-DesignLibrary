package com.inthecheesefactory.lab.designlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author zyq 15-12-1
 */
public class Util_System_Keyboard {

	/**
	 * 显示键盘，使用postDelayed效果更加～
	 *
	 * @param context
	 * @param edt
	 */
	public static void showSoftKeyboard(Context context, EditText edt) {
		if (context == null || edt == null) return;
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edt, 0);
	}

	/**
	 * 隐藏键盘
	 *
	 * @param context
	 * @param edt
	 */
	public static void hideSoftKeyboard(Context context, EditText edt) {
		if (context == null || edt == null) return;
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
	}

	/**
	 * 隐藏键盘，使用Ibinder
	 *
	 * @param context
	 * @param binder
	 */
	public static void hideSoftKeyboardByIBinder(Context context, IBinder binder) {
		if (context == null || binder == null) {
			return;
		}
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(binder, 0);
	}

	/**
	 * 利用Activity获取到当前界面focus，利用IBinder隐藏键盘
	 *
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		if (activity != null) {
			final View view = activity.getCurrentFocus();
			if (view != null) {
				final IBinder binder = view.getWindowToken();
				if (binder != null) {
					final InputMethodManager imm = (InputMethodManager) activity.getApplicationContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(binder, 0);
				}
			}
		}
	}



}

