package com.skichrome.mynews;

import com.skichrome.mynews.util.DateNYTConverter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateNYTConverterUnitTest
{
    private DateNYTConverter dateNYTConverter;
    private String cuttedString;

    @Before
    public void setUp()
    {
        //This string represent the raw dateNYTConverter format returned by the NewYorkTimes API in each articles
        String RAW_STRING = "2018-03-20T05:37:56-04:00";

        //create DateNYTConverter Object
        dateNYTConverter = new DateNYTConverter();

        //Cut the end of the raw string using dateNYTConverter method
        cuttedString = dateNYTConverter.deleteEndOfString(RAW_STRING);

        //extract all needed resources with dateNYTConverter methods
        dateNYTConverter.extractDataFromDateFormatted(cuttedString);
    }

    @Test
    public void shouldReturnCorrectDateLengthIfEntryDateIsTooLong()
    {
        assertEquals("2018-03-20", cuttedString);
    }

    @Test
    public void shouldReturnYearWithDateFormattedString()
    {
        assertEquals("2018", dateNYTConverter.getYear());
    }

    @Test
    public void shouldReturnMonthWithDateFormattedString()
    {
        assertEquals("03", dateNYTConverter.getMonth());
    }

    @Test
    public void shouldReturnDayOfMonthWithDateFormattedString()
    {
        assertEquals("20", dateNYTConverter.getDayOfMonth());
    }
}