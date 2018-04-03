package com.skichrome.mynews.controller.fragments.otherfragments;

import android.view.View;

/**
 * Used for notification parameters screen
 */
public class NotificationsFragment extends BaseSearchFragment implements View.OnClickListener
{
    //=====================
    // Empty Constructor
    //=====================

    /**
     * Required empty constructor
     */
    public NotificationsFragment()
    {
        // Required empty public constructor
    }

    /**
     * return a new instance of this fragment
     * @return
     *      new instance of this fragment
     */
    public static NotificationsFragment newInstance()
    {
        return new NotificationsFragment();
    }

    //=========================
    // Overrided Methods
    //=========================

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
        this.removeUnnecessaryFields();
        this.mSwitch.setOnClickListener(this);
    }

    /**
     * Remove the date selection fields and the button because they are useless in this fragment
     */
    protected void removeUnnecessaryFields()
    {
        this.mLinearLayout.setVisibility(View.GONE);
        this.mBtn.setVisibility(View.GONE);
    }

    /**
     * Used to get user data and set the notifications to On or Off
     *
     * @param v
     *      contain the view clicked by the user
     */
    @Override
    public void onClick(View v)
    {
        this.getDataFromEntryFields();
    }

    //=====================
    // Methods
    //=====================

}