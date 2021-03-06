package com.skichrome.mynews.util;

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
 *     the date field is formatted using {@link DateNYTConverter} class, and there is a method who change section separators
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
    private DateNYTConverter dateNYTConverterConverter;

    //=========================================
    // Constructor
    //=========================================

    /**
     * Instanciate a new DateNYTConverter object
     */
    ArticleSampleForAPIConverter()
    {
        this.dateNYTConverterConverter = new DateNYTConverter();
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
     * @see ArticleNYTConverter#convertTopStoriesResult(List)
     */
    public void configureArticleForTopStories(Result mTopStoriesResult)
    {
        //convert String before assign it
        this.section = convertSectionId(mTopStoriesResult.getSection());

        this.title = mTopStoriesResult.getTitle();

        //convert date before set it to field
        dateNYTConverterConverter.extractDataFromDateFormatted(dateNYTConverterConverter.deleteEndOfString(mTopStoriesResult.getPublishedDate()));
        this.date = dateNYTConverterConverter.toString();

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
     * @see ArticleNYTConverter#convertMostPopularResult(List)
     */
    public void configureArticleForMostPopular(com.skichrome.mynews.model.mostpopularapimostviewed.Result mMostPopularResult)
    {
        //convert String before assign it
        this.section = convertSectionId(mMostPopularResult.getSection());

        this.title = mMostPopularResult.getTitle();

        //convert date before set it to field
        dateNYTConverterConverter.extractDataFromDateFormatted(dateNYTConverterConverter.deleteEndOfString(mMostPopularResult.getPublishedDate()));
        this.date = dateNYTConverterConverter.toString();

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
     * @see ArticleNYTConverter#convertArticleSearchResult(List)
     */
    public void configureArticleForArticleSearch(Doc mDoc)
    {
        //Check the String returned, and replace it with a more friendly String
        if (mDoc.getNewDesk() == null || mDoc.getNewDesk().equals("None"))
            this.section = "News";
        else
            this.section = convertSectionId(mDoc.getNewDesk()); //convert String before assign it

        this.title = mDoc.getHeadline().getMain();

        //convert date before set it to field
        if (mDoc.getPubDate() != null)
        {
            dateNYTConverterConverter.extractDataFromDateFormatted(dateNYTConverterConverter.deleteEndOfString(mDoc.getPubDate()));
            this.date = dateNYTConverterConverter.toString();
        }

        this.articleUrl = mDoc.getWebUrl();

        //Check if the list isn't empty, if it's empty don't initialize the url here
        //Add start of url here because the api response doesn't include all url
        //
        // Api responce  => images/2018/03/18/us/18dc-mueller/merlin_132922965_522d16ad-7508-4433-9f7f-1574a26aee65-superJumbo.jpg
        // Url format example => https://static01.nyt.com/images/2018/03/18/us/18dc-mueller/merlin_132922965_522d16ad-7508-4433-9f7f-1574a26aee65-superJumbo.jpg?quality=75&auto=webp
        if (mDoc.getMultimedia().size() != 0)
            this.imageUrl = "https://static01.nyt.com/" + mDoc.getMultimedia().get(0).getUrl();
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
