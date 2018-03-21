package com.skichrome.mynews;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Toni on 21/03/2018.
 */

public class ModelAPIUnitTest
{
    @Test
    public void doesTheTopStoriesAPIReturnTheGoodValue()
    {
        MainNewYorkTimesTopStories mMainNewYorkTimesTopStories = new MainNewYorkTimesTopStories();

        assertEquals("Object must contains a list", List<Result>);
    }
}
