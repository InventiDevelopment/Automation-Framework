package cz.inventi.qa.framework.testapps.testapi.steps;

import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.core.objects.test.assertions.api.RestAssert;
import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.testapps.testapi.JsonPlaceHolderApi;
import cz.inventi.qa.framework.testapps.testapi.dtos.CommentDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.CreatePostRequestDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.CreatePostResponseDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class JsonPlaceHolderSteps extends StepsBase {
    private final JsonPlaceHolderApi jsonPlaceHolderApi = getApiAppInstanceOf(JsonPlaceHolderApi.class);

    @Step("Get Post By ID ({postId})")
    public PostDto getPostById(String postId) {
        PostDto post = jsonPlaceHolderApi
                .posts
                .post
                .getPostMapped(postId);
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
                .getCommentsMapped();
        Assert.assertNotEquals(comments.size(), 0, "There is at least 1 comment");
        Assert.assertEquals(comments.get(0).getPostId(), Long.parseLong(postId), "Comment's post ID is correct");
        return comments;
    }

    @Step("Get All Posts")
    public List<PostDto> getAllPosts() {
        List<PostDto> posts = jsonPlaceHolderApi
                .posts
                .getPosts()
                .getBody()
                .jsonPath()
                .getList("", PostDto.class);
        Assert.assertNotEquals(posts.size(), 0, "There is at least 1 post");
        return posts;
    }

    @Step("Create New Post")
    public CreatePostResponseDto createNewPost(long userId) {
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.random(userId);
        Response response = RestAssert.assertStatusPassed(
                jsonPlaceHolderApi
                        .posts
                        .createPost(createPostRequestDto)
        );
        CreatePostResponseDto createdPostResponse = response.as(CreatePostResponseDto.class);
        Assert.assertNotNull(createdPostResponse.getId(), "Check that post ID is present");
        return createdPostResponse;
    }
}

