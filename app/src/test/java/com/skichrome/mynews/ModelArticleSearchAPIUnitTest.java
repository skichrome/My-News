package com.skichrome.mynews;

import com.skichrome.mynews.model.articlesearchapi.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This test class assert that all fields required in the app are available for the article Search API
 */

public class ModelArticleSearchAPIUnitTest
{
    private MainNewYorkTimesArticleSearch mMainNewYorkTimesArticleSearch = new MainNewYorkTimesArticleSearch();

    @Before
    public void setArticleSearchFields()
    {
        Response mResponse = new Response();
        Doc mDocTemp = new Doc();
        List<Doc> mDocListTemp = new ArrayList<>();
        List<Multimedium> mMultimediumList = new ArrayList<>();

        mDocTemp.setWebUrl("http://www.amazingarticleURL.fr/");
        mDocTemp.setSectionName("Amazing section name");

        //set a multimedium test array
        Multimedium mTempMultimedium = new Multimedium();
        mTempMultimedium.setUrl("http://www.the-amazin-image-url.fr");
        mTempMultimedium.setHeight(75);
        mTempMultimedium.setWidth(75);
        mTempMultimedium.setCaption("amazing description of image");
        mMultimediumList.add(mTempMultimedium);
        mMultimediumList.add(mTempMultimedium);

        mDocTemp.setMultimedia(mMultimediumList);

        mDocListTemp.add(mDocTemp);
        mDocListTemp.add(mDocTemp);

        mResponse.setDocs(mDocListTemp);

        mMainNewYorkTimesArticleSearch.setResponse(mResponse);
    }

    @Test
    public void souldReturnAllFieldsNeededInArticleSearch() throws Exception
    {
        for (Doc doc : mMainNewYorkTimesArticleSearch.getResponse().getDocs())
        {
            assertThat("all variables must be present in the model fields", doc.getWebUrl() != null);
            assertThat("all variables must be present in the model fields", doc.getSectionName() != null);

            for (Multimedium multimedium : doc.getMultimedia())
            {
                assertThat("all variables must be present in the model fields", multimedium.getUrl() != null);
                assertThat("all variables must be present in the model fields", multimedium.getHeight() != null);
                assertThat("all variables must be present in the model fields", multimedium.getWidth() != null);
                assertThat("all variables must be present in the model fields", multimedium.getCaption() != null);
            }
        }
    }
}
