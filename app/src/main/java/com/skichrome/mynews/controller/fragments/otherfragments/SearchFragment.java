package com.skichrome.mynews.controller.fragments.otherfragments;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Allow to do some request on article sarch api with custom search keywords
 */
public class SearchFragment extends BaseSearchFragment implements View.OnClickListener
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
     * Used for callback to activities
     */
    private OnButtonSearchClickedCallback mCallback;

    //=========================
    // Empty Constructor
    //=========================

    /**
     * Required empty constructor
     */
    public SearchFragment()
    {
        // Required empty public constructor
    }

    //=========================
    // Overrided Methods
    //=========================

    /**
     * return a new instance of this fragment
     * @return
     *      new instance of this fragment
     */
    public static SearchFragment newInstance()
    {
        return new SearchFragment();
    }

    /**
     * @see BaseSearchFragment
     */
    @Override
    public void updateDesign()
    {
    }

    /**
     * @see BaseSearchFragment
     */
    @Override
    public void configureDesign()
    {
        this.createCallbackToParentActivity();
        this.removeUnnecessaryFields();

        mTextViewBeginDate.setOnClickListener(this);
        mTextViewEndDate.setOnClickListener(this);

        mBtn.setOnClickListener(this);
    }

    /**
     * Remove the switch field and the separator because they are useless in this fragment
     */
    private void removeUnnecessaryFields()
    {
        this.mViewSeparator.setVisibility(View.GONE);
        this.mSwitch.setVisibility(View.GONE);
    }

    /**
     * Used to set the date fields by creating a Date Picker to allow user to select a date, and also used to get user data on search button by calling {@link SearchFragment#getDataFromEntryFields()} method
     * @param v
     *      The view clicked by the user
     */
    @Override
    public void onClick(View v)
    {
        // get data from entry fields here if user click on button, if he clicked on button, other "if" will not be executed
        if (v == mBtn)
            this.getDataFromEntryFields();

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
     * Used to get user data in this specific fragment, and the common data by calling superclass method
     */
    @Override
    protected void getDataFromEntryFields()
    {
        super.getDataFromEntryFields();

        beginDate = checkIfNeedToBeNull(mTextViewBeginDate.getText().toString());
        endDate = checkIfNeedToBeNull(mTextViewEndDate.getText().toString());

        mCallback.onButtonSearchClicked(finalStringList, beginDate, endDate);
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

    //=====================
    // Methods
    //=====================

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