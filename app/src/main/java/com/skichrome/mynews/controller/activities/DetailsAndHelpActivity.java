package com.skichrome.mynews.controller.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.otherfragments.AboutFragment;
import com.skichrome.mynews.controller.fragments.otherfragments.ArticleDetailsFragment;
import com.skichrome.mynews.controller.fragments.otherfragments.HelpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skichrome.mynews.controller.activities.MainActivity.ID_OTHERS_ACTIVITIES;

/**
 * Used to show about fragment and help fragment
 */
public class DetailsAndHelpActivity extends AppCompatActivity
{
    //=========================================
    // Fields
    //=========================================

    /**
     * link to {@link HelpFragment}
     */
    private Fragment helpFragment;
    /**
     * link to {@link AboutFragment}
     */
    private Fragment aboutFragment;
    /**
     * Link to {@link ArticleDetailsFragment}
     */
    private Fragment articleDetailsFragment;
    /**
     * Link to the toolbar
     */
    @BindView(R.id.activity_toolbar) Toolbar mToolbar;

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
        setContentView(R.layout.activity_details_and_help);

        ButterKnife.bind(this);

        configureToolBar();
        configureAndShowFragmentById(getIntent().getStringExtra(ID_OTHERS_ACTIVITIES));
    }

    /**
     * configure toolbar for this activity
     */
    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Fragments Methods
    //=========================================

    /**
     * Show specific fragment according to an id in parameter
     * @param idToLaunchFragment
     *      The id to launch specific fragment
     */
    private void configureAndShowFragmentById (String idToLaunchFragment)
    {
        switch (idToLaunchFragment)
        {
            case "help" :
                showHelpFragment();
                break;
            case "about" :
                showAboutFragment();
                break;

            default :
                showArticleDetailsFragment(idToLaunchFragment);
                break;
        }
    }

    /**
     * configure help fragment
     */
    private void showHelpFragment ()
    {
        if (this.helpFragment == null)
            this.helpFragment = HelpFragment.newInstance();

        setTitle("Help");

        this.startTransactionFragment(this.helpFragment);
    }

    /**
     * configure about fragment
     */
    private void showAboutFragment ()
    {
        if (this.aboutFragment == null)
            this.aboutFragment = AboutFragment.newInstance();


        setTitle("About");

        this.startTransactionFragment(this.aboutFragment);
    }

    /**
     * configure article details fragment
     */
    private void showArticleDetailsFragment (String url)
    {
        if (this.articleDetailsFragment == null)
            this.articleDetailsFragment = ArticleDetailsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString(ID_OTHERS_ACTIVITIES, url);
        articleDetailsFragment.setArguments(bundle);

        this.startTransactionFragment(this.articleDetailsFragment);
    }

    /**
     * Show fragment passed in parameter
     * @param fragment
     *      the fragment to show if it isn't already visible
     */
    private void startTransactionFragment(Fragment fragment)
    {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.details_help_activity_frame_layout_for_fragments, fragment).commit();
    }
}