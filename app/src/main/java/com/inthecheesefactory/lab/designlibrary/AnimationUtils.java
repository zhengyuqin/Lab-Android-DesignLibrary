package com.inthecheesefactory.lab.designlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * @author zyq 15-11-29
 */
public class AnimationUtils {
	public static int dpToPx(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round(dp * density);
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


	public static void expandFade(final View fadeIn, final View fadeOut, int durationMs, final List<AnimationCallback>
			animations) {
		fadeOut.setAlpha(1);
		ViewPropertyAnimator animator = fadeOut.animate();
		animator.cancel();
		if (Build.VERSION.SDK_INT >= 16) {
			animator = animator.alpha(1).withLayer();
		} else {
			animator = animator.alpha(1);
		}
		animator.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				fadeIn.setVisibility(View.VISIBLE);
				fadeOut.setVisibility(View.GONE);
				for (int i = 0; i < animations.size(); i++) {
					animations.get(i).onAnimationEnd(true);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				super.onAnimationCancel(animation);
				for (int i = 0; i < animations.size(); i++) {
					animations.get(i).onAnimationStart(true);
				}
			}

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				for (int i = 0; i < animations.size(); i++) {
					animations.get(i).onAnimationStart(true);
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
