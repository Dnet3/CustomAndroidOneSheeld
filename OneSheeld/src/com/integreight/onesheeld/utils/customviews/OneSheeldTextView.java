package com.integreight.onesheeld.utils.customviews;

import com.integreight.onesheeld.OneSheeldApplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OneSheeldTextView extends TextView {

	public OneSheeldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(
				((OneSheeldApplication) context.getApplicationContext()).appFont,
				getTypeface() == null ? Typeface.NORMAL : getTypeface()
						.getStyle() == Typeface.BOLD ? Typeface.BOLD
						: Typeface.NORMAL);
		setPadding(getPaddingLeft(),
				getPaddingTop()
						- ((int) (1.5 * context.getResources()
								.getDisplayMetrics().density + .5f)),
				getPaddingRight(), getBottom());
	}

}