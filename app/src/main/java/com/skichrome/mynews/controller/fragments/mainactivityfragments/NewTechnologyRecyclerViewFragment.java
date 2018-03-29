package com.skichrome.mynews.controller.fragments.mainactivityfragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.skichrome.mynews.Utils.NewYorkTimesStreams;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;

import io.reactivex.observers.DisposableObserver;

/**
 * Contains the data of Article Search API in a recyclerView
 */
public class NewTechnologyRecyclerViewFragment extends BaseRecyclerViewFragment
{
    //=====================
    // Base Methods
    //=====================

    /**
     * Used each time we have to create this fragment to display it
     * @return
     *      new instance of this fragment
     */
    public static BaseRecyclerViewFragment newInstance ()
    {
        return new NewTechnologyRecyclerViewFragment();
    }

    /**
     * Send a request on API and update list by calling updateListResults method
     */
    @Override
    protected void configureDesign ()
    {
        //=========================
        // Http Request Method
        //=========================

        this.disposable = NewYorkTimesStreams.streamDownloadArticleSearchAPI("trump", "20180301", "20180329", "newest", false).subscribeWith(new DisposableObserver<MainNewYorkTimesArticleSearch>()
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
            public void onNext (MainNewYorkTimesArticleSearch mainNewYorkTimesArticleSearch)
            {
                Log.e("-----ArticleSearch-----", "onNext: Success ! Size of list : " + mainNewYorkTimesArticleSearch.getResponse().getDocs().size());
                updateListResults(mainNewYorkTimesArticleSearch);
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
            public void onError (Throwable e)
            {
                Log.e(this.getClass().getSimpleName(), "onError : ", e);
            }

            /**
             * Notifies the Observer that the {@link io.reactivex.Observable} has finished sending push-based notifications.
             * <p>
             * The {@link io.reactivex.Observable} will not call this method if it calls {@link #onError}.
             */
            @Override
            public void onComplete ()
            {
                Log.i(this.getClass().getSimpleName(), "onComplete : Background task terminated !");
            }
        });
    }

    /**
     * configure refresh on user swipe down on top of recyclerView
     */
    @Override
    protected void updateDesign ()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh ()
            {
                configureDesign();
            }
        });
    }


    private void updateListResults (MainNewYorkTimesArticleSearch mMainNewYorkTimesArticleSearch)
    {
        swipeRefreshLayout.setRefreshing(false);
        this.resultList.clear();

        this.resultList.addAll(dataAPIConverter.convertArticleSearchResult(mMainNewYorkTimesArticleSearch.getResponse().getDocs()));
        this.genericRVAdapter.notifyDataSetChanged();
    }
}