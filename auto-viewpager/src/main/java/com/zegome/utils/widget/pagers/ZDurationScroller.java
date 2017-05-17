package com.zegome.utils.widget.pagers;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by QuanLT on 10/20/16.
 */
public class ZDurationScroller extends Scroller {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private double mScrollFactor = 3;
    public boolean useDefault = false;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZDurationScroller(Context context) {
        super(context);
    }

    public ZDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);

    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public void setScrollDurationFactor(double scrollFactor) {
        this.mScrollFactor = scrollFactor;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, useDefault ? 360 : (int)(duration * mScrollFactor));
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
