package com.zegome.utils.widget.pagers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zegome.utils.widget.pagers.R;

/**
 * Created by QuanLT on 5/14/17.
 */

public class MainFragmentItem extends Fragment {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final int[] mImageIds = {R.drawable.im1, R.drawable.im2, R.drawable.im3, R.drawable.im4, R.drawable.im5, R.drawable.im6};

    // ===========================================================
    // Fields
    // ===========================================================
    private int mPosition;

    private View mRootView;
    private ImageView mImageView;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public static MainFragmentItem getNewItem(final int position) {
        final MainFragmentItem fragmentItem = new MainFragmentItem();

        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentItem.setArguments(args);

        return fragmentItem;
    }

    public int getPosition() {
        return mPosition;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments() != null ? getArguments().getInt("position") : 0;
    }


    // ===========================================================
    // Methods
    // ===========================================================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_fragment, container, false);

        mImageView = (ImageView) mRootView.findViewById(R.id.fragment_im_banner);
        Glide.with(getActivity())
                .load(mImageIds[getPosition()])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mImageView);

        return mRootView;
    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
