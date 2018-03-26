package com.skichrome.mynews;

import com.skichrome.mynews.Utils.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateUnitTest
{
    private Date date;
    private String cuttedString;

    @Before
    public void setUp() throws Exception
    {
        //This string represent the raw date format returned by the NewYorkTimes API in each articles
        String RAW_STRING = "2018-03-20T05:37:56-04:00";

        //create Date Object
        date = new Date();

        //Cut the end of the raw string using date method
        cuttedString = date.deleteEndOfString(RAW_STRING);

        //extract all needed resources with date methods
        date.extractDataFromDateFormatted(cuttedString);
    }

    @Test
    public void shouldReturnCorrectDateLengthIfEntryDateIsTooLong() throws Exception
    {
        assertEquals("2018-03-20", cuttedString);
    }

    @Test
    public void shouldReturnYearWithDateFormattedString() throws Exception
    {
        assertEquals("2018", date.getYear());
    }

    @Test
    public void shouldReturnMonthWithDateFormattedString() throws Exception
    {
        assertEquals("03", date.getMonth());
    }

    @Test
    public void shouldReturnDayOfMonthWithDateFormattedString() throws Exception
    {
        assertEquals("20", date.getDayOfMonth());
    }
}