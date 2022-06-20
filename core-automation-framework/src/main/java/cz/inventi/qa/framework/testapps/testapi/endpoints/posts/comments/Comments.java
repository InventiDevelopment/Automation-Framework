package cz.inventi.qa.framework.testapps.testapi.endpoints.posts.comments;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.annotations.api.ResponseSpecs;
import cz.inventi.qa.framework.core.data.enums.api.ResponseType;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.RestEndpoint;
import cz.inventi.qa.framework.testapps.testapi.dtos.CommentDto;
import io.restassured.response.Response;

import java.util.List;

@EndpointSpecs(url = "comments")
public class Comments extends RestEndpoint<Comments> {
    public Comment comment;

    public Comments(AOProps props) {
        super(props);
    }

    @ResponseSpecs(passedDto = CommentDto.class, responseType = ResponseType.ARRAY)
    public Response getComments() {
        return callGet();
    }

    public List<CommentDto> getCommentsMapped() {
        return getComments().getBody().jsonPath().getList("", CommentDto.class);
    }
}
