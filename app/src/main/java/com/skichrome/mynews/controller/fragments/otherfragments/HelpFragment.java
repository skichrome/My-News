package com.skichrome.mynews.controller.fragments.otherfragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skichrome.mynews.R;

/**
 * Used to show help section
 */
public class HelpFragment extends Fragment
{
    /**
     * empty constructor
     */
    public HelpFragment ()
    {
        // Required empty public constructor
    }

    /**
     * return a new instance of this fragment
     * @return
     *      new instance of this fragment
     */
    public static HelpFragment newInstance()
    {
        return new HelpFragment();
    }

    /**
     * Used for setup at the creation of each fragment instantiation
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
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

}