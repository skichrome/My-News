package com.skichrome.mynews.controller.fragments.otherfragments;


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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base abstract fragment for notification and article search api screens
 */
public abstract class BaseSearchFragment extends Fragment
{
    //=========================
    // Fields
    //=========================

    /**
     * contain the main text entry field for keywords to search on api
     */
    @BindView(R.id.fragment_base_search_notification_keywords_edit_text) EditText mEditTextKeyWords;
    /**
     * contain the begin date limit field that we want to use for api request, used in search screen
     */
    @BindView(R.id.fragment_base_search_notification_begin_date) TextView mTextViewBeginDate;
    /**
     * contain the end date limit field that we want to use for api request, used in search screen
     */
    @BindView(R.id.fragment_base_search_notification_end_date) TextView mTextViewEndDate;
    /**
     * contain the art checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_arts_check_box) CheckBox mCheckBoxArts;
    /**
     * contain the business checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_business_check_box) CheckBox mCheckBoxBusiness;
    /**
     * contain the entrepreneurs checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_entrepreneurs_check_box) CheckBox mCheckBoxEntrepreneurs;
    /**
     * contain the politics checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_politics_check_box) CheckBox mCheckBoxPolitics;
    /**
     * contain the sports checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_sports_check_box) CheckBox mCheckBoxSports;
    /**
     * contain the travel checkBox field
     */
    @BindView(R.id.fragment_base_search_notification_travel_check_box) CheckBox mCheckBoxTravel;
    /**
     * contain the switch field, used to enable and disable notifications
     */
    @BindView(R.id.fragment_base_search_notification_switch) Switch mSwitch;
    /**
     * contain button used to launch article search api request
     */
    @BindView(R.id.fragment_base_search_notification_button) Button mBtn;
    /**
     * Used as a decoration in notification screen, disabled in search screen
     */
    @BindView(R.id.fragment_base_search_notification_separator) View mViewSeparator;
    /**
     * Used as a container , used to disable date selection in notification screen
     */
    @BindView(R.id.fragment_base_search_notification_date_management_linear_layout) LinearLayout mLinearLayout;

    /**
     * Contain the  list of checkboxes displayed, used in user selection detection
     */
    protected List<CheckBox> checkBoxes1ist;
    /**
     * contain the list of query keywords to be used in api request
     */
    protected ArrayList<String> finalStringList = new ArrayList<>();
    /**
     * Contain the begin date for api request
     */
    protected String beginDate;
    /**
     * Contain the end date for api request
     */
    protected String endDate;

    //=========================
    // Base Abstract Methods
    //=========================

    /**
     * Used to configure the design, set up the view and listeners on fields
     */
    protected abstract void configureDesign();
    /**
     * update fields if needed
     */
    protected abstract void updateDesign ();

    //=====================
    // Empty Constructor
    //=====================

    /**
     * empty fragment constructor
     */
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

    /**
     * Used for setup at the creation of each fragment inherited
     *
     * @param inflater
     *      LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *      ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *      Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     *      Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(getFragmentLayout(), container, false);

        ButterKnife.bind(this, view);

        //configure and update design
        this.checkBoxes1ist = createCheckBoxesList();
        this.configureDesign();
        this.updateDesign();

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * create a list of all checkboxes available to simplify uses.
     * @return
     *      a list with all checkboxes
     */
    private List<CheckBox> createCheckBoxesList()
    {
        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(mCheckBoxArts);
        checkBoxes.add(mCheckBoxBusiness);
        checkBoxes.add(mCheckBoxEntrepreneurs);
        checkBoxes.add(mCheckBoxPolitics);
        checkBoxes.add(mCheckBoxSports);
        checkBoxes.add(mCheckBoxTravel);
        return checkBoxes;
    }

    /**
     * Get the user entry from all checkboxes and from editText field, and initialise dedicated variables with data collected
     */
    protected void getDataFromEntryFields()
    {
        for (CheckBox checkBox : checkBoxes1ist)
        {
            if (String.valueOf(checkBox.isChecked()).equals("true"))
                finalStringList.add(checkBox.getText().toString());
        }

        finalStringList.add(mEditTextKeyWords.getText().toString());
    }
}