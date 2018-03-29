package com.skichrome.mynews.Utils;

import com.skichrome.mynews.model.topstoriesapi.*;
import com.skichrome.mynews.model.articlesearchapi.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to convert raw list format sended by NYT API in a more generic list usable in a generic RecyclerView
 *
 * <p>This class allow to have only one recyclerView to display article in fragments.</p>
 */
public class DataAPIConverter
{
    //=========================================
    // Fields
    //=========================================

    /**
     * Contains the final formatted data list
     */
    private List<ArticleSampleForAPIConverter> formattedArticle;
    /**
     * Used as a final article format with only interesting fields defined and formatted
     */
    private ArticleSampleForAPIConverter articleSampleForAPIConverter;

    //=========================================
    // Constructor
    //=========================================

    /**
     * empty constructor
     */
    public DataAPIConverter()
    {
    }

    //=========================================
    // Public methods
    //=========================================

    /**
     * Used to convert Top Stories API data format
     *
     * <p>
     *     This method take a raw List in entry, for each item in list she create a new instance of {@link ArticleSampleForAPIConverter},
     *     initialize this new instance and add it to the final list returned at the end of the for loop.
     * </p>
     *
     * @param resultListTopStories
     *      raw list of articles
     * @return
     *      converted list
     */
    public List<ArticleSampleForAPIConverter> convertTopStoriesResult(List<Result> resultListTopStories)
    {
        //First initialize the final list
        formattedArticle = new ArrayList<>();

        //for each article returned by the API, create a new instance of ArticleSampleForAPIConverter and call the process method in it, finally add each new instance to the final list
        for (Result result : resultListTopStories)
        {
            articleSampleForAPIConverter = new ArticleSampleForAPIConverter();
            articleSampleForAPIConverter.configureArticleForTopStories(result);

            formattedArticle.add(articleSampleForAPIConverter);
        }

        //return the final list formatted
        return formattedArticle;
    }

    /**
     * Used to convert Most Popular API data format
     *
     * <p>
     *     This method take a raw List in entry, for each item in list she create a new instance of {@link ArticleSampleForAPIConverter},
     *     initialize this new instance and add it to the final list returned at the end of the for loop.
     * </p>
     *
     * @param resultsListMostPopular
     *      raw list of articles
     * @return
     *      converted list
     */
    public List<ArticleSampleForAPIConverter> convertMostPopularResult(List<com.skichrome.mynews.model.mostpopularapimostviewed.Result> resultsListMostPopular)
    {
        //First initialize the final list
        formattedArticle = new ArrayList<>();

        //for each article returned by the API, create a new instance of ArticleSampleForAPIConverter and call the process method in it, finally add each new instance to the final list
        for (com.skichrome.mynews.model.mostpopularapimostviewed.Result result : resultsListMostPopular)
        {
            articleSampleForAPIConverter = new ArticleSampleForAPIConverter();
            articleSampleForAPIConverter.configureArticleForMostPopular(result);

            formattedArticle.add(articleSampleForAPIConverter);
        }
        //return the final list formatted
        return formattedArticle;
    }

    /**
     * Used to convert Articles Search API data format
     *
     * <p>
     *     This method take a raw List in entry, for each item in list she create a new instance of {@link ArticleSampleForAPIConverter},
     *     initialize this new instance and add it to the final list returned at the end of the for loop.
     * </p>
     *
     * @param docsList
     *      raw list of articles
     * @return
     *      converted list
     */
    public List<ArticleSampleForAPIConverter> convertArticleSearchResult(List<Doc> docsList)
    {
        //First initialize the final list
        formattedArticle = new ArrayList<>();

        //for each article returned by the API, create a new instance of ArticleSampleForAPIConverter and call the process method in it, finally add each new instance to the final list
        for (Doc doc : docsList)
        {
            articleSampleForAPIConverter = new ArticleSampleForAPIConverter();
            articleSampleForAPIConverter.configureArticleForArticleSearch(doc);

            formattedArticle.add(articleSampleForAPIConverter);
        }
        //return the final list formatted
        return formattedArticle;
    }

    /**
     * For debug only, to ensure that data have been successfully converted
     * @return
     *      String with all articles described with some fields
     */
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < formattedArticle.size(); i++)
            str.append(i).append("-\t").append("[ ").append(formattedArticle.get(i).getTitle()).append(" - ").append(formattedArticle.get(i).getDate()).append(" - ").append(formattedArticle.get(i).getSection()).append(" ]").append(System.lineSeparator());

        return str.toString();
    }
}