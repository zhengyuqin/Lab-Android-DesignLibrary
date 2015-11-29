package com.inthecheesefactory.lab.designlibrary;

/**
 * @author zyq 15-11-29
 */
public interface AnimationCallback {

	void onAnimationEnd(boolean expand);

	void onAnimationCancel(boolean expand);

	void onAnimationStart(boolean expand);

}
