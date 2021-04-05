package cz.inventi.qa.framework.testapi.endpoints.posts;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.testapi.dtos.PostDto;

import java.util.List;

@EndpointSpecs(url = "posts")
public class Posts extends Endpoint<Posts> {
    public Post post;

    public Posts(AOProps props) {
        super(props);
    }

    public List<PostDto> getPosts() {
        return callGet().getBody().jsonPath().getList("", PostDto.class);
    }
}
