package com.skichrome.mynews.utils;

import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.model.mostpopularapimostviewed.MainNewYorkTimesMostPopular;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Used for Http Request on New York Times API
 */
public class NewYorkTimesStreams
{
    /**
     * Used for Http Request on Top Stories API
     *
     * @param section
     *      the section to filter article results
     * @return
     *      new observable
     */
    public static Observable<MainNewYorkTimesTopStories> streamDownloadTopStoriesAPI (String section)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitTopStories.create(NewYorkTimesService.class);
        return newYorkTimesService.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }

    /**
     * Used for Http Request on Most Popular API
     *
     * @param section
     *      the section to filter article results
     * @return
     *      new observable
     */
    public static Observable<MainNewYorkTimesMostPopular> streamDownloadMostPopularAPI (String section)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitMostPopular.create(NewYorkTimesService.class);
        return newYorkTimesService.getMostPopular(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }
    /**
     * Used for Http Request on Article Search API
     *
     * @param searchQueryItem
     *      a list of keywords used for the request
     * @param beginDate
     *      begin date filter for request on Most popular API
     * @param endDate
     *      end date filter for request on Most popular API
     * @return
     *      new observable
     *      Request on Most popular API
     */
    public static Observable<MainNewYorkTimesArticleSearch> streamDownloadArticleSearchAPI(List<String> searchQueryItem,
                                                                                           String beginDate,
                                                                                           String endDate)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitArticleSearch.create(NewYorkTimesService.class);
        return newYorkTimesService.getCustomArticles(searchQueryItem, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }
}