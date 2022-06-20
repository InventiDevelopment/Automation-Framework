package cz.inventi.qa.framework.testapps.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.annotations.api.ResponseSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostDto;
import cz.inventi.qa.framework.testapps.testapi.endpoints.posts.comments.Comments;
import io.restassured.response.Response;

@EndpointSpecs(url = "{postId}")
public class Post extends RestEndpoint<Post> {
    public Comments comments;

    public Post(AOProps props) {
        super(props);
    }

    public PostDto getPostMapped(String postId) {
        return getPost(postId).as(PostDto.class);
    }

    @ResponseSpecs(passedDto = PostDto.class)
    public Response getPost(String postId) {
        return setUrlParameter(postId).callGet();
    }
}
