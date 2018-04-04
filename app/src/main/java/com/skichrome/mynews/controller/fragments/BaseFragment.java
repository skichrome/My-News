package com.skichrome.mynews.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
     * configure refresh on user swipe down on top of recyclerView
     */
    protected abstract void updateDesign ();
    /**
     * Used to get the layout file and bind it to the view
     */
    protected abstract int getFragmentLayout();

    //=====================
    // Empty Constructor
    //=====================

    /**
     * Empty constructor, needed for Fragment instantiation, not modifiable
     */
    public BaseFragment()
    {
    }

    //=====================
    // Superclass Methods
    //=====================

    /**
     * Called when a fragment needs to be displayed, setup the views (ButterKnife) and call the configureDesign method (defined in child class)
     *
     * @param inflater
     *      LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *      ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *      Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     *      Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        //bind view
        ButterKnife.bind(this, view);
        // Configure and update Design (Developer will call this method instead of override onCreateView())
        this.configureDesign();
        this.updateDesign();

        return(view);
    }
}