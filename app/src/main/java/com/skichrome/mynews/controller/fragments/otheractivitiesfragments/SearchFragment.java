package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;

import android.view.View;

import java.util.Calendar;

/**
 * Allow to do some request on article sarch api with custom search keywords
 */
public class SearchFragment extends BaseSearchFragment
{
    //=========================
    // Fields
    //=========================

    Calendar mCalendar = Calendar.getInstance();

    //=========================
    // Empty Constructor
    //=========================

    /**
     * Required empty constructor
     */
    public SearchFragment()
    {
        // Required empty public constructor
    }

    //=========================
    // Overrided Methods
    //=========================

    /**
     * return a new instance of this fragment
     * @return
     *      new instance of this fragment
     */
    public static SearchFragment newInstance()
    {
        return new SearchFragment();
    }

    /**
     * @see BaseSearchFragment
     */
    @Override
    public void updateDesign()
    {
    }

    /**
     * @see BaseSearchFragment
     */
    @Override
    public void configureDesign()
    {
    }

    /**
     * @see BaseSearchFragment
     */
    @Override
    protected void removeUnnecessaryFields()
    {
        this.mViewSeparator.setVisibility(View.GONE);
        this.mSwitch.setVisibility(View.GONE);
    }

    //=====================
    // Methods
    //=====================

}
