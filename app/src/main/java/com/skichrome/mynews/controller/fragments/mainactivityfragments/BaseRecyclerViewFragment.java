package com.skichrome.mynews.controller.fragments.mainactivityfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.skichrome.mynews.R;
import com.skichrome.mynews.Utils.ArticleSampleForAPIConverter;
import com.skichrome.mynews.Utils.DataAPIConverter;
import com.skichrome.mynews.Utils.ItemClickSupportOnRecyclerView;
import com.skichrome.mynews.view.GenericRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * This is the model used to define most of the fragments who will display news in a RecyclerView in ViewPager defined in {@link com.skichrome.mynews.controller.activities.MainActivity}
 */
public abstract class BaseRecyclerViewFragment extends Fragment
{
    //=========================
    // CallBack Interface
    //=========================

    public interface OnRecyclerViewItemClicked
    {
        void onRVItemclicked(String url);
    }

    //=========================
    // Fields
    //=========================
    /**
     * Link to the recyclerView container
     */
    @BindView(R.id.base_fragment_recycler_view) RecyclerView recyclerView;
    /**
     * Adapter field
     */
    protected GenericRVAdapter genericRVAdapter;
    /**
     * Contains the list of article downloaded
     */
    protected List<ArticleSampleForAPIConverter> resultList;
    /**
     * Used for http request
     */
    Disposable disposable;

    @BindView(R.id.base_fragment_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    protected DataAPIConverter dataAPIConverter = new DataAPIConverter();
    protected OnRecyclerViewItemClicked mCallback;

    //=========================
    // Base Abstract Methods
    //=========================

    /**
     * Used to configure the design, for example here will be executed the http Request, the RecyclerView will be configured here
     */
    protected abstract void configureDesign();

    /**
     * configure refresh on user swipe down on top of recyclerView
     */
    protected abstract void updateDesign ();

    //=====================
    // Empty Constructor
    //=====================

    /**
     * Empty constructor, needed for Fragment instantiation, not modifiable
     */
    public BaseRecyclerViewFragment ()
    {
    }

    //=====================
    // Methods
    //=====================

    /**
     * Called when a fragment needs to be displayed, setup the views (ButterKnife) and call the configureDesign method (defined in child class)
     *
     * @param inflater
     *      used to setup view
     * @param container
     *      used to setup view
     * @param savedInstanceState
     *      Used to restore data if needed
     * @return
     *      a view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        //bind view
        ButterKnife.bind(this, view);
        // Configure and update Design (Developer will call this method instead of override onCreateView())
        this.configureDesign();
        this.updateDesign();

        this.configureRecyclerView();

        //configure item click on RecyclerView
        configureOnClickRecyclerView();

        return(view);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        createCallbackToParentActivity();
    }

    /**
     * Get the layout file used for child fragments
     * @return
     *      integer, the id of the layout file
     */
    private int getFragmentLayout ()
    {
        return R.layout.base_fragment_for_recycler_view;
    }

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
            Log.e(" createCallback ", " Must Implement OnButtonClickedListener ", e);
        }
    }

    //=========================
    // RecyclerView Methods
    //=========================

    private void configureRecyclerView ()
    {
        if (this.resultList == null)
            this.resultList = new ArrayList<>();

        this.genericRVAdapter = new GenericRVAdapter(resultList, Glide.with(this));
        this.recyclerView.setAdapter(genericRVAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set a separation in each cells in recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    protected void configureOnClickRecyclerView()
    {
        ItemClickSupportOnRecyclerView.addTo(recyclerView, R.layout.recycler_view_list_item)
                .setOnItemClickListener(new ItemClickSupportOnRecyclerView.OnItemClickListener()
                {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v)
                    {
                        Log.d("ItemClickSupport", "Position : " + position);
                        String articleUrl = genericRVAdapter.getArticle(position);

                        mCallback.onRVItemclicked(articleUrl);
                    }
                });
    }
}