package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skichrome.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment
{


    public HelpFragment ()
    {
        // Required empty public constructor
    }

    public static HelpFragment newInstance()
    {
        return new HelpFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

}
