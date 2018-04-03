package com.skichrome.mynews.controller.fragments.otherfragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.skichrome.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skichrome.mynews.controller.activities.MainActivity.ID_OTHERS_ACTIVITIES;

/**
 * Used to show article in a webView
 */
public class ArticleDetailsFragment extends Fragment
{

    /**
     * Used to load an article from an url
     */
    @BindView(R.id.article_details_fragment_web_view) WebView mWebView;

    /**
     * empty constructor
     */
    public ArticleDetailsFragment ()
    {
        // Required empty public constructor
    }

    /**
     * return a new instance of this fragment
     * @return
     *      new instance of this fragment
     */
    public static ArticleDetailsFragment newInstance()
    {
        return new ArticleDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_article_details, container, false);

        ButterKnife.bind(this, view);

        getBundleAndShowArticle();

        return view;
    }

    /**
     * Get the article url in bundle
     */
    private void getBundleAndShowArticle()
    {
        Bundle bundle = getArguments();

        if (bundle != null)
            mWebView.loadUrl(bundle.getString(ID_OTHERS_ACTIVITIES));

        getActivity().finish();
    }
}