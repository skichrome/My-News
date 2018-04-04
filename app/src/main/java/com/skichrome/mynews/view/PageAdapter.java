package com.skichrome.mynews.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skichrome.mynews.controller.fragments.ArticleFragment;

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
        //Used as a temp field to assign custom arguments in bundle
        Fragment articleFragment = ArticleFragment.newInstance();

        switch (position)
        {
            case 0 :
                articleFragment.setArguments(setBundleArgs("home", 10));
                break;

            case 1 :
                articleFragment.setArguments(setBundleArgs("all-sections", 20));
                break;

            case 2 :
                articleFragment.setArguments(setBundleArgs("technology", 10));
                break;

            case 3 :
                articleFragment.setArguments(setBundleArgs("sports", 10));
                break;

            case 4 :
                articleFragment.setArguments(setBundleArgs("science", 10));
                break;

            case 5 :
                articleFragment.setArguments(setBundleArgs("automobiles", 10));
                break;

            default:
                articleFragment.setArguments(setBundleArgs("home", 10));
        }

        return articleFragment;
    }

    /**
     * Set the number of pages in ViewPager
     * @return
     *      integer, the number of pages
     */
    @Override
    public int getCount ()
    {
        return 6;
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
                return "Technology";

            case 3 :
                return "Sports";

            case 4 :
                return "Science";

            case 5 :
                return "Automobiles";

            default:
                return "Page " + position;
        }
    }

    private Bundle setBundleArgs(String mSection, int mRequestId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("SECTION", mSection);
        bundle.putInt("REQUEST_ID", mRequestId);

        return bundle;
    }
}