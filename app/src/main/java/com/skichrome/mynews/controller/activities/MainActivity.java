package com.skichrome.mynews.controller.activities;

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
import com.skichrome.mynews.view.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    //=========================================
    // Fields
    //=========================================

    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_menu_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_navigation_view) NavigationView mNavigationView;
    @BindView(R.id.activity_main_tabs) TabLayout mTabLayout;
    @BindView(R.id.activity_main_view_pager) ViewPager mViewPager;

    //=========================================
    // Overrided Methods
    //=========================================

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
                return true;

            case R.id.activity_main_menu_notifications :
                return true;

            case R.id.activity_main_menu_help :
                return true;

            case R.id.activity_main_menu_about :
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
                break;

            case R.id.activity_main_menu_drawer_top_stories :
                break;

            case R.id.activity_main_menu_drawer_most_popular :
                break;

            case R.id.activity_main_menu_drawer_new_tech :
                break;

            case R.id.activity_main_menu_notifications :
                break;

            case R.id.activity_main_menu_help :
                break;

            case R.id.activity_main_menu_about :
                break;

            default :
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //=========================================
    // Configuration Methods
    //=========================================

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.activity_main_menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mToolbar.setElevation(30);
    }

    private void configureMenuDrawer ()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView ()
    {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

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
}
