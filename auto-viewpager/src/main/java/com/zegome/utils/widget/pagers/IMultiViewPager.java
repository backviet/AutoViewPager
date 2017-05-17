package com.zegome.utils.widget.pagers;

/**
 * Created by QuanLT on 10/20/16.
 */
public interface IMultiViewPager {
    int LIMITED_PAGE = 10000;

    int getCountRealItem();
    int getCurrentFromPosition(final int position);
    int getCenter();
    int get0Center();
}
