
package com.github.curioustechizen.ago;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.github.curioustechizen.ago.R;


/**
 * A {@code TextView} that, given a reference time, renders that time as a time period relative to the current time.
 * @author Kiran Rao
 * @see #setReferenceTime(long)
 *
 */
public class RelativeTimeTextView extends TextView {

    private long mReferenceTime;
    private String mText;
    private Handler mHandler = new Handler();

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long difference = Math.abs(System.currentTimeMillis() - mReferenceTime);
            long interval = DateUtils.MINUTE_IN_MILLIS;
            if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS;
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS;
            } else if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS;
            }
            updateTextDisplay();
            mHandler.postDelayed(this, interval);
        }
    };

    public RelativeTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RelativeTimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.RelativeTimeTextView, 0, 0);
        try {
            mText = a.getString(R.styleable.RelativeTimeTextView_reference_time);
        } finally {
            a.recycle();
        }

        try {
            mReferenceTime = Long.valueOf(mText);
        } catch (NumberFormatException nfe) {
            mReferenceTime = -1L;
        }

    }

    /**
     * Sets the reference time for this view. At any moment, the view will render a relative time period relative to the time set here.
     * <p/>
     * This value can also be set with the XML attribute {@code reference_time}
     * @param referenceTime The timestamp (in milliseconds since epoch) that will be the reference point for this view.
     */
    public void setReferenceTime(long referenceTime) {
        this.mReferenceTime = referenceTime;
        updateTextDisplay();
    }

    private void updateTextDisplay() {
        /*
         * TODO: Perform validation
         */
        if (this.mReferenceTime == -1L)
            return;
        setText(getRelativeTimeDisplayString());
    }

    private CharSequence getRelativeTimeDisplayString() {
        long now = System.currentTimeMillis();
        long difference = now - mReferenceTime; 
        return (difference >= 0 &&  difference<=DateUtils.MINUTE_IN_MILLIS) ? 
                getResources().getString(R.string.just_now): 
                DateUtils.getRelativeTimeSpanString(
                    mReferenceTime,
                    now,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startTaskForPeriodicallyUpdatingRelativeTime();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopTaskForPeriodicallyUpdatingRelativeTime();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopTaskForPeriodicallyUpdatingRelativeTime();
        } else {
            startTaskForPeriodicallyUpdatingRelativeTime();
        }
    }

    private void startTaskForPeriodicallyUpdatingRelativeTime() {
        mHandler.post(mUpdateTimeTask);
    }

    private void stopTaskForPeriodicallyUpdatingRelativeTime() {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.referenceTime = mReferenceTime;
        return ss;
    }
    
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        mReferenceTime = ss.referenceTime;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    public static class SavedState extends BaseSavedState {

        private long referenceTime;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(referenceTime);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        
        private SavedState(Parcel in) {
            super(in);
            referenceTime = in.readLong();
        }
    }
}
