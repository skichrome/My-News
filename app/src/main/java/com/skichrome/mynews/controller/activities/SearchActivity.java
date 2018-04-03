package com.skichrome.mynews.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.recyclerviewfragments.ArticleSearchRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.recyclerviewfragments.BaseRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.otherfragments.NotificationsFragment;
import com.skichrome.mynews.controller.fragments.otherfragments.SearchFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skichrome.mynews.controller.activities.MainActivity.ID_OTHERS_ACTIVITIES;

/**
 * Used as a controller to display notification parameters screen, search screen and article search results fragment
 */
public class SearchActivity extends AppCompatActivity implements SearchFragment.OnButtonSearchClickedCallback, BaseRecyclerViewFragment.OnRecyclerViewItemClicked
{
    //=========================================
    // Fields
    //=========================================

    /**
     * link to the toolbar of the app
     */
    @BindView(R.id.activity_toolbar) Toolbar mToolbar;

    /**
     * The notification screen fragment
     */
    Fragment notificationsFragment;
    /**
     * the search screen fragment
     */
    Fragment searchFragment;
    /**
     * The list of returned article by the api
     */
    Fragment articleSearchFragment;

    //=========================================
    // Base methods
    //=========================================

    /**
     * Called on each activity launch, here used to configure all visual elements
     *
     * @param savedInstanceState
     *      Used for data restoring (if needed, useless in this case)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        configureToolBar();

        configureAndShowFragmentById(getIntent().getIntExtra(ID_OTHERS_ACTIVITIES, 0));

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
        showArticleSearchFragment(mQueryList, mBeginDate, mEndDate);
    }

    /**
     * Used to show details about one article selected by user
     * @param url
     *      contain the article web url
     */
    @Override
    public void onRVItemClicked(String url)
    {
        Intent intent = new Intent(this, DetailsAndHelpActivity.class);
        intent.putExtra(ID_OTHERS_ACTIVITIES, url);
        startActivity(intent);
    }

    /**
     * Used to setup the activity toolbar
     */
    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Show fragments Methods
    //=========================================

    /**
     * Used to show fragment according to an id specific to each fragment
     * @param idToLaunchFragment
     *      contain the id of fragment to show
     */
    private void configureAndShowFragmentById (int idToLaunchFragment)
    {
        switch (idToLaunchFragment)
        {
            case 0 :
                showSearchArticleFragment();
                break;

            case 1 :
                showNotificationsFragment();
                break;

            default :
                break;
        }
    }

    /**
     * configure notification fragment
     */
    private void showNotificationsFragment()
    {
        if (notificationsFragment == null)
            notificationsFragment = NotificationsFragment.newInstance();

        setTitle("Notifications");

        startTransactionFragment(notificationsFragment);
    }

    /**
     * configure article search fragment
     */
    private void showSearchArticleFragment()
    {
        if (searchFragment == null)
            searchFragment = SearchFragment.newInstance();

        setTitle("Search");

        startTransactionFragment(searchFragment);
    }

    /**
     * configure article search results fragment
     *
     * @param mQueryList
     *      contain a list of query search keywords
     * @param mBeginDate
     *      contain the begin date for search request
     * @param mEndDate
     *      contain the end date for search request
     */
    private void showArticleSearchFragment(ArrayList<String> mQueryList, String mBeginDate, String mEndDate)
    {
        if (articleSearchFragment == null)
            articleSearchFragment = ArticleSearchRecyclerViewFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("SEARCH_DATA_LIST", mQueryList);
        bundle.putString("SEARCH_DATA_BEGIN_DATE", mBeginDate);
        bundle.putString("SEARCH_DATA_END_DATE", mEndDate);
        articleSearchFragment.setArguments(bundle);

        setTitle("Search Results");

        startTransactionFragment(articleSearchFragment);

    }

    /**
     * Show fragment passed in parameter
     * @param mFragment
     *      the fragment to show if it isn't already visible
     */
    private void startTransactionFragment(Fragment mFragment)
    {
        if (!mFragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.search_activity_frame_layout_for_fragments, mFragment).commit();
    }
}