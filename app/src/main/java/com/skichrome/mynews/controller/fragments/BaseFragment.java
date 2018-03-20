package com.skichrome.mynews.controller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skichrome.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * This is the model used to define most of the fragments who will display news in ViewPager
 */
public abstract class BaseFragment extends Fragment
{
    //===============================
    // Bind views with butterKnife
    //===============================

    @BindView(R.id.list_item_recycler_view_image) ImageView mImageViewRecyclerView;
    @BindView(R.id.list_item_recycler_view_title) android.widget.TextView mTextViewTitleRecyclerView;
    @BindView(R.id.list_item_recycler_view_date) TextView mTextViewDateRecyclerView;
    @BindView(R.id.list_item_recycler_view_start_article_light) TextView mTextViewStartArticleRecyclerView;

    //=========================
    // Base Abstract Methods
    //=========================

    protected abstract BaseFragment newInstance();
    protected abstract void configureDesign();
    protected abstract void updateDesign();

    //=====================
    // Empty Constructor
    //=====================

    public BaseFragment ()
    {
    }

    //=====================
    // Methods
    //=====================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        // Binding Views
        ButterKnife.bind(this, view);
        // Configure Design (Developer will call this method instead of override onCreateView())
        this.configureDesign();
        return(view);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        this.updateDesign();
    }

    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected int getFragmentLayout ()
    {
        return R.layout.base_fragment_for_recycler_view;
    }
}