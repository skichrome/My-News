package com.skichrome.mynews.controller;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.skichrome.mynews.util.androidjob.AndroidJobCreator;

/**
 * Main application class
 */
public class MainApp extends Application
{
    /**
     * Used to configure <a href="https://github.com/evernote/android-job">Android Job</a> library, this library must be configured in the application class
     */
    @Override
    public void onCreate()
    {
        super.onCreate();

        //configure Android Job library
        JobManager.create(this).addJobCreator(new AndroidJobCreator());
    }
}