package cz.inventi.qa.framework.testapps.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.CreatePostRequestDto;
import io.restassured.response.Response;

@EndpointSpecs(url = "posts")
public class Posts extends RestEndpoint<Posts> {
    public Post post;

    public Posts(AOProps props) {
        super(props);
    }

    public Response getPosts() {
        return callGet();
    }

    public Response createPost(CreatePostRequestDto createPostRequestDto) {
        return createRequest().body(createPostRequestDto.json()).post();
    }

    public Response createRandomPost() {
        return createRequest()
                .body(CreatePostRequestDto.random(1))
                .post();
    }
}
