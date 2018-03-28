package com.skichrome.mynews.controller.fragments.mainactivityfragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.skichrome.mynews.R;
import com.skichrome.mynews.Utils.NewYorkTimesStreams;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;
import com.skichrome.mynews.model.topstoriesapi.Result;
import com.skichrome.mynews.view.TopStoriesRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Contains the data of Top Stories API in a recyclerView
 */
public class TopStoriesRecyclerViewFragment extends BaseRecyclerViewFragment
{
    //=====================
    // Fields
    //=====================

    /**
     * Link to the recyclerView container
     */
    @BindView(R.id.base_fragment_recycler_view) RecyclerView recyclerView;

    /**
     * Contains the list of article downloaded
     */
    private List<Result> mResultList;
    /**
     * Adapter field
     */
    private TopStoriesRVAdapter mTopStoriesAdapter;
    /**
     * Used for http request
     */
    Disposable disposable;

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
        return new TopStoriesRecyclerViewFragment();
    }

    /**
     * @see BaseRecyclerViewFragment
     */
    @Override
    protected void configureDesign ()
    {
        executeHttpRequest();
        configureRecyclerView();
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
                executeHttpRequest();
            }
        });
    }

    //=====================
    // Fragment Methods
    //=====================

    private void configureRecyclerView ()
    {
        if (this.mResultList == null)
            this.mResultList = new ArrayList<>();

        this.mTopStoriesAdapter = new TopStoriesRVAdapter(mResultList, Glide.with(this));
        this.recyclerView.setAdapter(this.mTopStoriesAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set a separation in each cells in recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    //=========================
    // Http Request Methods
    //=========================

    private void executeHttpRequest()
    {
        this.disposable = NewYorkTimesStreams.streamDownloadTopStoriesAPI("home").subscribeWith(new DisposableObserver<MainNewYorkTimesTopStories>()
        {
            @Override
            public void onNext (MainNewYorkTimesTopStories mainNewYorkTimesTopStories)
            {
                Log.e("-----TopStories-----", "onNext: Success !");
                updateListResults(mainNewYorkTimesTopStories);
            }

            @Override
            public void onError (Throwable e)
            {
                Log.e(this.getClass().getSimpleName(), "onError : ", e);
            }

            @Override
            public void onComplete ()
            {
                Log.i(this.getClass().getSimpleName(), "onComplete : Background task terminated !");
            }
        });
    }

private void updateListResults (MainNewYorkTimesTopStories mMainNewYorkTimesTopStories)
    {
        swipeRefreshLayout.setRefreshing(false);
        this.mResultList.clear();
        this.mResultList.addAll(mMainNewYorkTimesTopStories.getResults());
        this.mTopStoriesAdapter.notifyDataSetChanged();
    }
}