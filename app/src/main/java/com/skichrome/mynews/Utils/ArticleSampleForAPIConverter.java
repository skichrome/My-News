package com.skichrome.mynews.Utils;

import com.skichrome.mynews.model.articlesearchapi.Doc;
import com.skichrome.mynews.model.topstoriesapi.Result;

import java.util.List;

/**
 * This class is used to format NYT API response
 *
 * <p>
 *     In this class for each article returned from API there is a method who get all interesting data and set it to class field.
 *     We need to create one instance of this class for each article sended by the api (grouped in a result or doc list in model).
 * </p>
 * <p>
 *     the date field is formatted using {@link Date} class, and there is a method who change section separators
 *     @see ArticleSampleForAPIConverter#convertSectionId(String)
 * </p>
 */
public class ArticleSampleForAPIConverter
{
    //=========================================
    // Fields
    //=========================================

    /**
     * A formatted version of section in API requests
     */
    private String section;
    /**
     * A formatted version of title in API requests
     */
    private String title;
    /**
     * A formatted version of date in API requests
     */
    private String date;
    /**
     * A formatted version of imageUrl in API requests
     */
    private String imageUrl;
    /**
     * A formatted version of articleUrl in API requests
     */
    private String articleUrl;

    /**
     * Used to format date to a usable form
     */
    private Date dateConverter;

    //=========================================
    // Constructor
    //=========================================

    /**
     * Instanciate a new Date object
     */
    ArticleSampleForAPIConverter()
    {
        this.dateConverter = new Date();
    }

    //=========================================
    // Getters
    //=========================================

    /**
     * get a formatted article section
     * @return
     *      String
     */
    public String getSection()
    {
        return section;
    }

    /**
     * get a formatted article title
     * @return
     *      String
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * get a formatted article date
     * @return
     *      String
     */
    public String getDate()
    {
        return date;
    }
    /**
     * get a formatted article imageUrl
     * @return
     *      String
     */
    public String getImageUrl()
    {
        return imageUrl;
    }
    /**
     * get a formatted article articleUrl
     * @return
     *      String
     */
    public String getArticleUrl()
    {
        return articleUrl;
    }

    //=========================================
    // Public Methods
    //=========================================

    /**
     * This method initialise class fields with one raw article in Top Stories API request
     * @param mTopStoriesResult
     *      Contain an article of API response
     *
     * @see DataAPIConverter#convertTopStoriesResult(List)
     */
    public void configureArticleForTopStories(Result mTopStoriesResult)
    {
        //convert String before assign it
        this.section = convertSectionId(mTopStoriesResult.getSection());

        this.title = mTopStoriesResult.getTitle();

        //convert date before set it to field
        dateConverter.extractDataFromDateFormatted(dateConverter.deleteEndOfString(mTopStoriesResult.getPublishedDate()));
        this.date = dateConverter.toString();

        this.articleUrl = mTopStoriesResult.getUrl();

        //Check if the list isn't empty, if it's empty don't initialize the url here
        if (mTopStoriesResult.getMultimedia().size() != 0)
            this.imageUrl = mTopStoriesResult.getMultimedia().get(0).getUrl();
        else
            this.imageUrl = null;
    }

    /**
     * This method initialise class fields with one raw article in Most Popular API request
     * @param mMostPopularResult
     *      Contain an article of API response
     *
     * @see DataAPIConverter#convertMostPopularResult(List)
     */
    public void configureArticleForMostPopular(com.skichrome.mynews.model.mostpopularapimostviewed.Result mMostPopularResult)
    {
        //convert String before assign it
        this.section = convertSectionId(mMostPopularResult.getSection());

        this.title = mMostPopularResult.getTitle();

        //convert date before set it to field
        dateConverter.extractDataFromDateFormatted(dateConverter.deleteEndOfString(mMostPopularResult.getPublishedDate()));
        this.date = dateConverter.toString();

        this.articleUrl = mMostPopularResult.getUrl();

        //Check if the list isn't empty, if it's empty don't initialize the url here
        if (mMostPopularResult.getMedia().size() != 0 && mMostPopularResult.getMedia().get(0).getMediaMetadata().size() != 0)
            this.imageUrl = mMostPopularResult.getMedia().get(0).getMediaMetadata().get(0).getUrl();
        else
            this.imageUrl = null;
    }

    /**
     * This method initialise class fields with one raw article in Article Search API request
     * @param mDoc
     *      Contain an article of API response
     *
     * @see DataAPIConverter#convertArticleSearchResult(List)
     */
    public void configureArticleForArticleSearch(Doc mDoc)
    {
        //Check the String returned, and replace it with a more friendly String
        if (mDoc.getNewDesk().equals("None"))
            this.section = "News";
        else
            this.section = convertSectionId(mDoc.getNewDesk()); //convert String before assign it

        this.title = mDoc.getHeadline().getMain();

        //convert date before set it to field
        dateConverter.extractDataFromDateFormatted(dateConverter.deleteEndOfString(mDoc.getPubDate()));
        this.date = dateConverter.toString();

        this.articleUrl = mDoc.getWebUrl();

        //Check if the list isn't empty, if it's empty don't initialize the url here
        if (mDoc.getMultimedia().size() != 0)
            this.imageUrl = mDoc.getMultimedia().get(0).getUrl();
        else
            this.imageUrl = null;
    }

    //=========================================
    // Private methods
    //=========================================

    /**
     * this method is used to convert section separator (from '/' to '>' )
     * @param rawSectionTitle
     *      the raw String to be converted
     * @return
     *      the converted String
     */
    private String convertSectionId (String rawSectionTitle)
    {
        return rawSectionTitle.replaceAll("/", ">");
    }
}
