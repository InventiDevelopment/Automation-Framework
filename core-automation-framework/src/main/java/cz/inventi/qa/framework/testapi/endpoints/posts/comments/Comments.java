package cz.inventi.qa.framework.testapi.endpoints.posts.comments;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapi.dtos.CommentDto;

import java.util.List;

@EndpointSpecs(url = "comments")
public class Comments extends RestEndpoint<Comments> {
    public Comment comment;

    public Comments(AOProps props) {
        super(props);
    }

    public List<CommentDto> getComments() {
        return callGet().getBody().jsonPath().getList("", CommentDto.class);
    }
}
