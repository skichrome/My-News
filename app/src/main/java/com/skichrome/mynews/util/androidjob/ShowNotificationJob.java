package com.skichrome.mynews.util.androidjob;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
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
import com.skichrome.mynews.util.ArticleNYTConverter;
import com.skichrome.mynews.util.ArticleSampleForAPIConverter;
import com.skichrome.mynews.util.NewYorkTimesStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * this class is dedicated to Android Job library methods
 * <p>
 * Here we create a new Job, there is one method to start Job and one to stop it, when run the job the app make a request on ArticleSearch API and
 * check if articles are returned with keywords in sharedPreferences, if articles are available, we display a notification, that allow user to go to the app
 * </p>
 */
public class ShowNotificationJob extends Job
{
    /**
     * Contain the tag for the job identification
     */
    public static final String TAG = "notification_job_tag";
    /**
     * contain the id for channel used for notification (needed on Android API 26 and above)
     */
    private static final String CHANNEL_ID = "channel_id";
    /**
     * Contain the list of query keywords used to launch ArticleSearch api request
     */
    private ArrayList<String> searchKeywordsList;
    /**
     * Used to convert the API result in a usable format in recyclerView
     */
    private ArticleNYTConverter articleNYTConverter = new ArticleNYTConverter();

    /**
     * Create and launch a new job with specific id
     * <p>
     * the job is periodic (once a day), require network to be executed,
     * if a second job is created he replace the first created job.
     * </p>
     *
     * @return integer, id of the job created
     */
    public static int schedulePeriodicJob()
    {
        return new JobRequest.Builder(ShowNotificationJob.TAG)
                .setPeriodic(TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(2))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    /**
     * Used to cancel a job (with switch in the app)
     * @param jobId
     *      the id of job to cancel
     */
    public static void cancelJob(int jobId)
    {
        JobManager.instance().cancel(jobId);
    }

    /**
     * This method is invoked from a background thread. You should run your desired task here.
     * This method is thread safe. Each time a job starts executing a new instance of your {@link Job}
     * is instantiated. You can identify your {@link Job} with the passed {@code params}.
     *
     * <br>
     * <br>
     *
     * You should call {@link #isCanceled()} frequently for long running jobs and stop your
     * task if necessary.
     *
     * <br>
     * <br>
     *
     * A {@link PowerManager.WakeLock} is acquired for 3 minutes for each {@link Job}. If your task
     * needs more time, then you need to create an extra {@link PowerManager.WakeLock}.
     *
     * @param params The parameters for this concrete job.
     * @return The result of this {@link Job}. Note that returning {@link Result#RESCHEDULE} for a periodic
     * {@link Job} is invalid and ignored.
     */
    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params)
    {
        searchKeywordsList = new ArrayList<>();
        SharedPreferences searchParameters = getContext().getSharedPreferences("searchParameters", Context.MODE_PRIVATE);

        int arrayListSize = searchParameters.getInt("SIZE_OF_LIST_KEYWORDS", 0);

        for (int i = 0; i < arrayListSize; i++)
            searchKeywordsList.add(searchParameters.getString("PREF_KEY_SEARCH_KEYWORDS_" + i, null));

        this.getArticleSearchResultsOnAPI();

        return Result.SUCCESS;
    }

    /**
     * Send a Article Search request on API and update list by calling updateListResults method, with formatted articles in {@link ArticleNYTConverter}
     */
    private void getArticleSearchResultsOnAPI()
    {
        Disposable disposable = NewYorkTimesStreams.streamDownloadArticleSearchAPI(this.searchKeywordsList, null, null).subscribeWith(new DisposableObserver<MainNewYorkTimesArticleSearch>()
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
                updateListResults(articleNYTConverter.convertArticleSearchResult(mainNewYorkTimesArticleSearch.getResponse().getDocs()));
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
        ArrayList<ArticleSampleForAPIConverter> resultList = new ArrayList<>(mResultsList);

        if (resultList.size() != 0)
            configureNotification();
    }

    /**
     * Used to configure a system notification
     * <p>
     *     The notification is configured to launch an activity when user click on it, on click
     *     the notification is automatically canceled
     * </p>
     * <p>
     *     To support recent Android versions (API 26+) we need to set a notification channel for these
     *     versions, and attach it to the notification manager
     * </p>
     */
    private void configureNotification()
    {
        //used to launch activity on user click
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        //create the notification with title, message and icon
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_app_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle("Real Time Alert")
                .setContentText("New articles available !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //for recent Android versions... create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = getContext().getString(R.string.channel_name);
            String description = getContext().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null)
            {
                notificationManager.createNotificationChannel(channel);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(10, mBuilder.build());
            }
        }
        //default notification for older Android versions
        else
        {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            notificationManager.notify(10, mBuilder.build());
        }
    }
}