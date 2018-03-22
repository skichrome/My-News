package com.skichrome.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skichrome.mynews.controller.fragments.MostPopularFragment;
import com.skichrome.mynews.controller.fragments.NewTechnologyFragment;
import com.skichrome.mynews.controller.fragments.TopStoriesFragment;

/**
 * This adapter send the correct fragment to display
 */

public class PageAdapter extends FragmentStatePagerAdapter
{
    protected PageAdapter (FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem (int position)
    {
        switch (position)
        {
            case 0 :
                return TopStoriesFragment.newInstance();

            case 1 :
                return MostPopularFragment.newInstance();

            case 2 :
                return NewTechnologyFragment.newInstance();

                default:
                    return TopStoriesFragment.newInstance();
        }
    }

    @Override
    public int getCount ()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle (int position)
    {
        return "Page " + position;
    }
}