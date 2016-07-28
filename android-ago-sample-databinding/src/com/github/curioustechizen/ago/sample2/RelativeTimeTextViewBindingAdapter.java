package com.github.curioustechizen.ago.sample2;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import com.github.curioustechizen.ago.RelativeTimeTextView;

@BindingMethods({
        @BindingMethod(type = RelativeTimeTextView.class, attribute = "rttv:relative_time_prefix", method = "setPrefix"),
        @BindingMethod(type = RelativeTimeTextView.class, attribute = "rttv:relative_time_suffix", method = "setSuffix"),
})
public class RelativeTimeTextViewBindingAdapter {
    @BindingAdapter("rttv:reference_time")
    public static void setReferenceTime(RelativeTimeTextView view, long time) {
        view.setReferenceTime(time);
    }
}