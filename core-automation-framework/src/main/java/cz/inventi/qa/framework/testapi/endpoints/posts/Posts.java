package cz.inventi.qa.framework.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.ApiUtils;
import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapi.dtos.PostDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import java.util.List;

@EndpointSpecs(url = "posts")
public class Posts extends RestEndpoint<Posts> {
    public Post post;

    public Posts(AOProps props) {
        super(props);
    }

    public List<PostDto> getPosts() {
        return callGet().getBody().jsonPath().getList("", PostDto.class);
    }

    public Response createPost() {
        return createRequest().body(generateRandomPost()).post();
    }

    public Posts createRandomPostWithAssert() {
        PostDto newPost = generateRandomPost();
        PostDto mappedResponse = createRequest()
                .body(ApiUtils.convertToJson(newPost))
                .contentType(ContentType.JSON)
                .post()
                .body()
                .as(PostDto.class);

        Assert.assertEquals(mappedResponse.getBody(), newPost.getBody());
        Assert.assertEquals(mappedResponse.getTitle(), newPost.getTitle());
        Assert.assertEquals(mappedResponse.getUserId(), newPost.getUserId());
        return this;
    }

    private PostDto generateRandomPost() {
        return new PostDto(Long.parseLong("1"), RandomStringUtils.random(25, true, false), RandomStringUtils.random(160, true, false));
    }
}
