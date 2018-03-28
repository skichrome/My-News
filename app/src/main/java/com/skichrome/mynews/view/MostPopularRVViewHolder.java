package com.skichrome.mynews.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.skichrome.mynews.R;
import com.skichrome.mynews.Utils.Date;
import com.skichrome.mynews.model.mostpopularapimostviewed.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Used in {@link MostPopularRVAdapter}
 */
public class MostPopularRVViewHolder extends RecyclerView.ViewHolder
{
    //=====================
    // Fields
    //=====================

    /**
     * Used to convert date in a usable format
     * @see com.skichrome.mynews.Utils.Date
     */
    private Date date = new Date();

    /**
     * Image field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_image)
    ImageView mImageViewArticle;
    /**
     * section id field in recylerView
     */
    @BindView(R.id.list_item_recycler_view_section_id)
    TextView mTextViewSectionId;
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
    MostPopularRVViewHolder (View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //=====================
    // Base Methods
    //=====================

    /**
     * Used to update UI
     * @param result
     *      A specific article in resultList
     * @param glide
     *      Used to set image in view
     */
    void updateWithMostPopularApiData (Result result, RequestManager glide)
    {
        this.mTexViewTitle.setText(result.getTitle());
        this.mTextViewSectionId.setText(convertSectionId(result.getSection()));

        //convert and show date
        date.extractDataFromDateFormatted(date.deleteEndOfString(result.getPublishedDate()));
        this.mTexViewDate.setText(date.toString());

        //set image in result, if no image available display a default image (useful if the RecyclerView is scrolled very quickly to avoid crash)
        if (result.getMedia().get(0).getMediaMetadata().size() != 0)
            glide.load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(mImageViewArticle);
        else
            this.mImageViewArticle.setBackgroundResource(R.drawable.default_newspaper);
    }

    /**
     * this method convert in a String all slash characters detected with '>'
     * @param rawSectionTitle
     *      Contain the raw String that we have to analyse and transform
     * @return
     *      The converted String
     */
    private String convertSectionId (String rawSectionTitle)
    {
        return rawSectionTitle.replaceAll("/", ">");
    }
}