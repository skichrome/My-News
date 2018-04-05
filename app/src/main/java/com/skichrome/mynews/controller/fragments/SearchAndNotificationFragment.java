package com.skichrome.mynews.controller.fragments;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.skichrome.mynews.R;
import com.skichrome.mynews.utils.androidjob.ShowNotificationJob;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment for notification and article search api screens
 */
public class SearchAndNotificationFragment extends BaseFragment implements View.OnClickListener
{
    //=========================
    // Callback interface
    //=========================

    /**
     * Used for callback to activities
     */
    public interface OnButtonSearchClickedCallback
    {
        /**
         * Used when user click on search button
         * @param mQueryList
         *      contain a list of query search keywords
         * @param mBeginDate
         *      contain the begin date for search request
         * @param mEndDate
         *      contain the end date for search request
         */
        void onButtonSearchClicked(ArrayList<String> mQueryList, String mBeginDate, String mEndDate);
    }

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
     * used to display information according to the screen that we want to display
     */
    private int screenId;
    /**
     * Used to detect if user has typed at least one keyword before clicking on button
     */
    private boolean editTextState = false;
    /**
     * Used for callback to activities
     */
    private OnButtonSearchClickedCallback mCallback;

    /**
     * Contain the  list of checkboxes displayed, used in user selection detection
     */
    private List<CheckBox> checkBoxes1ist;
    /**
     * contain the list of query keywords to be used in api request
     */
    private ArrayList<String> finalStringList = new ArrayList<>();

    /**
     * Used to store and restore options and keywords typed by user on notification screen
     */
    private SharedPreferences searchParameters;
    /**
     * Used to identify job task, and cancel id when needed
     */
    private int jobId;

    //=====================
    // newInstance Method
    //=====================

    /**
     * Used each time we have to create this fragment to display it
     *
     * @return new instance of this fragment
     */
    public static Fragment newInstance()
    {
        return new SearchAndNotificationFragment();
    }

    //=====================
    // Superclass Methods
    //=====================

    /**
     * @see BaseFragment#configureDesign()
     */
    @Override
    protected void configureDesign()
    {
        searchParameters = getContext().getSharedPreferences("searchParameters", MODE_PRIVATE);

        this.checkBoxes1ist = this.createCheckBoxesList();
        this.getDataFromBundle();
        this.removeUselessEntryFields();
        this.createCallbackToParentActivity();
        this.setTextEntryListenerForButtonActivation();
    }

    /**
     * @see BaseFragment#updateDesign()
     */
    @Override
    protected void updateDesign()
    {
    }

    /**
     * @return integer, id
     *
     * @see BaseFragment#getFragmentLayout()
     */
    @Override
    protected int getFragmentLayout()
    {
        return R.layout.fragment_search_and_notification;
    }

    //=====================
    // onClick Methods
    //=====================

    /**
     * Used to set the date fields by creating a Date Picker to allow user to select a date,also used to get user data
     * on search button by calling {@link SearchAndNotificationFragment#getDataFromEntryFields()} method.
     * Finally used to set the notifications to On or Off
     *
     * @param v
     *      The view clicked by the user
     */
    @Override
    public void onClick(View v)
    {
        //Setup the notifications to On or Off for notification screen
        if (v == mSwitch)
        {
            if (mSwitch.isChecked())
            {
                boolean checkBoxesState = checkIfUserHaveSelectedAtLeastOneCheckBox();
                //ensure that user have typed one keyword and checked one checkbox
                if (editTextState && checkBoxesState)
                {
                    //get the user request keywords
                    this.getDataFromEntryFields();
                    this.storeDataInSharedPreferences();
                    //start job
                    this.jobId = ShowNotificationJob.schedulePeriodicJob();
                    Log.e("-----JOB_STATUS-----", "onClick: job activated");
                }
                else
                {
                    mSwitch.setChecked(false);
                    Toast.makeText(getContext(), "You must select one category and type one word", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                ShowNotificationJob.cancelJob(jobId);
                Log.e("-----JOB_STATUS-----", "onClick: job disabled");
            }
        }

        // get data from entry fields here if user click on button, if he clicked on button, other "if" will not be executed
        if (v == mBtn)
        {
            //Used to detect if user has checked at least one checkbox before clicking on button
            boolean checkBoxesState = checkIfUserHaveSelectedAtLeastOneCheckBox();
            //ensure that user have typed one keyword and checked one checkbox
            if (editTextState && checkBoxesState)
                this.getDataFromEntryFields();
            else
                Toast.makeText(getContext(), "You must select one category and type one word", Toast.LENGTH_SHORT).show();
        }

        //Get a calendar instance
        final Calendar myCalendar = Calendar.getInstance();

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        if (v == mTextViewBeginDate)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    String date = (dayOfMonth + "/" + (month + 1) + "/" + year);

                    mTextViewBeginDate.setText(date);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
        if (v == mTextViewEndDate)
        {
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    String date = (dayOfMonth + "/" + (month + 1) + "/" + year);

                    mTextViewEndDate.setText(date);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }

    /**
     * Store data with sharedPreferences, used to restore screen state, and execute in planified task http request
     */
    private void storeDataInSharedPreferences()
    {
        for (String finalString : finalStringList)
            searchParameters.edit().putString("PREF_KEY_SEARCH_KEYWORDS_" + finalStringList.indexOf(finalString), finalString).apply();

        searchParameters.edit().putInt("SIZE_OF_LIST_KEYWORDS", finalStringList.size()).apply();
        searchParameters.edit().putBoolean("SWITCH_STATE", mSwitch.isChecked()).apply();
    }

    /**
     * Check if user has checked at least one checkbox, and set the button color if true; he's not allowed to launch request without any checkbox selected
     * @return
     *      boolean, true if a checked checkbox is detected, else return false
     */
    private boolean checkIfUserHaveSelectedAtLeastOneCheckBox()
    {
        for (CheckBox checkBox : checkBoxes1ist)
        {
            if (checkBox.isChecked())
                return true;
        }
        return false;
    }

    //==========================================
    // Configuration Methods
    //==========================================

    /**
     * create a list of all checkboxes available to simplify uses.
     *
     * @return a list with all checkboxes
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
     * Get the data passed in bundle and set up fields on screen if data is stored in sharedPreferences
     */
    private void getDataFromBundle()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
            this.screenId = bundle.getInt("ID_SEARCH_NOTIFICATION_FRAG");

        //set the switch to activated if a job is already started
        this.mSwitch.setChecked(searchParameters.getBoolean("SWITCH_STATE", false));

        //get the size of arrayList stored in sharedPreferences for for loop
        int arrayListSize = searchParameters.getInt("SIZE_OF_LIST_KEYWORDS", 0);
        //set the editText content with the first item of list
        mEditTextKeyWords.setText(searchParameters.getString("PREF_KEY_SEARCH_KEYWORDS_0", ""));
        //check if words stored match with checkboxes titles, if match set checkbox state to checked
        for (int i = 0; i < arrayListSize; i++)
        {
            for (CheckBox checkBox : checkBoxes1ist)
            {
                String wordToCompare = searchParameters.getString("PREF_KEY_SEARCH_KEYWORDS_" + i, null);

                if (wordToCompare.equals(checkBox.getText().toString()))
                    checkBox.setChecked(true);
            }
        }
    }

    /**
     * This method remove the fields useless for each screen, and set a click listener on needed fields
     */
    private void removeUselessEntryFields()
    {
        switch (screenId)
        {
            case 0 :
                //For search screen : Remove the switch field and the separator because they are useless
                this.mViewSeparator.setVisibility(View.GONE);
                this.mSwitch.setVisibility(View.GONE);

                //set elevation for android version above lollipop
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    this.mBtn.setElevation(30);

                //set onClickListener for search button
                this.mBtn.setOnClickListener(this);
                break;

            case 1 :
                //for notification screen : remove the date selection fields and the button because they are useless
                this.mLinearLayout.setVisibility(View.GONE);
                this.mBtn.setVisibility(View.GONE);

                //set onClickListener for switch
                this.mSwitch.setOnClickListener(this);
                break;

            default :
                break;
        }
        mTextViewBeginDate.setOnClickListener(this);
        mTextViewEndDate.setOnClickListener(this);
    }

    /**
     * Set a listener on editText field, ensure that user can't launch an api request without any keywords
     */
    private void setTextEntryListenerForButtonActivation()
    {
        mEditTextKeyWords.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //set the boolean to true if something is present in the editText field, else set to false
                editTextState = s.toString().length() != 0;
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    //==========================================
    // Get data Methods
    //==========================================

    /**
     * Get the user entry from all checkboxes and from editText field, and initialise dedicated variables with data collected, after that
     * get specific screen data
     */
    private void getDataFromEntryFields()
    {
        //get the editText content as the first field of list (usefull to restore state of screen with sharedPreferences
        finalStringList.add(mEditTextKeyWords.getText().toString());

        //get the common fields data content
        for (CheckBox checkBox : checkBoxes1ist)
        {
            if (String.valueOf(checkBox.isChecked()).equals("true"))
                finalStringList.add(checkBox.getText().toString());
        }

        //get each specific fields
        switch (screenId)
        {
            case 0 :
                //for search screen : get data and check if the value need to be null (case of an empty string) or not
                String beginDate = checkIfNeedToBeNull(mTextViewBeginDate.getText().toString());
                String endDate = checkIfNeedToBeNull(mTextViewEndDate.getText().toString());

                mCallback.onButtonSearchClicked(finalStringList, beginDate, endDate);
                break;

            default:
                break;
        }
    }

    /**
     * Check if the entry String is empty (case of a non-checked checkbox), and replace it by null value
     * @param string
     *      the String to be checked
     * @return
     *      if entry String equals to empty String return null, else return the String without any modifications
     */
    private String checkIfNeedToBeNull(String string)
    {
        if (string.equals(""))
            return null;
        else return string;
    }

    //==========================================
    // Callback Method
    //==========================================

    /**
     * Create a callback to parent activity, display a log if error is thrown
     */
    public void createCallbackToParentActivity()
    {
        try
        {
           mCallback = (OnButtonSearchClickedCallback) getActivity();
        }
        catch (ClassCastException e)
        {
            Log.e(" createCallback ", " Must Implement OnButtonClickedListener ", e);
        }
    }
}