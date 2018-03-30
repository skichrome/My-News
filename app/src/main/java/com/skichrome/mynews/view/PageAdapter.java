package com.skichrome.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skichrome.mynews.controller.fragments.mainactivityfragments.MostPopularRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.ArticleSearchRecyclerViewFragment;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.TopStoriesRecyclerViewFragment;

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
                return TopStoriesRecyclerViewFragment.newInstance();

            case 1 :
                return MostPopularRecyclerViewFragment.newInstance();

            case 2 :
                return ArticleSearchRecyclerViewFragment.newInstance();

            default:
                return TopStoriesRecyclerViewFragment.newInstance();
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
                return "Article Search";

            default:
                return "Page " + position;
        }
    }
}