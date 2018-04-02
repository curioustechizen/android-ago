package com.github.curioustechizen.ago.sample;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;

import com.github.curioustechizen.ago.RelativeTimeTextView;

/**
 * A fully custom RTTV that does not call DateUtils at all. Instead, it uses its own logic to display the relative time string
 */
public class FullyCustomRttv extends RelativeTimeTextView {
    public FullyCustomRttv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullyCustomRttv(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected CharSequence getRelativeTimeDisplayString(long referenceTime, long now) {
        int resourceId = 0;
        long difference = referenceTime - now;
        if(Math.abs(difference) < DateUtils.MINUTE_IN_MILLIS) resourceId = R.string.fully_custom_rttv_now;
        else if(referenceTime > now) resourceId = R.string.fully_custom_rttv_future;
        else if(referenceTime < now) resourceId = R.string.fully_custom_rttv_past;

        return getResources().getString(resourceId);
    }
}
