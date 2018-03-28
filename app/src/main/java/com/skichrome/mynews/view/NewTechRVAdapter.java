package com.skichrome.mynews.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.skichrome.mynews.R;
import com.skichrome.mynews.controller.fragments.mainactivityfragments.NewTechnologyRecyclerViewFragment;
import com.skichrome.mynews.model.articlesearchapi.Doc;

import java.util.List;

/**
 * This adapter is used for Recycler view in {@link NewTechnologyRecyclerViewFragment}
 */
public class NewTechRVAdapter extends RecyclerView.Adapter<NewTechRVViewHolder>
{
    //=====================
    // Fields
    //=====================

    /**
     * Contains the list of articles to be displayed
     */
    private List<Doc> docList;
    /**
     * Used in the viewHolder to set image with a web url
     */
    private RequestManager glide;

    //=====================
    // Constructor
    //=====================

    /**
     * Used to setup class fields
     * @param mDocList
     *      Contains the list of articles to be displayed
     * @param mGlide
     *      Used in the viewHolder to set image with a web url
     */
    public NewTechRVAdapter (List<Doc> mDocList, RequestManager mGlide)
    {
        this.docList = mDocList;
        this.glide = mGlide;
    }

    //=====================
    // Adapter Methods
    //=====================

    /**
     * Called when RecyclerView needs a new {@link android.support.v7.widget.RecyclerView.ViewHolder} of the given type to represent
     * an item.
     *
     * @param parent
     *         The ViewGroup into which the new View will be added after it is bound to
     *         an adapter position.
     * @param viewType
     *         The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     *
     */
    @NonNull
    @Override
    public NewTechRVViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_list_item, parent, false);
        return new NewTechRVViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link android.support.v7.widget.RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder
     *         The ViewHolder which should be updated to represent the contents of the
     *         item at the given position in the data set.
     * @param position
     *         The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder (@NonNull NewTechRVViewHolder holder, int position)
    {
        holder.updateWithNewTechApiData(this.docList.get(position), this.glide);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount ()
    {
        return docList.size();
    }
}