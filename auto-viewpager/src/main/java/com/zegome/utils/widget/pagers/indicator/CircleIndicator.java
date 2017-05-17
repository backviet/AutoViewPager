package com.zegome.utils.widget.pagers.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.zegome.utils.widget.pagers.IMultiViewPager;

import pager.widget.utils.zegome.com.autoviewpager.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by QuanLT on 2/6/17.
 */
public class CircleIndicator extends View implements IPagerIndicator {

    // ===========================================================
    // Constants
    // ===========================================================
    private static final boolean INDICATOR_CENTERED = true;
    private static final boolean INDECATOR_SNAP = false;
    private static final int INDICATOR_RADIUS = 4;                  //in dp
    private static final int INDICATOR_COLOR_FILL = Color.WHITE;
    private static final int INDICATOR_COLOR_PAGE = Color.TRANSPARENT;
    private static final int INDICATOR_STROKE_COLOR = Color.GRAY;
    private static final int INDICATOR_STROKE_WIDTH = 1;            //in dp

    // ===========================================================
    // Fields
    // ===========================================================
    private float mRadius;
    private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
    private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
    private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mCurrentPage;
    private int mSnapPage;
    private float mPageOffset;
    private int mScrollState;
    private boolean mCentered;
    private boolean mSnap;

    // ===========================================================
    // Constructors
    // ===========================================================

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) return;

        if (attrs == null) return;

        //Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);

        mCentered = a.getBoolean(R.styleable.CircleIndicator_indicator_centered, INDICATOR_CENTERED);
        mPaintPageFill.setStyle(Paint.Style.FILL);
        mPaintPageFill.setColor(a.getColor(R.styleable.CircleIndicator_indicator_pageColor, INDICATOR_COLOR_PAGE));
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setColor(a.getColor(R.styleable.CircleIndicator_indicator_strokeColor, INDICATOR_STROKE_COLOR));
        mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CircleIndicator_indicator_strokeWidth, dpAsPixels(context, INDICATOR_STROKE_WIDTH)));
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(a.getColor(R.styleable.CircleIndicator_indicator_fillColor, INDICATOR_COLOR_FILL));
        mRadius = a.getDimension(R.styleable.CircleIndicator_indicator_radius, dpAsPixels(context, INDICATOR_RADIUS));
        mSnap = a.getBoolean(R.styleable.CircleIndicator_indicator_snap, INDECATOR_SNAP);

        a.recycle();
    }

    public CircleIndicator(Context context) {
        this(context, null);
    }


    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
    }

    @Override
    public void setViewPager(ViewPager viewPager, int initPosition) {
        setViewPager(viewPager);
        setCurrentPostion(initPosition);
    }

    @Override
    public void setCurrentPostion(int position) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(position);
        mCurrentPage = getCurrentFromPosition(position);
        invalidate();
    }

    @Override
    public void setOnPageChangedListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        if (mViewPager == null || mViewPager.getAdapter() == null) {
            return 0;
        }
        if (mViewPager.getAdapter() instanceof IMultiViewPager) {
            return ((IMultiViewPager)mViewPager.getAdapter()).getCountRealItem();
        }
        return mViewPager.getAdapter().getCount();
    }

    @Override
    public int getCurrentFromPosition(int postion) {
        if (mViewPager == null || mViewPager.getAdapter() == null) {
            return postion;
        }
        if (mViewPager.getAdapter() instanceof IMultiViewPager) {
            return ((IMultiViewPager)mViewPager.getAdapter()).getCurrentFromPosition(postion);
        }
        return postion;
    }


    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = getCount();
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentPostion(count - 1);
            return;
        }

        int longSize;
        int longPaddingBefore;
        int longPaddingAfter;
        int shortPaddingBefore;

        longSize = getWidth();
        longPaddingBefore = getPaddingLeft();
        longPaddingAfter = getPaddingRight();
        shortPaddingBefore = getPaddingTop();

        final float threeRadius = mRadius * 3;
        final float shortOffset = shortPaddingBefore + mRadius;
        float longOffset = longPaddingBefore + mRadius;
        if (mCentered) {
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * threeRadius) / 2.0f);
        }

        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }

        //Draw stroked circles
        for (int iLoop = 0; iLoop < count; iLoop++) {
            float drawLong = longOffset + (iLoop * threeRadius);
            dX = drawLong;
            dY = shortOffset;

            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
            }
        }

        //Draw the filled circle according to the current scroll
        float cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius;
        if (!mSnap) {
            cx += mPageOffset * threeRadius;
        }

        dX = longOffset + cx;
        dY = shortOffset;

        canvas.drawCircle(dX, dY, mRadius, mPaintFill);
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = getCurrentFromPosition(position);
        mPageOffset = positionOffset;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = getCurrentFromPosition(position);
            mSnapPage = mCurrentPage;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        mSnapPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private static int dpAsPixels(@NonNull final Context context, final int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpAsPixels(sizeInDp, scale);
    }

    private static int dpAsPixels(final float sizeInDp, final float density) {
        int dpAsPixels = (int) (sizeInDp * density + 0.5f);
        return dpAsPixels;
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */

    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            final int count = getCount();
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
