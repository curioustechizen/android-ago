package com.github.curioustechizen.ago.sample;

import android.content.Context;
import android.util.AttributeSet;

import com.github.curioustechizen.ago.RelativeTimeTextView;

/**
 * A custom RTTV that adds a prefix to the displayed relative time string
 */
public class PrefixRttv extends RelativeTimeTextView {
    public PrefixRttv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrefixRttv(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected CharSequence getRelativeTimeDisplayString(long referenceTime, long now) {
        CharSequence displayString = super.getRelativeTimeDisplayString(referenceTime, now);
        return getResources().getString(R.string.format_prefix_custom_rttv, displayString);
    }
}
