package com.skichrome.mynews;

import com.skichrome.mynews.Utils.ArticleSampleForAPIConverter;
import com.skichrome.mynews.Utils.DataAPIConverter;
import com.skichrome.mynews.model.articlesearchapi.Doc;
import com.skichrome.mynews.model.articlesearchapi.Headline;
import com.skichrome.mynews.model.articlesearchapi.MainNewYorkTimesArticleSearch;
import com.skichrome.mynews.model.articlesearchapi.Multimedium;
import com.skichrome.mynews.model.articlesearchapi.Response;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class DataAPIConverterTest
{
    private MainNewYorkTimesArticleSearch mMainNewYorkTimesArticleSearch = new MainNewYorkTimesArticleSearch();
    private DataAPIConverter dataAPIConverter = new DataAPIConverter();
    private List<ArticleSampleForAPIConverter> convertedArticles = new ArrayList<>();

    @Before
    public void setArticleSearchFields()
    {
        Response mResponse = new Response();
        Doc mDocTemp = new Doc();
        List<Doc> mDocListTemp = new ArrayList<>();
        List<Multimedium> mMultimediumList = new ArrayList<>();

        //define an url
        mDocTemp.setWebUrl("http://www.amazingarticleURL.fr/");

        //define a section name
        mDocTemp.setNewDesk("Amazing section name");

        //define the article title
        Headline headline = new Headline();
        headline.setMain("Amazing title of article !");
        mDocTemp.setHeadline(headline);

        //define a date
        mDocTemp.setPubDate("07/10/2017");

        //set a multimedium test array
        Multimedium mTempMultimedium = new Multimedium();
        mTempMultimedium.setUrl("http://www.the-amazin-image-url.fr");
        mTempMultimedium.setCaption("amazing description of image");
        mMultimediumList.add(mTempMultimedium);
        mMultimediumList.add(mTempMultimedium);

        mDocTemp.setMultimedia(mMultimediumList);

        mDocListTemp.add(mDocTemp);
        mDocListTemp.add(mDocTemp);

        mResponse.setDocs(mDocListTemp);

        //add all test fields
        mMainNewYorkTimesArticleSearch.setResponse(mResponse);

        //convert raw data and get only interesting fields
        convertedArticles = dataAPIConverter.convertArticleSearchResult(mMainNewYorkTimesArticleSearch.getResponse().getDocs());
    }

    @Test
    public void souldReturnAllFieldsNeededInArticleSearch() throws Exception
    {
        for (ArticleSampleForAPIConverter article : convertedArticles)
        {
            assertThat("all variables must be present in the model fields", article.getArticleUrl() != null);
            assertThat("all variables must be present in the model fields", article.getSection() != null);

            assertThat("all variables must be present in the model fields", article.getImageUrl() != null);
            assertThat("all variables must be present in the model fields", article.getDate() != null);
            assertThat("all variables must be present in the model fields", article.getTitle() != null);
        }
    }
}
