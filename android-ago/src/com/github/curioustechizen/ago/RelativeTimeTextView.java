
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

import java.lang.ref.WeakReference;


/**
 * A {@code TextView} that, given a reference time, renders that time as a time period relative to the current time.
 * @author Kiran Rao
 * @see #setReferenceTime(long)
 *
 */
public class RelativeTimeTextView extends TextView {

    private static final long INITIAL_UPDATE_INTERVAL = DateUtils.MINUTE_IN_MILLIS;

    private long mReferenceTime;
    private String mPrefix;
    private String mSuffix;
    private Handler mHandler = new Handler();
    private UpdateTimeRunnable mUpdateTimeTask;
    private boolean isUpdateTaskRunning = false;

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
        String referenceTimeText;
        try {
            referenceTimeText = a.getString(R.styleable.RelativeTimeTextView_reference_time);
            mPrefix = a.getString(R.styleable.RelativeTimeTextView_relative_time_prefix);
            mSuffix = a.getString(R.styleable.RelativeTimeTextView_relative_time_suffix);

            mPrefix = mPrefix == null ? "" : mPrefix;
            mSuffix = mSuffix == null ? "" : mSuffix;
        } finally {
            a.recycle();
        }

        try {
            mReferenceTime = Long.valueOf(referenceTimeText);
        } catch (NumberFormatException nfe) {
        	/*
        	 * TODO: Better exception handling
        	 */
            mReferenceTime = -1L;
        }

        setReferenceTime(mReferenceTime);

    }

    /**
     * Returns prefix
     * @return
     */
    @Deprecated
    public String getPrefix() {
        return this.mPrefix;
    }

    /**
     * @deprecated This method is not suitable for i18n.
     * Instead, override {@link #getRelativeTimeDisplayString(long, long)}
     * <p/>
     * String to be attached before the reference time
     * @param prefix
     *
     * Example:
     * [prefix] in XX minutes
     */
    @Deprecated
    public void setPrefix(String prefix) {
        this.mPrefix = prefix;
        updateTextDisplay();
    }

    /**
     * Returns suffix
     * @return
     */
    @Deprecated
    public String getSuffix() {
        return this.mSuffix;
    }

    /**
     *
     * @deprecated This method is not suitable for i18n.
     * Instead, override {@link #getRelativeTimeDisplayString(long, long)}
     * <p/>
     * String to be attached after the reference time
     * @param suffix
     *
     * Example:
     * in XX minutes [suffix]
     */
    @Deprecated
    public void setSuffix(String suffix) {
        this.mSuffix = suffix;
        updateTextDisplay();
    }

    /**
     * Sets the reference time for this view. At any moment, the view will render a relative time period relative to the time set here.
     * <p/>
     * This value can also be set with the XML attribute {@code reference_time}
     * @param referenceTime The timestamp (in milliseconds since epoch) that will be the reference point for this view.
     */
    public void setReferenceTime(long referenceTime) {
        this.mReferenceTime = referenceTime;
        
        /*
         * Note that this method could be called when a row in a ListView is recycled.
         * Hence, we need to first stop any currently running schedules (for example from the recycled view.
         */
        stopTaskForPeriodicallyUpdatingRelativeTime();
        
        /*
         * Instantiate a new runnable with the new reference time
         */
        initUpdateTimeTask();
        
        /*
         * Start a new schedule.
         */
        startTaskForPeriodicallyUpdatingRelativeTime();
        
        /*
         * Finally, update the text display.
         */
        updateTextDisplay();
    }

    private void updateTextDisplay() {
        /*
         * TODO: Validation, Better handling of negative cases
         */
        if (this.mReferenceTime == -1L)
            return;
        setText(mPrefix + getRelativeTimeDisplayString(mReferenceTime, System.currentTimeMillis()) + mSuffix);
    }

    /**
     * Get the text to display for relative time. By default, this calls {@link DateUtils#getRelativeTimeSpanString(long, long, long, int)} passing {@link DateUtils#FORMAT_ABBREV_RELATIVE} flag.
     * <br/>
     * You can override this method to customize the string returned. For example you could add prefixes or suffixes, or use Spans to style the string etc
     * @param referenceTime The reference time passed in through {@link #setReferenceTime(long)} or through {@code reference_time} attribute
     * @param now The current time
     * @return The display text for the relative time
     */
    protected CharSequence getRelativeTimeDisplayString(long referenceTime, long now) {
        long difference = now - referenceTime;
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
        if(mUpdateTimeTask.isDetached()) initUpdateTimeTask();
        mHandler.post(mUpdateTimeTask);
        isUpdateTaskRunning = true;
    }

    private void initUpdateTimeTask() {
        mUpdateTimeTask = new UpdateTimeRunnable(this, mReferenceTime);
    }

    private void stopTaskForPeriodicallyUpdatingRelativeTime() {
        if(isUpdateTaskRunning) {
            mUpdateTimeTask.detach();
            mHandler.removeCallbacks(mUpdateTimeTask);
            isUpdateTaskRunning = false;
        }
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

    private static class UpdateTimeRunnable implements Runnable {

        private long mRefTime;
        private final WeakReference<RelativeTimeTextView> weakRefRttv;

        UpdateTimeRunnable(RelativeTimeTextView rttv, long refTime) {
            this.mRefTime = refTime;
            weakRefRttv = new WeakReference<>(rttv);
        }

        boolean isDetached() {
            return weakRefRttv.get() == null;
        }

        void detach() {
            weakRefRttv.clear();
        }

        @Override
        public void run() {
            RelativeTimeTextView rttv = weakRefRttv.get();
            if (rttv == null) return;
            long difference = Math.abs(System.currentTimeMillis() - mRefTime);
            long interval = INITIAL_UPDATE_INTERVAL;
            if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS;
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS;
            } else if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS;
            }
            rttv.updateTextDisplay();
            rttv.mHandler.postDelayed(this, interval);

        }
    }
}
