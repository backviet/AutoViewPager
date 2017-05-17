package com.zegome.utils.widget.pagers;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zegome.utils.widget.pagers.adapter.MainPagerAdapter;
import com.zegome.utils.widget.pagers.fragments.MainFragmentItem;
import com.zegome.utils.widget.pagers.indicator.CircleIndicator;

/**
 * Created by QuanLT.
 */

public class MainActivity extends AppCompatActivity {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final long AUTO_SLIDE_DURATION = 1 * 1000;

    // ===========================================================
    // Fields
    // ===========================================================
    private TextView mRealPositionTv;
    private TextView mRealItemTv;

    private ZAutoViewPager mViewPager;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ZAutoViewPager) findViewById(R.id.main_view_pager);
        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setSwipe(true);
        mViewPager.setAni(true);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(adapter.get0Center(), false);
        mViewPager.setDuration(AUTO_SLIDE_DURATION);

        mRealItemTv = (TextView) findViewById(R.id.main_tv_real_item);
        mRealPositionTv = (TextView) findViewById(R.id.main_tv_real_position);

        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.main_indicator);
        indicator.setViewPager(mViewPager, mViewPager.getCurrentItem());
        indicator.setOnPageChangedListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRealPositionTv.setText("" + (position + 1));
                final MainFragmentItem mainFragmentItem = getFragmentForPosition(adapter.getItemId(position));
                if (mainFragmentItem == null) {
                    mRealItemTv.setText("1");
                } else {
                    mRealItemTv.setText("" + (mainFragmentItem.getPosition() + 1));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Button startNowBt = (Button) findViewById(R.id.main_bt_start_now);
        startNowBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.startAutoScroll();
            }
        });

        final Button startDelayedBt = (Button) findViewById(R.id.main_bt_start_delayed);
        startDelayedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.startAutoScroll(AUTO_SLIDE_DURATION);
            }
        });

        final Button stopBt = (Button) findViewById(R.id.main_bt_stop);
        stopBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.stopAutoScroll();
            }
        });
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * @return may return null if the fragment has not been instantiated yet for that position - this depends on if the fragment has been viewed
     * yet OR is a sibling covered by {@link android.support.v4.view.ViewPager#setOffscreenPageLimit(int)}. Can use this to call methods on
     * the current positions fragment.
     */
    public
    @Nullable
    MainFragmentItem getFragmentForPosition(long id) {
        String tag = makeFragmentName(mViewPager.getId(), id);
        MainFragmentItem fragment = (MainFragmentItem) getSupportFragmentManager().findFragmentByTag(tag);
        return fragment;
    }

    /**
     * @param containerViewId the ViewPager this adapter is being supplied to
     * @param id              pass in getItemId(position) as this is whats used internally in this class
     * @return the tag used for this pages fragment
     */
    public static String makeFragmentName(int containerViewId, long id) {
        return "android:switcher:" + containerViewId + ":" + id;
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}