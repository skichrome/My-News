package com.skichrome.mynews.Utils;

import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.model.mostpopularapimostviewed.MainNewYorkTimesMostPopular;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Used for Http request on NYT API
 */
public interface NewYorkTimesService
{
    /**
     * Request on TopStories API
     * @param section
     *      The section to return
     * @return
     *      new instance of Retrofit
     */
    @GET("{section}.json?api-key=e4a8b2318952484aa1aacefbd54277cd")
    Observable<MainNewYorkTimesTopStories> getTopStories(@Path("section") String section);

    Retrofit retrofitTopStories = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    /**
     * Request on Most popular API
     * @param section
     *      The section to return
     * @return
     *      new instance of Retrofit
     */
    @GET("{section}/1.json?api-key=e4a8b2318952484aa1aacefbd54277cd")
    Observable<MainNewYorkTimesMostPopular> getMostPopular(@Path("section") String section);

    Retrofit retrofitMostPopular = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/mostpopular/v2/mostemailed/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();




    @GET("articlesearch.json?api-key=e4a8b2318952484aa1aacefbd54277cd")
    Observable<MainNewYorkTimesArticleSearch> getCustomArticles(
            @Query("searchQueryItem") String searchQueryItem,
            @Query("beginDate") String beginDate,
            @Query("endDate") String endDate,
            @Query("sort") String sort,
            @Query("highLight") Boolean highLight);

    Retrofit retrofitArticleSearch = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/search/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}