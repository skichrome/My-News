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
        configureAndShowFragmentById(getIntent().getStringExtra(ID_OTHERS_ACTIVITIES));
    }

    private void configureToolBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //=========================================
    // Fragments Methods
    //=========================================

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

    private void showArticleDetailsFragment (String url)
    {
        if (this.articleDetailsFragment == null)
            this.articleDetailsFragment = ArticleDetailsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString(ID_OTHERS_ACTIVITIES, url);
        articleDetailsFragment.setArguments(bundle);

        this.startTransactionFragment(this.articleDetailsFragment);
    }

    private void startTransactionFragment(Fragment fragment)
    {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction().replace(R.id.details_help_activity_frame_layout_for_fragments, fragment).commit();
    }
}