package com.skichrome.mynews.controller.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity
{
    //=========================================
    // Base Abstract Methods
    //=========================================

    /**
     * Used to configure all things needed in activity
     */
    protected abstract void configureDesign();
    /**
     * Used to update fields if needed
     */
    protected abstract void updateDesign();
    /**
     * Used to bind layout to activity
     */
    protected abstract int getActivityLayout();

    //=========================================
    // Fields
    //=========================================

    /**
     * Used to pass data in intents to other activities
     */
    public static final String ID_OTHERS_ACTIVITIES = "Details_and_help_activity";

    //=========================================
    // Superclass Methods
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
        setContentView(getActivityLayout());

        ButterKnife.bind(this);
        this.configureDesign();
        this.updateDesign();
    }
}
