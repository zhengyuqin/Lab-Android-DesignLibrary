package com.inthecheesefactory.lab.designlibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zyq 15-12-1
 */
public class EmoticonUtils {

	private static Pattern sPattern;

	/**
	 * 获取图片名称获取图片的资源id的方法
	 *
	 * @param imageName
	 * @return
	 */
	public static int getResourceByReflect(String imageName) {
//        Class drawable = com.oplay.android.R.drawable.class;
//        Field field = null;
		int r_id = com.inthecheesefactory.lab.designlibrary.R.drawable.emoji_0x0f602;
		try {
//            field = drawable.getDeclaredField(imageName);
//            r_id = field.getInt(drawable);
			Field f = com.inthecheesefactory.lab.designlibrary.R.drawable.class.getDeclaredField(imageName);
			r_id = f.getInt(com.inthecheesefactory.lab.designlibrary.R.drawable.class);
		} catch (Exception e) {
//                r_id=R.drawable.b_nothing;

		}
		return r_id;
	}

	public static List<Emoticon> getAllEmoticonsLocation(Spanned text) {
		if (sPattern == null) {
			sPattern = Pattern.compile("(\\[0x\\w{4,6}\\])");
		}
		Matcher mat = sPattern.matcher(text);
		List<Emoticon> emoticons = new ArrayList<Emoticon>(mat.groupCount());
		while (mat.find()) {
			try {
				Emoticon emoticon = new Emoticon();
				emoticon.setStart(mat.start());
				emoticon.setEnd(mat.end());
				String group = mat.group();
				emoticon.setPhrase(group);
				String imageName = "emoji_" + group.substring(1, group.length() - 1);
				int resId = getResourceByReflect(imageName);
				emoticon.setImageName(imageName);
				emoticon.setResId(resId);
				emoticons.add(emoticon);
			} catch (Exception e) {

			}
		}

		return emoticons;
	}

	public static SpannableString txtToImg(Context context, Emoticon emoticon, String content) {
		final SpannableString ss = new SpannableString(content);
		int starts = 0;
		int end = 0;

		if (content.indexOf("[", starts) != -1 && content.indexOf("]", end) != -1) {
			starts = content.indexOf("[", starts);
			end = content.indexOf("]", end);
//            String phrase = content.substring(starts, end + 1);
			try {
				final Field f = com.inthecheesefactory.lab.designlibrary.R.drawable.class.getDeclaredField(emoticon.getImageName());
				final int i = f.getInt(com.inthecheesefactory.lab.designlibrary.R.drawable.class);
				final Drawable drawable = context.getResources().getDrawable(i);
				if (drawable != null) {
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					final ImageSpan span = new ImageSpan(drawable);
					ss.setSpan(span, starts, end + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			} catch (Throwable e) {

			}
		}
		return ss;
	}

}