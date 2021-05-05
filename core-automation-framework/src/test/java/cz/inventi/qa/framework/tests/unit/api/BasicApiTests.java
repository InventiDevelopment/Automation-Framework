package cz.inventi.qa.framework.tests.unit.api;

import cz.inventi.qa.framework.core.objects.assertions.Assert;
import cz.inventi.qa.framework.testapi.dtos.CommentDto;
import cz.inventi.qa.framework.testapi.dtos.PostRequestDto;
import cz.inventi.qa.framework.testapi.dtos.PostResponseDto;
import cz.inventi.qa.framework.tests.core.ApiTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.List;

public class BasicApiTests extends ApiTestCase {

    @Test
    public void getPostByIdTest() {
        long POST_ID = Long.parseLong("1");
        PostResponseDto post = jsonPlaceHolderApi
                .posts
                .post
                .getPost(Long.toString(POST_ID));

        Assert.assertEquals(post.getId(), POST_ID, "Post ID is equal");
    }

    @Test
    public void getPostCommentsTest() {
        long POST_ID = Long.parseLong("2");
        List<CommentDto> comments = jsonPlaceHolderApi
                .posts
                .post
                .setUrlParameter(Long.toString(POST_ID))
                .comments
                .getComments();

        Assert.assertNotEquals(comments.size(), 0, "There is at least 1 comment");
        Assert.assertEquals(comments.get(0).getPostId(), POST_ID, "Comment's post ID is correct");
    }

    @Test
    public void getAllPostsTest() {
        List<PostResponseDto> posts = jsonPlaceHolderApi
                .posts
                .getPosts()
                .getBody()
                .jsonPath()
                .getList("", PostResponseDto.class);

        Assert.assertNotEquals(posts.size(), 0, "There is at least 1 post");
    }

    @Test
    public void createNewPostTest() {
        PostRequestDto newPost = generateRandomPost();
        PostResponseDto createdPost = jsonPlaceHolderApi
            .posts
            .createPost(newPost)
            .as(PostResponseDto.class);

        Assert.assertEquals(createdPost.getBody(), newPost.getBody(), "Check body content");
        Assert.assertEquals(createdPost.getTitle(), newPost.getTitle(), "Check title");
        Assert.assertEquals(createdPost.getUserId(), newPost.getUserId(), "Check userId");
    }

    private PostRequestDto generateRandomPost() {
        return new PostRequestDto(Long.parseLong("1"), RandomStringUtils.random(25, true, false), RandomStringUtils.random(160, true, false));
    }
}

