package com.skichrome.mynews;

import com.skichrome.mynews.model.topstoriesapi.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This test class assert that all fields required in the app are available for the top stories API
 */

public class ModelTopStoriesAPIUnitTest
{
    private List<Result> mResultsList;
    private List<Multimedium> mMultimedium;

    @Before
    public void setVariables()
    {
        mResultsList = new ArrayList<>();
        mMultimedium = new ArrayList<>();

        //set a test result array, with test defined fields
        Result mTempResult = new Result();
        mTempResult.setUrl("http://www.example.fr");
        mTempResult.setTitle("An amazing title of this article");
        mTempResult.setCreatedDate("01012017");

        //set a multimedium test array
        Multimedium mTempMultimedium = new Multimedium();
        mTempMultimedium.setUrl("http://www.the-amazin-image-url.fr");
        mTempMultimedium.setHeight(75);
        mTempMultimedium.setWidth(75);
        mTempMultimedium.setCaption("amazing description of image");
        mMultimedium.add(mTempMultimedium);
        mMultimedium.add(mTempMultimedium);

        mTempResult.setMultimedia(mMultimedium);

        //add two items to the list
        mResultsList.add(mTempResult);
        mResultsList.add(mTempResult);
    }

    @Test
    public void shouldTheTopStoriesAPIModelReturnAllNeededValues()
    {
        MainNewYorkTimesTopStories mMainNewYorkTimesTopStories = new MainNewYorkTimesTopStories();
        mMainNewYorkTimesTopStories.setResults(mResultsList);

        for (Result mResult : mMainNewYorkTimesTopStories.getResults())
        {
            assertThat("all variables must be present in the model fields", mResult.getUrl() != null);
            assertThat("all variables must be present in the model fields", mResult.getTitle() != null);
            assertThat("all variables must be present in the model fields", mResult.getCreatedDate() != null);
            assertThat("all variables must be present in the model fields", mResult.getMultimedia() != null);
            for (Multimedium mMultimedium : mResult.getMultimedia())
            {
                assertThat("all variables must be present in the model fields", mMultimedium.getUrl() != null);
                assertThat("all variables must be present in the model fields", mMultimedium.getHeight() != null);
                assertThat("all variables must be present in the model fields", mMultimedium.getWidth() != null);
                assertThat("all variables must be present in the model fields", mMultimedium.getCaption() != null);
            }
        }
    }
}
