package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;


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
 * A simple {@link Fragment} subclass.
 */
public class ArticleDetailsFragment extends Fragment
{

    /**
     * Used to load an article from an url
     */
    @BindView(R.id.article_details_fragment_web_view) WebView mWebView;

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

        ButterKnife.bind(this, view);

        getBundleAndShowArticle();

        return view;
    }

    private void getBundleAndShowArticle()
    {
        Bundle bundle = getArguments();

        String url = bundle.getString(ID_OTHERS_ACTIVITIES);

        mWebView.loadUrl(url);
        getActivity().finish();
    }
}