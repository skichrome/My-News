package com.skichrome.mynews.controller.fragments.mainactivityfragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.skichrome.mynews.R;
import com.skichrome.mynews.Utils.NewYorkTimesStreams;
import com.skichrome.mynews.model.articlesearchapi.Doc;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.view.NewTechRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Contains the data of Article Search API in a recyclerView
 */
public class NewTechnologyRecyclerViewFragment extends BaseRecyclerViewFragment
{
    //=====================
    // Fields
    //=====================

    /**
     * Link to the recyclerView container
     */
    @BindView(R.id.base_fragment_recycler_view)
    RecyclerView recyclerView;

    /**
     * Contains the list of article downloaded
     */
    private List<Doc> mDocList;
    /**
     * Adapter field
     */
    private NewTechRVAdapter mNewTechRVAdapter;
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
        return new NewTechnologyRecyclerViewFragment();
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
        if (this.mDocList == null)
            this.mDocList = new ArrayList<>();

        this.mNewTechRVAdapter = new NewTechRVAdapter(mDocList, Glide.with(this));
        this.recyclerView.setAdapter(mNewTechRVAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set a separation in each cells in recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    //=========================
    // Http Request Methods
    //=========================

    private void executeHttpRequest ()
    {
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

    private void updateListResults (MainNewYorkTimesArticleSearch mMainNewYorkTimesArticleSearch)
    {
        swipeRefreshLayout.setRefreshing(false);
        this.mDocList.clear();
        this.mDocList.addAll(mMainNewYorkTimesArticleSearch.getResponse().getDocs());
        this.mNewTechRVAdapter.notifyDataSetChanged();
    }
}