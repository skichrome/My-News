package com.skichrome.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.skichrome.mynews.R;
import com.skichrome.mynews.util.ArticleSampleForAPIConverter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A View Holder used for data update in cells of RecyclerView
 */
public class GenericRVViewHolder extends RecyclerView.ViewHolder
{
    //=====================
    // Fields
    //=====================

    /**
     * Image field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_image) ImageView mImageViewArticle;
    /**
     * section id field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_section_id) TextView mTextViewSectionId;
    /**
     * date field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_date) TextView mTexViewDate;
    /**
     * title field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_start_title) TextView mTexViewTitle;

    //=====================
    // Constructor
    //=====================

    /**
     * Used here to bind views with ButterKnife
     * @param itemView
     *      the view model
     */
    GenericRVViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //=====================
    // Base Methods
    //=====================

    /**
     * Used to update UI
     * @param mSample
     *      A specific article in resultList
     * @param mGlide
     *      Used to set image in view
     */
    void updateRecyclerViewItemData(ArticleSampleForAPIConverter mSample, RequestManager mGlide)
    {
        this.mTextViewSectionId.setText(mSample.getSection());
        this.mTexViewTitle.setText(mSample.getTitle());
        this.mTexViewDate.setText(mSample.getDate());

        if (mSample.getImageUrl() == null)
            mImageViewArticle.setBackgroundResource(R.drawable.default_newspaper);
        else
            mGlide.load(mSample.getImageUrl()).into(mImageViewArticle);
    }
}