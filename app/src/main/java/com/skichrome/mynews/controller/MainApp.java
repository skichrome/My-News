package com.skichrome.mynews.controller;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.skichrome.mynews.utils.androidjob.AndroidJobCreator;

public class MainApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        JobManager.create(this).addJobCreator(new AndroidJobCreator());
    }
}