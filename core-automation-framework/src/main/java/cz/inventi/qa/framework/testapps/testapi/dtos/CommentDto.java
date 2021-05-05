package cz.inventi.qa.framework.testapps.testapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private long postId;
    private long id;
    private String name;
    private String email;
    private String body;
}
