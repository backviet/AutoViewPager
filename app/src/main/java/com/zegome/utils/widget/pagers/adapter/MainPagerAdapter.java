package com.zegome.utils.widget.pagers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zegome.utils.widget.pagers.R;
import com.zegome.utils.widget.pagers.ZMutiViewFragmentPagerAdapter;
import com.zegome.utils.widget.pagers.fragments.MainFragmentItem;

/**
 * Created by QuanLT on 5/16/17.
 * Copyright © 2017 イオンドットコム株式会社. All rights reserved.
 */

public class MainPagerAdapter extends ZMutiViewFragmentPagerAdapter {

    // ===========================================================
    // Constants
    // ===========================================================
    public static final int NUMBER_INDEX = 6;

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================
    public MainPagerAdapter(FragmentManager fm) {
        super(fm, NUMBER_INDEX);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    @Override
    public Fragment getFragmentItem(int position) {
        return MainFragmentItem.getNewItem(position);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}