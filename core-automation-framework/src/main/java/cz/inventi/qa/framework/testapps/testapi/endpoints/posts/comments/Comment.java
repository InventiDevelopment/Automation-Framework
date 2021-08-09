package cz.inventi.qa.framework.testapps.testapi.endpoints.posts.comments;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.annotations.api.ResponseSpecs;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.CommentDto;
import io.restassured.response.Response;

@EndpointSpecs(url = "{commentId}")
public class Comment extends RestEndpoint<Comment> {

    public Comment(AOProps props) {
        super(props);
    }

    @ResponseSpecs(passedDto = CommentDto.class)
    public Response getComment(String commentId) {
        setUrlParameter(commentId);
        return callGet();
    }
}
