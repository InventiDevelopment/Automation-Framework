package cz.inventi.qa.framework.testapi.endpoints.posts.comments;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapi.dtos.CommentDto;

@EndpointSpecs(url = "{commentId}")
public class Comment extends RestEndpoint<Comment> {

    public Comment(AOProps props) {
        super(props);
    }

    public CommentDto getComment(String commentId) {
        setUrlParameter(commentId);
        return callGet().as(CommentDto.class);
    }
}
