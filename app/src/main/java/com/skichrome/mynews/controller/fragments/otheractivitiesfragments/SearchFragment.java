package com.skichrome.mynews.controller.fragments.otheractivitiesfragments;

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

    public interface OnButtonSearchClickedCallback
    {
        void onButtonSearchClicked(ArrayList<String> mQueryList, String mBeginDate, String mEndDate);
    }

    //=========================
    // Fields
    //=========================

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

    @Override
    public void onClick(View v)
    {
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

        if (v == mBtn)
        {
            super.getDataFromEntryFields();
        }
    }


    @Override
    protected void getDataFromEntryFields()
    {
        super.getDataFromEntryFields();
        mCallback.onButtonSearchClicked(finalStringList, beginDate, endDate);
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