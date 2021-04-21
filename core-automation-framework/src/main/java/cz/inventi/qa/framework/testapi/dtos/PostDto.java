package cz.inventi.qa.framework.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostDto {

    long userId;
    long id;
    String title;
    String body;

    @JsonCreator
    public PostDto(@JsonProperty("userId") long userId, @JsonProperty("id") long id,
                   @JsonProperty("title") String title, @JsonProperty("body") String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public PostDto(long userId,String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
