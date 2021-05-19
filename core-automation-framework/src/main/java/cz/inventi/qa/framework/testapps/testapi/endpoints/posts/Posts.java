package cz.inventi.qa.framework.testapps.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.utils.ApiUtils;
import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostRequestDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

@EndpointSpecs(url = "posts")
public class Posts extends RestEndpoint<Posts> {
    public Post post;

    public Posts(AOProps props) {
        super(props);
    }

    public Response getPosts() {
        return callGet();
    }

    public Response createPost(PostRequestDto postRequestDto) {
        return createRequest().body(ApiUtils.convertToJson(postRequestDto)).post();
    }

    public Response createRandomPost() {
        PostRequestDto newPost = generateRandomPost();
        return createRequest()
                .body(ApiUtils.convertToJson(newPost))
                .contentType(ContentType.JSON)
                .post();
    }

    private PostRequestDto generateRandomPost() {
        return new PostRequestDto(Long.parseLong("1"), RandomStringUtils.random(25, true, false), RandomStringUtils.random(160, true, false));
    }
}
