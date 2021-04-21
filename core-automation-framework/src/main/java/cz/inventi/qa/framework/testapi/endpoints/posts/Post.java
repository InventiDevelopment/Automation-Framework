package cz.inventi.qa.framework.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapi.dtos.PostDto;
import cz.inventi.qa.framework.testapi.endpoints.posts.comments.Comments;

@EndpointSpecs(url = "{postId}")
public class Post extends RestEndpoint<Post> {
    public Comments comments;

    public Post(AOProps props) {
        super(props);
    }

    public PostDto getPost(String postId) {
        setUrlParameter(postId);
        return callGet().as(PostDto.class);
    }
}
