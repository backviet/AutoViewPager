package com.zegome.utils.widget.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by QuanLT on 10/21/16.
 */
public abstract class ZMutiViewFragmentPagerAdapter extends FragmentPagerAdapter implements IMultiViewPager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    protected int mCountRealItem;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZMutiViewFragmentPagerAdapter(FragmentManager fm, final int countRealItem) {
        super(fm);
        mCountRealItem = countRealItem;
        assert mCountRealItem <= 0;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public int getCount() {
        return IMultiViewPager.LIMITED_PAGE;
    }

    public int getCurrentFromPosition(final int position) {
        return position % mCountRealItem;
    }

    @Override
    public int getCountRealItem() {
        return mCountRealItem;
    }

    @Override
    public int getCenter() {
        return LIMITED_PAGE / 2;
    }

    @Override
    public int get0Center() {
        final int center = getCenter();
        return center - center % mCountRealItem;
    }

    @Override
    public final Fragment getItem(int position) {
        return getFragmentItem(getCurrentFromPosition(position));
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    public abstract Fragment getFragmentItem(int position);

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
