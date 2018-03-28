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

import static com.skichrome.mynews.controller.activities.MainActivity.ID_OTHERS_ACTIVITIES;

public class DetailsAndHelpActivity extends AppCompatActivity
{
    //=========================================
    // Fields
    //=========================================

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

        configureToolBar();
        configureAndShowFragmentById(getIntent().getIntExtra(ID_OTHERS_ACTIVITIES, 0));
    }

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Fragments Methods
    //=========================================

    private void configureAndShowFragmentById (int idToLaunchFragment)
    {
        switch (idToLaunchFragment)
        {
            case 0 :
                showHelpFragment();
                break;
            case 1 :
                showAboutFragment();
                break;
            case 2 :
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

        setTitle("Help");

        this.startTransactionFragment(this.helpFragment);
    }

    private void showAboutFragment ()
    {
        if (this.aboutFragment == null)
            this.aboutFragment = AboutFragment.newInstance();


        setTitle("About");

        this.startTransactionFragment(this.aboutFragment);
    }

    private void showArticleDetailsFragment ()
    {
        if (this.articleDetailsFragment == null)
            this.articleDetailsFragment = ArticleDetailsFragment.newInstance();

        this.startTransactionFragment(this.articleDetailsFragment);
    }

    private void startTransactionFragment(Fragment fragment)
    {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.details_help_activity_frame_layout_for_fragments, fragment).commit();
    }
}
