package com.skichrome.mynews.controller.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.skichrome.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_main_tabs) TabLayout mTabLayout;
    @BindView(R.id.activity_main_view_pager) ViewPager mViewPager;
    @BindView(R.id.activity_main_menu_drawer_layout) DrawerLayout mDrawerLayout;

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
        configureViewPagerAndTabs();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        return true;
    }

    //=========================================
    // Configuration Methods
    //=========================================

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.activity_main_menu);
    }

    private void configureMenuDrawer ()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureViewPagerAndTabs ()
    {
    }
}
