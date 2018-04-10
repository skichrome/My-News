package com.skichrome.mynews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.skichrome.mynews.controller.activities.MainActivity;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.util.NewYorkTimesStreams;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class test if the Article Search API return a list of at least one article when sending a request.
 */
@RunWith(AndroidJUnit4.class)
public class ArticleSearchAPIAndroidTest
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private ArrayList<String> queryStringList;

    @Before
    public void configureQueryStringList()
    {
        queryStringList = new ArrayList<>();

        queryStringList.add("Trump");
        queryStringList.add("Politics");
    }

    @Test
    public void doesTheArticleSearchAPIReturnSomeArticles()
    {
        //get the stream
        Observable<MainNewYorkTimesArticleSearch> observable = NewYorkTimesStreams.streamDownloadArticleSearchAPI(queryStringList, null, null);

        //create a new testObserver
        TestObserver<MainNewYorkTimesArticleSearch> testObserver = new TestObserver<>();

        //launch observable and check there is no errors
        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        //get the downloaded object
        MainNewYorkTimesArticleSearch mainNewYorkTimesArticleSearch = testObserver.values().get(0);

        assertThat("The list of article downloaded must contain at least one element", mainNewYorkTimesArticleSearch.getResponse().getDocs().size() != 0);

    }
}