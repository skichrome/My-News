package com.skichrome.mynews.controller.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.skichrome.mynews.R;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.model.mostpopularapimostviewed.MainNewYorkTimesMostPopular;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;
import com.skichrome.mynews.util.ArticleNYTConverter;
import com.skichrome.mynews.util.ArticleSampleForAPIConverter;
import com.skichrome.mynews.util.ItemClickSupportOnRecyclerView;
import com.skichrome.mynews.util.NewYorkTimesStreams;
import com.skichrome.mynews.view.RVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * This fragment will display a recyclerView with a list of article from the NYT API
 */
public class ArticleFragment extends BaseFragment
{
    //=========================
    // CallBack Interface
    //=========================

    /**
     * Used for callback to activities
     */
    public interface OnRecyclerViewItemClicked
    {
        /**
         * Used when user click on a recyclerView cell to do something
         * @param url
         *      the url of article selected by user
         */
        void onRVItemClicked(String url);
    }

    //=========================
    // Fields
    //=========================

    /**
     * Link to the recyclerView container
     */
    @BindView(R.id.base_fragment_recycler_view) RecyclerView recyclerView;
    /**
     * Link to the refresh layout xml container
     */
    @BindView(R.id.base_fragment_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    /**
     * Adapter field
     */
    private RVAdapter RVAdapter;
    /**
     * Contains the list of article downloaded
     */
    private ArrayList<ArticleSampleForAPIConverter> resultList;
    /**
     * Used for http request
     */
    Disposable disposable;
    /**
     * Used to convert each api request in a formatted article list with only interesting fields, allow to use only one recyclerView adatper and view holder in the app
     */
    private ArticleNYTConverter articleNYTConverter = new ArticleNYTConverter();
    /**
     * Used for activity callback
     */
    private OnRecyclerViewItemClicked mCallback;
    /**
     * Used for custom request on api
     */
    private String section = null;
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
    /**
     * Used to identify which request we have to execute
     */
    private int requestId;

    //=========================
    // newInstance Method
    //=========================

    /**
     * Used each time we have to create this fragment to display it
     * @return
     *      new instance of this fragment
     */
    public static BaseFragment newInstance()
    {
        return new ArticleFragment();
    }

    //=========================
    // Superclass Methods
    //=========================

    /**
     * @see BaseFragment#configureDesign()
     */
    @Override
    protected void configureDesign()
    {
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.getRequestId();
        this.executeHttpRequest();
    }

    /**
     * @see BaseFragment#updateDesign()
     */
    @Override
    protected void updateDesign()
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

    /**
     * @see BaseFragment#getFragmentLayout()
     */
    @Override
    protected int getFragmentLayout()
    {
        return R.layout.fragment_article;
    }

    /**
     * Used here to create callback to parent activity
     * @param context
     *      	Context
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    //=========================
    // RecyclerView Methods
    //=========================

    /**
     * Used to configure the recyclerView, and to set a divider between each recyclerView cells
     */
    private void configureRecyclerView ()
    {
        this.resultList = new ArrayList<>();

        this.RVAdapter = new RVAdapter(resultList, Glide.with(this));
        this.recyclerView.setAdapter(RVAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set a separation in each cells in recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * Configure the possibility of user to have a callback when he click on a recyclerView item.
     */
    private void configureOnClickRecyclerView()
    {
        ItemClickSupportOnRecyclerView.addTo(recyclerView, R.layout.recycler_view_list_item)
                .setOnItemClickListener(new ItemClickSupportOnRecyclerView.OnItemClickListener()
                {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v)
                    {
                        String articleUrl = RVAdapter.getArticle(position);
                        mCallback.onRVItemClicked(articleUrl);
                    }
                });
    }

    //=========================
    // Other Private Methods
    //=========================

    /**
     * Create a callback to parent activity, display a log if error is thrown
     */
    private void createCallbackToParentActivity()
    {
        try
        {
            mCallback = (OnRecyclerViewItemClicked) getActivity();
        }
        catch (ClassCastException e)
        {
            Log.e("createCallback", " Must Implement OnButtonClickedListener ", e);
        }
    }

    /**
     * Used to update api request results with data downloaded, format it in a usable form.
     *
     * @param mResultsList
     *      The result object
     */
    private void updateListResults (List<ArticleSampleForAPIConverter> mResultsList)
    {
        if (mResultsList.size() == 0)
            this.showAlertNoArticleFound();

        swipeRefreshLayout.setRefreshing(false);
        this.resultList.clear();

        this.resultList.addAll(mResultsList);
        this.RVAdapter.notifyDataSetChanged();
    }

    /**
     * Get common parameters stored in bundle, before executing Http request
     */
    private void getRequestId()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
            this.requestId = bundle.getInt("REQUEST_ID");
    }

    /**
     * Get the parameters stored in bundle, to launch specific api request
     */
    private void getSearchParameters()
    {
        this.queryList = new ArrayList<>();
        Bundle bundle = getArguments();

        if (bundle != null)
            this.section = bundle.getString("SECTION");
    }

    /**
     * Get the parameters stored in bundle, to launch specific api request
     */
    private void getSearchParametersForArticleSearch()
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

    /**
     * Execute http request according to the request id in bundle
     */
    private void executeHttpRequest()
    {
        swipeRefreshLayout.setRefreshing(true);

        switch (requestId)
        {
            case 10 :
                getTopStoriesResultsOnAPI();
                break;

            case 20 :
                getMostPopularResultsOnAPI();
                break;

            case 30 :
                getArticleSearchResultsOnAPI();
                break;

            default:    // secure option to avoid crash or blank screen if there is a unknown problem with id and bundle
                this.section = "home";
                getTopStoriesResultsOnAPI();
                break;
        }
    }

    //==================================================
    // Top Stories Http Request Method
    //==================================================

    /**
     * Send a Top Stories request on API and update list by calling updateListResults method, with formatted articles in {@link ArticleNYTConverter}
     */
    private void getTopStoriesResultsOnAPI ()
    {
        getSearchParameters();

        this.disposable = NewYorkTimesStreams.streamDownloadTopStoriesAPI(section).subscribeWith(new DisposableObserver<MainNewYorkTimesTopStories>()
        {
            /**
             * Provides the Observer with a new item to observe.
             * <p>
             * The {@link io.reactivex.Observable} may call this method 0 or more times.
             * <p>
             * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
             * {@link #onError}.
             *
             * @param mMainNewYorkTimesTopStories
             *         the item emitted by the Observable
             */
            @Override
            public void onNext (MainNewYorkTimesTopStories mMainNewYorkTimesTopStories)
            {
                Log.e("-----TopStories-----", "onNext: Success ! Size of list : " + mMainNewYorkTimesTopStories.getResults().size());
                updateListResults(articleNYTConverter.convertTopStoriesResult(mMainNewYorkTimesTopStories.getResults()));
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

    //==================================================
    // Most Popular Http Request Method
    //==================================================

    /**
     * Send a Most Popular request on API and update list by calling updateListResults method, with formatted articles in {@link ArticleNYTConverter}
     */
    private void getMostPopularResultsOnAPI()
    {
        getSearchParameters();

        this.disposable = NewYorkTimesStreams.streamDownloadMostPopularAPI(section).subscribeWith(new DisposableObserver<MainNewYorkTimesMostPopular>()
        {
            /**
             * Provides the Observer with a new item to observe.
             * <p>
             * The {@link io.reactivex.Observable} may call this method 0 or more times.
             * <p>
             * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
             * {@link #onError}.
             *
             * @param mainNewYorkTimesMostPopular
             *         the item emitted by the Observable
             */
            @Override
            public void onNext (MainNewYorkTimesMostPopular mainNewYorkTimesMostPopular)
            {
                Log.e("-----MostPopular-----", "onNext: Success ! Size of list : " + mainNewYorkTimesMostPopular.getResults().size());
                updateListResults(articleNYTConverter.convertMostPopularResult(mainNewYorkTimesMostPopular.getResults()));
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

    //==================================================
    // Article Search Http Request Method
    //==================================================

    /**
     * Send a Article Search request on API and update list by calling updateListResults method, with formatted articles in {@link ArticleNYTConverter}
     */
    private void getArticleSearchResultsOnAPI()
    {
        getSearchParametersForArticleSearch();

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
     * If api request return an empty list of articles, we show an alertDialog who tell to the user the "problem", and quit the app on positive button click
     */
    private void showAlertNoArticleFound()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bad news...");
        builder.setMessage("Your request didn't return anything...");
        builder.setPositiveButton("Go back", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
}