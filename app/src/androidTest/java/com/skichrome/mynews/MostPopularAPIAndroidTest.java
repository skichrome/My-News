package com.skichrome.mynews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.skichrome.mynews.controller.activities.MainActivity;
import com.skichrome.mynews.model.mostpopularapimostviewed.MainNewYorkTimesMostPopular;
import com.skichrome.mynews.util.NewYorkTimesStreams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class test if the Most Popular API return a list of at least one article when sending a request.
 */
@RunWith(AndroidJUnit4.class)
public class MostPopularAPIAndroidTest
{
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void doesTheMostPopularAPIReturnSomeArticles()
    {
        //get the stream
        Observable<MainNewYorkTimesMostPopular> observable = NewYorkTimesStreams.streamDownloadMostPopularAPI("all-sections");

        //create a new testObserver
        TestObserver<MainNewYorkTimesMostPopular> testObserver = new TestObserver<>();

        //launch observable and check there is no errors
        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        //get the downloaded object
        MainNewYorkTimesMostPopular mainNewYorkTimesMostPopular = testObserver.values().get(0);

        assertThat("The list of article downloaded must contain at least one element", mainNewYorkTimesMostPopular.getResults().size() != 0);

    }
}