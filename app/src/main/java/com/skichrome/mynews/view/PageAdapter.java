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
    /**
     * Used to pass the FragmentManager to the superclass
     * @param fm
     *      the FragmentManager
     */
    protected PageAdapter (FragmentManager fm)
    {
        super(fm);
    }

    /**
     * Return the needed fragment
     * @param position
     *      Determine the fragment that we have to instantiate, represent the number of the page in ViewPager
     * @return
     *      New specific Fragment
     */
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

    /**
     * Set the number of pages in ViewPager
     * @return
     *      integer, the number of pages
     */
    @Override
    public int getCount ()
    {
        return 3;
    }

    /**
     * Used to set the title of each Tab with a position
     * @param position
     *      the position of the page that we want to set the title
     * @return
     *      the title according to the position
     */
    @Override
    public CharSequence getPageTitle (int position)
    {
        switch (position)
        {
            case 0 :
                return "Top Stories";

            case 1 :
                return "Most Popular";

            case 2 :
                return "New Tech";

            default:
                return "Page " + position;
        }
    }
}