package com.skichrome.mynews.controller.fragments;

import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends BaseFragment
{
    //=====================
    // Base Methods
    //=====================

    public static BaseFragment newInstance ()
    {
        return new TopStoriesFragment();
    }

    @Override
    protected void configureDesign ()
    {
    }

    @Override
    protected void updateDesign ()
    {
        mTextViewTest.setText("It's a test, you're in Top Stories Fragment !");
    }
}