package cz.inventi.qa.framework.tests.unit.api;

import cz.inventi.qa.framework.testapi.dtos.CommentDto;
import cz.inventi.qa.framework.testapi.dtos.PostDto;
import cz.inventi.qa.framework.tests.core.ApiTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BasicApiTests extends ApiTestCase {

    @Test
    public void getPostByIdTest () {
        long POST_ID = Long.parseLong("1");
        PostDto post = jsonPlaceHolderApi
                .posts
                .post
                .getPost(Long.toString(POST_ID));

        Assert.assertEquals(post.getId(), POST_ID);
    }

    @Test
    public void getPostCommentsTest () {
        long POST_ID = Long.parseLong("2");
        List<CommentDto> comments = jsonPlaceHolderApi
                .posts
                .post
                .setUrlParameter(Long.toString(POST_ID))
                .comments
                .getComments();

        Assert.assertNotEquals(comments.size(), 0);
        Assert.assertEquals(comments.get(0).getPostId(), POST_ID);
    }

    @Test
    public void getAllPostsTest () {
        List<PostDto> posts = jsonPlaceHolderApi
                .posts
                .getPosts();

        Assert.assertNotEquals(posts.size(), 0);
    }

    @Test
    public void createNewPostTest () {
        jsonPlaceHolderApi
            .posts
            .createRandomPostWithAssert();
    }
}

