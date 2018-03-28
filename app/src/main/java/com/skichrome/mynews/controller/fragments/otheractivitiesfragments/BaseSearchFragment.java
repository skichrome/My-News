package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.skichrome.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseSearchFragment extends Fragment
{
    //=========================
    // Fields
    //=========================

    @BindView(R.id.fragment_base_search_notification_keywords_edit_text) EditText mEditTextKeyWords;
    @BindView(R.id.fragment_base_search_notification_begin_date) TextView mTextViewBeginDate;
    @BindView(R.id.fragment_base_search_notification_end_date) TextView mTextViewEndDate;
    @BindView(R.id.fragment_base_search_notification_arts_check_box) CheckBox mCheckBoxArts;
    @BindView(R.id.fragment_base_search_notification_business_check_box) CheckBox mCheckBoxBusiness;
    @BindView(R.id.fragment_base_search_notification_entrepreneurs_check_box) CheckBox mCheckBoxEntrepreneurs;
    @BindView(R.id.fragment_base_search_notification_politics_check_box) CheckBox mCheckBoxPolitics;
    @BindView(R.id.fragment_base_search_notification_sports_check_box) CheckBox mCheckBoxSports;
    @BindView(R.id.fragment_base_search_notification_travel_check_box) CheckBox mCheckBoxTravel;
    @BindView(R.id.fragment_base_search_notification_switch) Switch mSwitch;
    @BindView(R.id.fragment_base_search_notification_button) Button mBtn;
    @BindView(R.id.fragment_base_search_notification_separator) View mViewSeparator;
    @BindView(R.id.fragment_base_search_notification_date_management_linear_layout) LinearLayout mLinearLayout;

    //=========================
    // Base Abstract Methods
    //=========================

    protected abstract void updateDesign();
    protected abstract void configureDesign();
    protected abstract void removeUnnecessaryFields();

    //=====================
    // Empty Constructor
    //=====================

    public BaseSearchFragment()
    {
        // Required empty public constructor
    }

    //=====================
    // Methods
    //=====================

    /**
     * get the fragment layout id used to process user info
     * @return
     *      integer, fragment layout id
     */
    private int getFragmentLayout()
    {
        return R.layout.fragment_base_search;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_base_search, container, false);

        ButterKnife.bind(this, view);

        //configure and update design
        this.removeUnnecessaryFields();
        this.configureDesign();
        this.updateDesign();

        // Inflate the layout for this fragment
        return view;
    }
}
