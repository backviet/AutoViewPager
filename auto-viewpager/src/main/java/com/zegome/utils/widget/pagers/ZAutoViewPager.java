package com.zegome.utils.widget.pagers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by QuanLT on 10/20/16.
 */
public class ZAutoViewPager extends ZViewPager {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final int WHAT_AUTO_SCROLL = 0;
    public static final int DEFAULT_DURATION = 3000;

    // ===========================================================
    // Fields
    // ===========================================================
    private long mDuration = DEFAULT_DURATION;

    private boolean mIsStopWhenTouch = true;

    private Handler mHandler;
    private boolean mIsAutoScroll = false;
    private boolean mIsStopByTouch = false;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZAutoViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public ZAutoViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }


    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public boolean isStopWhenTouch() {
        return mIsStopWhenTouch;
    }

    public void setStopWhenTouch(boolean stopScrollWhenTouch) {
        this.mIsStopWhenTouch = stopScrollWhenTouch;
    }

    public void setAutoScroll(final boolean isAutoScroll) {
        stopAutoScroll();
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (mIsStopWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && mIsAutoScroll) {
                mIsStopByTouch = true;
                mScroller.useDefault = true;
                stopAutoScroll();
            } else if (((ev.getAction() == MotionEvent.ACTION_UP) || (ev.getAction() == MotionEvent.ACTION_CANCEL))
                    && mIsStopByTouch) {
                mIsStopByTouch = false;
                mScroller.useDefault = false;
                startAutoScroll(mDuration);
            }
        }

        getParent().requestDisallowInterceptTouchEvent(false);

        return super.dispatchTouchEvent(ev);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void init() {
        mHandler = new ZAutoHandler(this);
        startAutoScroll(mDuration);
    }

    public void startAutoScroll() {
        if (mIsAutoScroll) {
            return;
        }
        mIsAutoScroll = true;
        sendScrollMessage();
    }

    public void startAutoScroll(long duration) {
        mIsAutoScroll = true;
        sendScrollMessageDelayed(duration);
    }

    public void stopAutoScroll() {
        if (!mIsAutoScroll) {
            return;
        }
        mIsAutoScroll = false;
        mHandler.removeMessages(WHAT_AUTO_SCROLL);
    }

    private void sendScrollMessage() {
        mHandler.removeMessages(WHAT_AUTO_SCROLL);
        mHandler.sendEmptyMessage(WHAT_AUTO_SCROLL);
    }

    private void sendScrollMessageDelayed(long delayMills) {
        mHandler.removeMessages(WHAT_AUTO_SCROLL);
        mHandler.sendEmptyMessageDelayed(WHAT_AUTO_SCROLL, delayMills);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class ZAutoHandler extends Handler {

        private final WeakReference<ZAutoViewPager> autoViewPager;

        public ZAutoHandler(ZAutoViewPager zViewPager) {
            this.autoViewPager = new WeakReference<>(zViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_AUTO_SCROLL:
                    ZAutoViewPager pager = this.autoViewPager.get();
                    if (pager != null) {
                        final int curr = pager.getCurrentItem();
                        final int count = pager.getAdapter().getCount();
                        int next = curr >= count - 1 ? 0 : curr + 1;
                        if (next == 0) {
                            pager.setCurrentItem(next, false);
                        } else {
                            pager.setCurrentItem(next);
                        }
                        pager.sendScrollMessageDelayed(pager.mDuration + pager.getScrollDuration());
                    }
                default:
                    break;
            }
        }
    }
}
