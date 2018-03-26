package com.skichrome.mynews.controller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skichrome.mynews.R;

import butterknife.ButterKnife;

/**
 * This is the model used to define most of the fragments who will display news in a RecyclerView in ViewPager defined in {@link com.skichrome.mynews.controller.activities.MainActivity}
 */
public abstract class BaseFragment extends Fragment
{
    //=========================
    // Base Abstract Methods
    //=========================

    /**
     * Used to configure the design, for example here will be executed the http Request, the RecyclerView will be configured here
     */
    protected abstract void configureDesign();

    /**
     * Used to update the design, when needed
     */
    protected abstract void updateDesign();

    //=====================
    // Empty Constructor
    //=====================

    /**
     * Empty constructor, needed for Fragment instantiation, not modifiable
     */
    public BaseFragment ()
    {
    }

    //=====================
    // Methods
    //=====================

    /**
     * Called when a fragment needs to be displayed, setup the views (ButterKnife) and call the configureDesign method (defined in child class)
     *
     * @param inflater
     *      used to setup view
     * @param container
     *      used to setup view
     * @param savedInstanceState
     *      Used to restore data if needed
     * @return
     *      a view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        //bind view
        ButterKnife.bind(this, view);
        // Configure Design (Developer will call this method instead of override onCreateView())
        this.configureDesign();
        return(view);
    }

    /**
     * Get the layout file used for child fragments
     * @return
     *      integer, the id of the layout file
     */
    protected int getFragmentLayout ()
    {
        return R.layout.base_fragment_for_recycler_view;
    }
}