package com.skichrome.mynews.controller.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.otheractivitiesfragments.AboutFragment;
import com.skichrome.mynews.controller.fragments.otheractivitiesfragments.ArticleDetailsFragment;
import com.skichrome.mynews.controller.fragments.otheractivitiesfragments.HelpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skichrome.mynews.controller.activities.MainActivity.ID_DETAILS_HELP_ACTIVITY_FOR_FRAGMENTS;

public class DetailsAndHelpActivity extends AppCompatActivity
{
    //=========================================
    // Fields
    //=========================================

    private int idToLaunchFragment;
    private Fragment helpFragment;
    private Fragment aboutFragment;
    private Fragment articleDetailsFragment;

    @BindView(R.id.activity_toolbar) Toolbar mToolbar;

    //=========================================
    // Overrided Methods
    //=========================================

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_and_help);

        ButterKnife.bind(this);

        idToLaunchFragment = getIntent().getIntExtra(ID_DETAILS_HELP_ACTIVITY_FOR_FRAGMENTS, 0);

        configureToolBar();
        configureAndShowFragmentById();
    }

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Fragments Methods
    //=========================================

    private void configureAndShowFragmentById ()
    {
        switch (idToLaunchFragment)
        {
            case 1 :
                showHelpFragment();
                break;
            case 2 :
                showAboutFragment();
                break;
            case 3 :
                showArticleDetailsFragment();
                break;

            default :
                break;
        }
    }

    private void showHelpFragment ()
    {
        if (this.helpFragment == null)
            this.helpFragment = HelpFragment.newInstance();

        this.startTransactionFragment(this.helpFragment);
    }

    private void showAboutFragment ()
    {
        if (this.aboutFragment == null)
            this.aboutFragment = AboutFragment.newInstance();

        mToolbar.setTitle("About");
        this.startTransactionFragment(this.aboutFragment);
    }

    private void showArticleDetailsFragment ()
    {
        if (this.articleDetailsFragment == null)
            this.articleDetailsFragment = ArticleDetailsFragment.newInstance();

        mToolbar.setTitle("Help");
        this.startTransactionFragment(this.articleDetailsFragment);
    }

    private void startTransactionFragment(Fragment fragment)
    {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.details_help_activity_frame_layout_for_fragments, fragment).commit();
    }
}
