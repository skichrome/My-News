package com.skichrome.mynews.utils.androidjob;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.activities.MainActivity;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.utils.ArticleSampleForAPIConverter;
import com.skichrome.mynews.utils.DataAPIConverter;
import com.skichrome.mynews.utils.NewYorkTimesStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ShowNotificationJob extends Job
{
    public static final String TAG = "notification_job_tag";
    private static final String CHANNEL_ID = "channel_id";
    private static SharedPreferences searchParameters;
    private Disposable disposable;
    private ArrayList<String> searchKeywordsList;
    private ArrayList<ArticleSampleForAPIConverter> resultList;
    private DataAPIConverter dataAPIConverter = new DataAPIConverter();
    private int arrayListSize;

    public static int schedulePeriodicJob()
    {
        return new JobRequest.Builder(ShowNotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    public static void cancelJob(int jobId)
    {
        JobManager.instance().cancel(jobId);
    }

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params)
    {
        searchKeywordsList = new ArrayList<>();
        searchParameters = getContext().getSharedPreferences("searchParameters", Context.MODE_PRIVATE);

        arrayListSize = searchParameters.getInt("SIZE_OF_LIST_KEYWORDS", 0);

        for (int i = 0; i < arrayListSize; i++)
            searchKeywordsList.add(searchParameters.getString("PREF_KEY_SEARCH_KEYWORDS_" + i, null));

        this.getArticleSearchResultsOnAPI();

        return Result.SUCCESS;
    }

    /**
     * Send a Article Search request on API and update list by calling updateListResults method, with formatted articles in {@link DataAPIConverter}
     */
    private void getArticleSearchResultsOnAPI()
    {
        this.disposable = NewYorkTimesStreams.streamDownloadArticleSearchAPI(this.searchKeywordsList, null, null).subscribeWith(new DisposableObserver<MainNewYorkTimesArticleSearch>()
        {
            /**
             * Provides the Observer with a new item to observe.
             * <p>
             * The {@link io.reactivex.Observable} may call this method 0 or more times.
             * <p>
             * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
             * {@link #onError}.
             *
             * @param mainNewYorkTimesArticleSearch
             *         the item emitted by the Observable
             */
            @Override
            public void onNext(MainNewYorkTimesArticleSearch mainNewYorkTimesArticleSearch)
            {
                Log.e("-----ArticleSearch-----", "onNext: Success ! Size of list : " + mainNewYorkTimesArticleSearch.getResponse().getDocs().size());
                updateListResults(dataAPIConverter.convertArticleSearchResult(mainNewYorkTimesArticleSearch.getResponse().getDocs()));
            }

            /**
             * Notifies the Observer that the {@link io.reactivex.Observable} has experienced an error condition.
             * <p>
             * If the {@link io.reactivex.Observable} calls this method, it will not thereafter call {@link #onNext} or
             * {@link #onComplete}.
             *
             * @param e
             *         the exception encountered by the Observable
             */
            @Override
            public void onError(Throwable e)
            {
                Log.e(this.getClass().getSimpleName(), "onError : ", e);
            }

            /**
             * Notifies the Observer that the {@link io.reactivex.Observable} has finished sending push-based notifications.
             * <p>
             * The {@link io.reactivex.Observable} will not call this method if it calls {@link #onError}.
             */
            @Override
            public void onComplete()
            {
                Log.i(this.getClass().getSimpleName(), "onComplete : Background task terminated !");
            }
        });
    }

    /**
     * Used to update api request results with data downloaded, format it in a usable form.
     *
     * @param mResultsList
     *         The result object
     */
    private void updateListResults(List<ArticleSampleForAPIConverter> mResultsList)
    {
        this.resultList = new ArrayList<>();
        this.resultList.addAll(mResultsList);

        if (resultList.size() != 0)
            configureNotification();
    }

    private void configureNotification()
    {
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID);
        notification.setSmallIcon(R.drawable.default_newspaper)
                .setContentTitle("My News")
                .setContentText("New articles availables !")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(0, notification.build());
    }
}
