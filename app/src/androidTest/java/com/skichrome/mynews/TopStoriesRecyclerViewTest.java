package com.skichrome.mynews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.skichrome.mynews.Utils.NewYorkTimesStreams;
import com.skichrome.mynews.controller.activities.MainActivity;
import com.skichrome.mynews.model.topstoriesapi.MainNewYorkTimesTopStories;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This class test if the RecyclerView have loaded some articles.
 */
@RunWith(AndroidJUnit4.class)
public class TopStoriesRecyclerViewTest
{
    private static final int ITEM_POSITION = 2;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToSpecificItemAndCheckIfDisplayed()
    {
        //get the stream
        Observable<MainNewYorkTimesTopStories> observable = NewYorkTimesStreams.streamDownloadTopStoriesAPI("home");

        //create a new testObserver
        TestObserver<MainNewYorkTimesTopStories> testObserver = new TestObserver<>();

        //launch observable and check there is no errors
        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        //get the downloaded object
        MainNewYorkTimesTopStories mainNewYorkTimesTopStories = testObserver.values().get(0);

        //check that view with specific title is displayed
        onView(withText(mainNewYorkTimesTopStories.getResults().get(ITEM_POSITION).getTitle())).check(matches(isDisplayed()));
    }
}