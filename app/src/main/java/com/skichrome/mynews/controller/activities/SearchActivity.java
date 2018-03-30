package com.skichrome.mynews.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.ArticleSearchRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.BaseRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.otheractivitiesfragments.NotificationsFragment;
import com.skichrome.mynews.controller.fragments.otheractivitiesfragments.SearchFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skichrome.mynews.controller.activities.MainActivity.ID_OTHERS_ACTIVITIES;

public class SearchActivity extends AppCompatActivity implements SearchFragment.OnButtonSearchClickedCallback, BaseRecyclerViewFragment.OnRecyclerViewItemClicked
{
    //=========================================
    // Fields
    //=========================================

    @BindView(R.id.activity_toolbar) Toolbar mToolbar;

    Fragment notificationsFragment;
    Fragment searchFragment;
    Fragment articleSearchFragment;

    //=========================================
    // Base methods
    //=========================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        configureToolBar();

        configureAndShowFragmentById(getIntent().getIntExtra(ID_OTHERS_ACTIVITIES, 0));

    }

    @Override
    public void onButtonSearchClicked(ArrayList<String> mQueryList, String mBeginDate, String mEndDate)
    {
        showArticleSearchFragment(mQueryList, mBeginDate, mEndDate);
    }

    @Override
    public void onRVItemClicked(String url)
    {
        Intent intent = new Intent(this, DetailsAndHelpActivity.class);
        intent.putExtra(ID_OTHERS_ACTIVITIES, url);
        startActivity(intent);
    }

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Show fragments Methods
    //=========================================

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

    private void showNotificationsFragment()
    {
        if (notificationsFragment == null)
            notificationsFragment = NotificationsFragment.newInstance();

        setTitle("Notifications");

        startTransactionFragment(notificationsFragment);
    }

    private void showSearchArticleFragment()
    {
        if (searchFragment == null)
            searchFragment = SearchFragment.newInstance();

        setTitle("Search");

        startTransactionFragment(searchFragment);
    }

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

    private void startTransactionFragment(Fragment mFragment)
    {
        if (!mFragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.search_activity_frame_layout_for_fragments, mFragment).commit();
    }
}