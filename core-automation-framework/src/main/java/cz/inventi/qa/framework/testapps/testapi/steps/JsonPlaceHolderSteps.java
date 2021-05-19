package cz.inventi.qa.framework.testapps.testapi.steps;

import cz.inventi.qa.framework.core.objects.test.assertions.api.RestAssert;
import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.testapps.testapi.JsonPlaceHolderApi;
import cz.inventi.qa.framework.testapps.testapi.dtos.CommentDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostRequestDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostResponseDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class JsonPlaceHolderSteps extends StepsBase {
    private final JsonPlaceHolderApi jsonPlaceHolderApi = getApiAppInstanceOf(JsonPlaceHolderApi.class);

    @Step("Get Post By ID ({postId})")
    public PostResponseDto getPostById(String postId) {
        PostResponseDto post = jsonPlaceHolderApi
                .posts
                .post
                .getPost(postId);

        Assert.assertEquals(post.getId(), Long.parseLong(postId), "Post ID is equal");
        return post;
    }

    @Step("Get Comments for Post ({postId})")
    public List<CommentDto> getPostComments(String postId) {
        List<CommentDto> comments = jsonPlaceHolderApi
                .posts
                .post
                .setUrlParameter(postId)
                .comments
                .getComments();

        Assert.assertNotEquals(comments.size(), 0, "There is at least 1 comment");
        Assert.assertEquals(comments.get(0).getPostId(), Long.parseLong(postId), "Comment's post ID is correct");
        return comments;
    }

    @Step("Get All Posts")
    public List<PostResponseDto> getAllPosts() {
        List<PostResponseDto> posts = jsonPlaceHolderApi
                .posts
                .getPosts()
                .getBody()
                .jsonPath()
                .getList("", PostResponseDto.class);

        Assert.assertNotEquals(posts.size(), 0, "There is at least 1 post");
        return posts;
    }

    @Step("Create New Post")
    public PostRequestDto createNewPost() {
        PostRequestDto newPost = generateRandomPost();
        Response response = RestAssert.assertStatusPassed(jsonPlaceHolderApi.posts.createPost(newPost));
        PostResponseDto createdPost = response.as(PostResponseDto.class);

        Assert.assertNotNull(createdPost.getId(), "Check that post ID is present");
        Assert.assertEquals(createdPost.getBody(), newPost.getBody(), "Check body content");
        Assert.assertEquals(createdPost.getTitle(), newPost.getTitle(), "Check title");
        return newPost;
    }

    private PostRequestDto generateRandomPost() {
        return new PostRequestDto(Long.parseLong("1"), RandomStringUtils.random(25, true, false), RandomStringUtils.random(160, true, false));
    }
}

