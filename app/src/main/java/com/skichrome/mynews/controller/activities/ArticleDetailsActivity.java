package com.skichrome.mynews.controller.activities;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skichrome.mynews.R;

import butterknife.BindView;

public class ArticleDetailsActivity extends BaseActivity
{
    /**
     * Link to the toolbar
     */
    @BindView(R.id.activity_toolbar) Toolbar toolbar;
    /**
     * Link to the WebView used to display article
     */
    @BindView(R.id.article_details_web_view) WebView webView;

    /**
     * @see BaseActivity#configureDesign()
     */
    @Override
    protected void configureDesign()
    {
        configureToolBar();
        configureWebView();
    }

    /**
     * @see BaseActivity#updateDesign()
     */
    @Override
    protected void updateDesign()
    {
        webView.loadUrl(getIntent().getStringExtra(ID_OTHERS_ACTIVITIES));
        setContentView(webView);
    }

    /**
     * @see BaseActivity#getActivityLayout()
     * @return
     *      integer id
     */
    @Override
    protected int getActivityLayout()
    {
        return R.layout.activity_article_details;
    }

    /**
     * Used to configure the toolBar of the activity
     */
    private void configureToolBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureWebView()
    {
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
    }
}
