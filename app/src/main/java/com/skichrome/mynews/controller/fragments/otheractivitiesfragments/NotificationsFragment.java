package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;


import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
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

    @Override
    public void onClick(View v)
    {
        this.getDataFromEntryFields();
    }

    //=====================
    // Methods
    //=====================

}