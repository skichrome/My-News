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
public class ArticleDetailsFragment extends Fragment
{


    public ArticleDetailsFragment ()
    {
        // Required empty public constructor
    }

    public static ArticleDetailsFragment newInstance()
    {
        return new ArticleDetailsFragment();
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_details, container, false);

        return view;
    }

}
