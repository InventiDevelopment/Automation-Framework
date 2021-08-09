package cz.inventi.qa.framework.testapps.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.annotations.api.ResponseSpecs;
import cz.inventi.qa.framework.core.data.enums.api.ResponseType;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.CreatePostRequestDto;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostDto;
import io.restassured.response.Response;

import java.util.List;

@EndpointSpecs(url = "posts") // Define URL part for given endpoint that will stack to the root URL
public class Posts extends RestEndpoint<Posts> {
    public Post post;

    public Posts(AOProps props) {
        super(props);
    }

    @ResponseSpecs(passedDto = PostDto.class, responseType = ResponseType.ARRAY)
    public Response getPosts() {
        return callGet();
    }

    public List<PostDto> getPostsMapped() {
        return getPosts()
                .getBody()
                .jsonPath()
                .getList("", PostDto.class);
    }

    @ResponseSpecs(passedDto = PostDto.class)
    public Response createPost(CreatePostRequestDto createPostRequestDto) {
        return createRequest().body(createPostRequestDto.json()).post();
    }

    @ResponseSpecs(passedDto = PostDto.class)
    public Response createRandomPost() {
        return createRequest()
                .body(CreatePostRequestDto.random(1))
                .post();
    }
}
