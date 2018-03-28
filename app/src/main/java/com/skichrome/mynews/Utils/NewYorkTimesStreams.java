package com.skichrome.mynews.Utils;

import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.model.mostpopularapimostviewed.MainNewYorkTimesMostPopular;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Used for Http Request on Top Stories API
 */

public class NewYorkTimesStreams
{
    public static Observable<MainNewYorkTimesTopStories> streamDownloadTopStoriesAPI (String section)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitTopStories.create(NewYorkTimesService.class);
        return newYorkTimesService.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }

    public static Observable<MainNewYorkTimesMostPopular> streamDownloadMostPopularAPI (String section)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitMostPopular.create(NewYorkTimesService.class);
        return newYorkTimesService.getMostPopular(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }

    public static Observable<MainNewYorkTimesArticleSearch> streamDownloadArticleSearchAPI(String searchQueryItem,
                                                                                           String beginDate,
                                                                                           String endDate,
                                                                                           String sort,
                                                                                           Boolean highLight)
    {
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofitArticleSearch.create(NewYorkTimesService.class);
        return newYorkTimesService.getCustomArticles(searchQueryItem, beginDate, endDate, sort, highLight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }
}