package com.zegome.utils.widget.pagers.indicator;

import android.support.v4.view.ViewPager;

/**
 * Created by QuanLT on 2/6/17.
 */

public interface IPagerIndicator extends ViewPager.OnPageChangeListener {
    int getCount();

    int getCurrentFromPosition(int postion);

    void setViewPager(ViewPager viewPager);

    void setViewPager(ViewPager viewPager, int initPosition);

    void setCurrentPostion(int postion);

    void setOnPageChangedListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
