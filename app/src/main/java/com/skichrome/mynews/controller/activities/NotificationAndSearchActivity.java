package com.skichrome.mynews.controller.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.ArticleFragment;
import com.skichrome.mynews.controller.fragments.SearchAndNotificationFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Used as a controller to display notification parameters screen, search screen and article search results fragment
 */
public class NotificationAndSearchActivity extends BaseActivity implements ArticleFragment.OnRecyclerViewItemClicked, SearchAndNotificationFragment.OnButtonSearchClickedCallback
{
    //=========================================
    // Fields
    //=========================================

    /**
     * link to the toolbar of the app
     */
    @BindView(R.id.activity_toolbar) Toolbar mToolbar;

    /**
     * The notification  and search screen dedicated fragment
     */
    Fragment notificationsAndSearchFragment;
    /**
     * Used to display results of Article Search request
     */
    Fragment articleFragment;

    //=========================================
    // Superclass methods
    //=========================================

    /**
     * @see BaseActivity#configureDesign()
     */
    @Override
    protected void configureDesign()
    {
        this.configureToolBar();
    }

    /**
     * @see BaseActivity#updateDesign()
     */
    @Override
    protected void updateDesign()
    {
        this.showSearchAndNotificationFragment(getIntent().getIntExtra(ID_OTHERS_ACTIVITIES, 0));
    }

    /**
     * @see BaseActivity#getActivityLayout()
     */
    @Override
    protected int getActivityLayout()
    {
        return R.layout.activity_search_and_notification;
    }

    /**
     * Used to show details about one article selected by user
     * @param url
     *      contain the article web url
     */
    @Override
    public void onRVItemClicked(String url)
    {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra(ID_OTHERS_ACTIVITIES, url);
        startActivity(intent);
    }

    /**
     * Used to get the data from search fragment and launch request by displaying article search results fragment
     *
     * @param mQueryList
     *      contain a list of query search keywords
     * @param mBeginDate
     *      contain the begin date for search request
     * @param mEndDate
     *      contain the end date for search request
     */
    @Override
    public void onButtonSearchClicked(ArrayList<String> mQueryList, String mBeginDate, String mEndDate)
    {
        showArticleFragment(mQueryList, mBeginDate, mEndDate);
    }

    //=========================================
    // Configuration Method
    //=========================================

    /**
     * Used to setup the activity toolbar
     */
    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);

        //set elevation for android version above lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            this.mToolbar.setElevation(30);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //=========================================
    // Show fragment Method
    //=========================================

    /**
     * Used to configure and show search and notification fragment, with an id that represent the screen to display (search or notifications)
     * @param intExtra
     *      the id to select the screen to display
     */
    private void showSearchAndNotificationFragment(int intExtra)
    {
        if (notificationsAndSearchFragment == null)
            notificationsAndSearchFragment = SearchAndNotificationFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("ID_SEARCH_NOTIFICATION_FRAG", intExtra);
        notificationsAndSearchFragment.setArguments(bundle);

        startTransactionFragment(notificationsAndSearchFragment);
    }

    /**
     * Used to configure and show article fragment, pass the request keywords to a new bundle and an id to select correct http request
     *
     * @param mQueryList
     *      contain a list of query search keywords
     * @param mBeginDate
     *      contain the begin date for search request
     * @param mEndDate
     *      contain the end date for search request
     */
    private void showArticleFragment(ArrayList<String> mQueryList, String mBeginDate, String mEndDate)
    {
        if (articleFragment == null)
            articleFragment = ArticleFragment.newInstance();

        Bundle bundle = new Bundle();

        bundle.putInt("REQUEST_ID", 30);

        bundle.putStringArrayList("SEARCH_DATA_LIST", mQueryList);
        bundle.putString("SEARCH_DATA_BEGIN_DATE", mBeginDate);
        bundle.putString("SEARCH_DATA_END_DATE", mEndDate);
        articleFragment.setArguments(bundle);

        setTitle("Search Results");

        startTransactionFragment(articleFragment);
    }

    /**
     * Show fragment passed in parameter
     * @param fragment
     *      the fragment to show if it isn't already visible
     */
    private void startTransactionFragment(Fragment fragment)
    {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.search_and_notification_activity_frame_layout_for_fragments, fragment).commit();
    }
}