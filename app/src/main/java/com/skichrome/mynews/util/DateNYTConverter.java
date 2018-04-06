package com.skichrome.mynews.util;

/**
 * This class is used to convert date format sent by NewYorkTimes API to a usable format
 */
public class DateNYTConverter
{
    // ====================
    // Fields
    // ====================
    /**
     * contains the final day of month
     */
    private String dayOfMonth;
    /**
     * contains the final month
     */
    private String month;
    /**
     * contains the final year
     */
    private String year;

    // ====================
    // Getters
    // ====================

    /**
     * get the converted day of month
     * @return
     *      String with converted day of month
     */
    public String getDayOfMonth()
    {
        return dayOfMonth;
    }

    /**
     * get the converted month
     * @return
     *      String with converted month
     */
    public String getMonth()
    {
        return month;
    }

    /**
     * get the converted year
     * @return
     *      String with converted year
     */
    public String getYear()
    {
        return year;
    }

    // ====================
    // Methods
    // ====================

    /**
     * Used to extract only the start of the date, for the NYT the number of needed characters is 9
     * @param beforeConversion
     *      The raw String to be cutted
     * @return
     *      the cutted String
     */
    public String deleteEndOfString(String beforeConversion)
    {
        //create a string builder (better for performance)
        StringBuilder str = new StringBuilder();

        //extract the 9 first characters from String and add to str
        for (int i = 0; i < 10; i++)
            str.append(beforeConversion.charAt(i));

        return str.toString();
    }

    /**
     * Extract data from cutted date (need to execute deleteEndOfString method first !)
     * @param dateFormatted
     *      the cutted String containing the date to be extracted
     */
    public void extractDataFromDateFormatted(String dateFormatted)
    {
        //StringBuilders used to store final String
        StringBuilder strDay = new StringBuilder();
        StringBuilder strMonth = new StringBuilder();
        StringBuilder strYear = new StringBuilder();

        // get the needed fields according to their position in the String
        for (int i = 0; i < dateFormatted.length(); i++)
        {
            if (i <= 3)
                strYear.append(dateFormatted.charAt(i));
            if (5 <= i && i <= 6)
                strMonth.append(dateFormatted.charAt(i));
            if (8 <= i)
                strDay.append(dateFormatted.charAt(i));
        }

        //update the class fields to allow external access of converted data
        this.dayOfMonth = strDay.toString();
        this.month = strMonth.toString();
        this.year = strYear.toString();
    }

    /**
     * Formatted output for debugging if needed
     * @return
     *      A String with date description
     */
    @Override
    public String toString()
    {
        return this.getDayOfMonth() + "/" + this.getMonth() + "/" + this.getYear();
    }
}