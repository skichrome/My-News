package com.skichrome.mynews.controller.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.BaseRecyclerViewFragment;
import com.skichrome.mynews.view.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The main activity is used as a controller to display different fragments in a viewPager, a navigation drawer, a toolBar and a tabLayout
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseRecyclerViewFragment.OnRecyclerViewItemClicked
{
    //=========================================
    // Fields
    //=========================================

    public static final String ID_OTHERS_ACTIVITIES = "Details_and_help_activity";

    /**
     * Contains the toolBar of the app
     */
    @BindView(R.id.activity_toolbar) Toolbar mToolbar;
    /**
     * Contains the navigation drawer of the app
     */
    @BindView(R.id.activity_main_menu_drawer_layout) DrawerLayout mDrawerLayout;
    /**
     * include the navigation drawer header and the navigation drawer menu fields
     */
    @BindView(R.id.activity_main_navigation_view) NavigationView mNavigationView;
    /**
     * Used to display a fragment title above the view pager, and allow user to change fragment by clicking on one tab
     */
    @BindView(R.id.activity_main_tabs) TabLayout mTabLayout;
    /**
     * Used to display different fragments on a single view in MainActivity
     */
    @BindView(R.id.activity_main_view_pager) ViewPager mViewPager;

    //=========================================
    // Overrided Methods
    //=========================================

    /**
     * Called on each activity launch, here used to configure all visual elements
     *
     * @param savedInstanceState
     *      Used for data restoring (if needed, useless in this case)
     */
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configureToolBar();
        configureMenuDrawer();
        configureNavigationView();
        configureViewPagerAndTabs();
    }

    /**
     * Get the layout menu and inflate the menu in toolBar
     * @param menu
     *      the menu to be inflated
     * @return
     *      boolean
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item
     *         The menu item that was selected.
     *
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     *
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.activity_main_menu_search :
                launchSearchActivity(0);
                return true;

            case R.id.activity_main_menu_notifications :
                launchSearchActivity(1);
                return true;

            case R.id.activity_main_menu_help :
                launchDetailsAndHelpActivity("help");
                return true;

            case R.id.activity_main_menu_about :
                launchDetailsAndHelpActivity("about");
                return true;

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item
     *         The selected item
     *
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.activity_main_menu_drawer_search :
                launchSearchActivity(0);
                break;

            case R.id.activity_main_menu_drawer_top_stories :
                changeToMostPopularFragmentInViewPager(0);
                break;

            case R.id.activity_main_menu_drawer_most_popular :
                changeToMostPopularFragmentInViewPager(1);
                break;

            case R.id.activity_main_menu_drawer_new_tech :
                changeToMostPopularFragmentInViewPager(2);
                break;

            case R.id.activity_main_menu_drawer_notifications :
                launchSearchActivity(1);
                break;

            case R.id.activity_main_menu_drawer_help :
                launchDetailsAndHelpActivity("help");
                break;

            case R.id.activity_main_menu_drawer_about :
                launchDetailsAndHelpActivity("about");
                break;

            default :
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    /**
     * Used to launch details activity that can display an article in WebView
     * @param url
     *      the url to the article on the web
     */
    @Override
    public void onRVItemclicked(String url)
    {
        launchDetailsAndHelpActivity(url);
    }

    //=========================================
    // Configuration Methods
    //=========================================

    /**
     * Configure the toolBar with layout menu file, and if the android version is compatible with Lollipop functionality, add z-elevation
     */
    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.activity_main_menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mToolbar.setElevation(30);
    }

    /**
     * configure menu drawer with toolBar and add a listener
     */
    private void configureMenuDrawer ()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * set a listener in navigationView
     */
    private void configureNavigationView ()
    {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * configure the viewPager with specific {@link PageAdapter}, and if the android version is compatible with Lollipop functionality, add z-elevation
     */
    private void configureViewPagerAndTabs ()
    {
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager())
        {
        });

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mTabLayout.setElevation(30);
            mViewPager.setElevation(5);
        }
    }

    //=========================================
    // Menus actions Methods
    //=========================================

    /**
     * generic method that launch a new activity with an id that represent a specific fragment in parameter
     * @param mUrlId
     *      the id that will pe passed to the intent
     */
    private void launchDetailsAndHelpActivity( String mUrlId)
    {
        Intent intent = new Intent(this, DetailsAndHelpActivity.class);
        intent.putExtra(ID_OTHERS_ACTIVITIES, mUrlId);
        startActivity(intent);
    }

    private void launchSearchActivity(int fragId)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(ID_OTHERS_ACTIVITIES, fragId);
        startActivity(intent);
    }

    /**
     * This method allow changing current viewPager fragment with id in parameters( {@link #onNavigationItemSelected(MenuItem)})
     * @param fragId
     *      the fragment id
     */
    private void changeToMostPopularFragmentInViewPager(int fragId)
    {
        mViewPager.setCurrentItem(fragId);
    }
}
