package com.skichrome.mynews.controller.fragments.recyclerviewfragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.skichrome.mynews.Utils.NewYorkTimesStreams;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

/**
 * Contains the data of Article Search API in a recyclerView
 */
public class ArticleSearchRecyclerViewFragment extends BaseRecyclerViewFragment
{
    //=====================
    // Fields
    //=====================

    /**
     * contains a list of query search keywords for api request search
     */
    private ArrayList<String> queryList;

    /**
     * contain begin date for api request search
     */
    private String beginDate = null;

    /**
     * contain end date for api request search
     */
    private String endDate = null;

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
        return new ArticleSearchRecyclerViewFragment();
    }

    /**
     * Send a request on API and update list by calling updateListResults method
     */
    @Override
    protected void configureDesign ()
    {
        getSearchParameters();

        //=========================
        // Http Request Method
        //=========================

        this.disposable = NewYorkTimesStreams.streamDownloadArticleSearchAPI(this.queryList, this.beginDate, this.endDate).subscribeWith(new DisposableObserver<MainNewYorkTimesArticleSearch>()
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

    /**
     * Used to update api request results with data downloaded, format it in a usable form.
     *
     * @param mMainNewYorkTimesArticleSearch
     *      The result object
     */
    private void updateListResults (MainNewYorkTimesArticleSearch mMainNewYorkTimesArticleSearch)
    {
        swipeRefreshLayout.setRefreshing(false);
        this.resultList.clear();

        this.resultList.addAll(dataAPIConverter.convertArticleSearchResult(mMainNewYorkTimesArticleSearch.getResponse().getDocs()));
        this.genericRVAdapter.notifyDataSetChanged();
    }

    /**
     * Get the parameters stored in bundle, to launch specific api request
     */
    private void getSearchParameters()
    {
        this.queryList = new ArrayList<>();

        Bundle bundle = getArguments();

        if (bundle != null)
        {
            this.queryList.addAll(bundle.getStringArrayList("SEARCH_DATA_LIST"));
            this.beginDate = bundle.getString("SEARCH_DATA_BEGIN_DATE", null);
            this.endDate = bundle.getString("SEARCH_DATA_END_DATE", null);
        }
    }
}