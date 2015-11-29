package com.inthecheesefactory.lab.designlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.InputMethodManager;

/**
 * @author zyq 15-11-29
 */
public class Utils {

	public static int dpToPx(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round(dp * density);
	}

	public static void setPaddingAll(View v, int paddingInDp) {
		v.setPadding(
				dpToPx(v.getContext(), paddingInDp),
				dpToPx(v.getContext(), paddingInDp),
				dpToPx(v.getContext(), paddingInDp),
				dpToPx(v.getContext(), paddingInDp));

	}

	public static Point getSizeOfScreen(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}


	public static boolean showInputMethod(View view) {
		final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			return imm.showSoftInput(view, 0);
		}
		return false;
	}

	public static boolean hideInputMethod(View view) {
		final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		return false;
	}

	// AnimUtils

	public static final int DEFAULT_DURATION = -1;
	public static final int NO_DELAY = 0;

	public static class AnimationCallback {

		public void onAnimationEnd() {
		}

		public void onAnimationCancel() {
		}
	}

	public static void crossFadeViews(View fadeIn, View fadeOut, int duration) {
		fadeIn(fadeIn, duration);
		fadeOut(fadeOut, duration);
	}

	public static void fadeOut(View fadeOut, int duration) {
		fadeOut(fadeOut, duration, null);
	}

	public static void fadeOut(final View fadeOut, int durationMs,
	                           final AnimationCallback callback) {
		fadeOut.setAlpha(1);
		ViewPropertyAnimator animator = fadeOut.animate();
		animator.cancel();
		if (Build.VERSION.SDK_INT >= 16) {
			animator = animator.alpha(0).withLayer();
		} else {
			animator = animator.alpha(0);
		}
		animator.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				fadeOut.setVisibility(View.GONE);
				if (callback != null) {
					callback.onAnimationEnd();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				fadeOut.setVisibility(View.GONE);
				fadeOut.setAlpha(0);
				if (callback != null) {
					callback.onAnimationCancel();
				}
			}
		});
		if (durationMs != DEFAULT_DURATION) {
			animator.setDuration(durationMs);
		}
		animator.start();
	}

	public static void fadeIn(View fadeIn, int durationMs) {
		fadeIn(fadeIn, durationMs, NO_DELAY, null);
	}

	public static void fadeIn(final View fadeIn, int durationMs, int delay,
	                          final AnimationCallback callback) {
		fadeIn.setAlpha(0);
		ViewPropertyAnimator animator = fadeIn.animate();
		animator.cancel();
		animator.setStartDelay(delay);
		if (Build.VERSION.SDK_INT >= 16) {
			animator = animator.alpha(1).withLayer();
		} else {
			animator = animator.alpha(1);
		}
		animator.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				fadeIn.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				fadeIn.setAlpha(1);
				if (callback != null) {
					callback.onAnimationCancel();
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (callback != null) {
					callback.onAnimationEnd();
				}
			}
		});
		if (durationMs != DEFAULT_DURATION) {
			animator.setDuration(durationMs);
		}
		animator.start();
	}

	public static void animateHeight(final View view, int from, int to, int duration) {
		boolean expanding = to > from;

		ValueAnimator anim = ValueAnimator.ofInt(from, to);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
				layoutParams.height = val;
				view.setLayoutParams(layoutParams);
			}
		});
		anim.setDuration(duration);
		anim.start();

	}
}
