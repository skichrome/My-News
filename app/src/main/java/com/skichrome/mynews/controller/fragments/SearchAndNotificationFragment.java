package com.skichrome.mynews.controller.fragments;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.skichrome.mynews.util.androidjob.ShowNotificationJob;

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
     * Used to know if the job is launched or not, to update the switch according to this field
     */
    private boolean stateJob;
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
    /**
     * Used to detect if user has typed at least one keyword before clicking on button
     */
    private boolean editTextState;

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
    // newInstance Method
    //=====================

    /**
     * Used to set the date fields by creating a DateNYTConverter Picker to allow user to select a date,also used to get user data
     * on search button by calling {@link SearchAndNotificationFragment#getDataFromEntryFields()} method.
     * Finally used to set the notifications to On or Off
     *
     * @param v
     *         The view clicked by the user
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
                editTextState = checkIfUserTypedText();
                //ensure that user have typed one keyword and checked one checkbox
                if (editTextState && checkBoxesState)
                {
                    //get the user request keywords
                    this.getDataFromEntryFields();

                    //start job
                    this.jobId = ShowNotificationJob.schedulePeriodicJob();

                    //memorise status of job
                    this.stateJob = true;
                    Log.e("-----JOB_STATUS-----", "onClick: job activated");

                    this.storeDataInSharedPreferences();
                }
                else
                {
                    mSwitch.setChecked(false);
                    Toast.makeText(getContext(), "You must select one category and type one word", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //cancel job
                ShowNotificationJob.cancelJob(jobId);
                //save the status of job
                this.stateJob = false;
                //delete all things saved in sharedPreferences, because if notifications are disabled we don't want to save fields
                searchParameters.edit().clear().apply();
                //re-insert the state of stateJob in case of re-lauching the app
                searchParameters.edit().putBoolean("STATE_JOB", stateJob).apply();

                Log.e("-----JOB_STATUS-----", "onClick: job disabled");
            }
        }

        // get data from entry fields here if user click on button, if he clicked on button, other "if" will not be executed
        if (v == mBtn)
        {
            //Used to detect if user has checked at least one checkbox before clicking on button
            boolean checkBoxesState = checkIfUserHaveSelectedAtLeastOneCheckBox();
            //ensure that user have typed one keyword and checked one checkbox, else show a Toast alert
            editTextState = checkIfUserTypedText();
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
                    String dayString = dayOfMonth + "";
                    String monthString = (month + 1) + "";

                    //convert the date in usable String format
                    if (monthString.length() == 1)
                        monthString = "0" + (month + 1);
                    if (dayString.length() == 1)
                        dayString = "0" + dayOfMonth;

                    //put date in sharedPreferences in readable format for ArticleSearch API
                    String dateString = year + monthString + dayString;
                    searchParameters.edit().putString("BEGIN_DATE", dateString).apply();

                    //display the selected date in field on screen
                    dateString = dayString + "/" + monthString + "/" + year;
                    mTextViewBeginDate.setText(dateString);
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
                    String dayString = dayOfMonth + "";
                    String monthString = (month + 1) + "";

                    //convert the date in usable String format
                    if (monthString.length() == 1)
                        monthString = "0" + (month + 1);
                    if (dayString.length() == 1)
                        dayString = "0" + dayOfMonth;

                    //put date in sharedPreferences in readable format for ArticleSearch API
                    String dateString = year + monthString + dayString;
                    searchParameters.edit().putString("END_DATE", dateString).apply();

                    //display the selected date in field on screen
                    dateString = dayString + "/" + monthString + "/" + year;
                    mTextViewEndDate.setText(dateString);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
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
        this.checkBoxes1ist = this.createCheckBoxesList();
        this.getDataFromBundle();
        this.removeUselessEntryFields();
        this.createCallbackToParentActivity();
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
     * Store data with sharedPreferences, used to restore screen state, and execute in planified task http request
     */
    private void storeDataInSharedPreferences()
    {
        //store the size of the list (used to restore the list)
        searchParameters.edit().putInt("SIZE_OF_LIST_KEYWORDS", finalStringList.size()).apply();

        //store all items stored in the list with unique id for each items
        for (String finalString : finalStringList)
            searchParameters.edit().putString("PREF_KEY_SEARCH_KEYWORDS_" + finalStringList.indexOf(finalString), finalString).apply();

        //store id of running job
        searchParameters.edit().putInt("ID_OF_JOB", jobId).apply();

        //store the job status
        searchParameters.edit().putBoolean("STATE_JOB", stateJob).apply();
    }

    /**
     * Check if user has checked at least one checkbox, and set the button color if true; he's not allowed to launch request without any checkbox selected
     *
     * @return boolean, true if a checked checkbox is detected, else return false
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

    /**
     * Check if there is something in the search editText
     *
     * @return boolean, true if there is something, else false
     */
    private boolean checkIfUserTypedText()
    {
        return mEditTextKeyWords.getText().toString().length() != 0;
    }

    /**
     * Get the data passed in bundle and set up fields on screen if data is stored in sharedPreferences
     */
    private void getDataFromBundle()
    {
        //get sharedPreferences
        searchParameters = getContext().getSharedPreferences("searchParameters", MODE_PRIVATE);

        //get bundle to get id of screen to display
        Bundle bundle = getArguments();
        if (bundle != null)
            this.screenId = bundle.getInt("ID_SEARCH_NOTIFICATION_FRAG");

        //set the switch to activated if a job is already started
        this.stateJob = searchParameters.getBoolean("STATE_JOB", false);
        this.mSwitch.setChecked(stateJob);

        //set the jobId if a job is already created and the app has been completely closed
        jobId = searchParameters.getInt("ID_OF_JOB", 0);

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

                if (wordToCompare != null && wordToCompare.equals(checkBox.getText().toString()))
                    checkBox.setChecked(true);
            }
        }

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
     * This method remove the fields useless for each screen, and set a click listener on needed fields
     */
    private void removeUselessEntryFields()
    {
        switch (screenId)
        {
            case 0:
                //For search screen : Remove the switch field and the separator because they are useless
                this.mViewSeparator.setVisibility(View.GONE);
                this.mSwitch.setVisibility(View.GONE);

                //set elevation for android version above lollipop
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    this.mBtn.setElevation(30);

                //set onClickListener for search button
                this.mBtn.setOnClickListener(this);
                break;

            case 1:
                //for notification screen : remove the date selection fields and the button because they are useless
                this.mLinearLayout.setVisibility(View.GONE);
                this.mBtn.setVisibility(View.GONE);

                //set onClickListener for switch
                this.mSwitch.setOnClickListener(this);
                break;

            default:
                break;
        }
        mTextViewBeginDate.setOnClickListener(this);
        mTextViewEndDate.setOnClickListener(this);
    }

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

        //create callback only for search screen
        switch (screenId)
        {
            case 0:
                mCallback.onButtonSearchClicked(finalStringList, searchParameters.getString("BEGIN_DATE", null), searchParameters.getString("END_DATE", null));
                break;

            default:
                break;
        }
    }

    //==========================================
    // Get data Methods
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

    //==========================================
    // Callback Interface
    //==========================================

    /**
     * Used for callback to activities
     */
    public interface OnButtonSearchClickedCallback
    {
        /**
         * Used when user click on search button
         *
         * @param mQueryList
         *         contain a list of query search keywords
         * @param mBeginDate
         *         contain the begin date for search request
         * @param mEndDate
         *         contain the end date for search request
         */
        void onButtonSearchClicked(ArrayList<String> mQueryList, String mBeginDate, String mEndDate);
    }
}