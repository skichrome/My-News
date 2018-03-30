package com.skichrome.mynews;

import com.skichrome.mynews.model.mostpopularapimostviewed.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This test class assert that all fields required in the app are available for the most popular API, I use only the most viewed request
 */
public class ModelMostPopularAPIMostViewedUnitTest
{
    private MainNewYorkTimesMostPopular mMainNewYorkTimesMostPopular = new MainNewYorkTimesMostPopular();

    @Before
    public void setMostPopularFields()
    {
        Result resultTemp = new Result();
        List<Result> mResultsList = new ArrayList<>();

        resultTemp.setTitle("An amazing title");
        resultTemp.setUrl("http://wwww.anamazingarticle.fr/");
        resultTemp.setPublishedDate("22022018");

        mResultsList.add(resultTemp);
        mResultsList.add(resultTemp);

        mMainNewYorkTimesMostPopular.setResults(mResultsList);
    }

    @Test
    public void shouldReturnAllFieldNeededInMostPopularTab() throws Exception
    {
        for (Result result : mMainNewYorkTimesMostPopular.getResults())
        {
            assertThat("all variables must be present in the model fields", result.getUrl() != null);
            assertThat("all variables must be present in the model fields", result.getTitle() != null);
            assertThat("all variables must be present in the model fields", result.getPublishedDate() != null);
        }
    }
}