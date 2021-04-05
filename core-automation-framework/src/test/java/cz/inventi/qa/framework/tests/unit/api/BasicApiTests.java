package cz.inventi.qa.framework.tests.unit.api;

import cz.inventi.qa.framework.tests.core.ApiTestCase;
import org.testng.annotations.Test;

public class BasicApiTests extends ApiTestCase {

    @Test
    public void getPostByIdTest () {
        jsonPlaceHolderApi
                .posts
                .post
                .getPost("1");
    }

    @Test
    public void getPostCommentsTest () {
        jsonPlaceHolderApi
                .posts
                .post
                .setUrlParameter("2")
                .comments
                .getComments();
    }

    @Test
    public void getAllPostsTest () {
        jsonPlaceHolderApi
                .posts
                .getPosts();
    }
}

