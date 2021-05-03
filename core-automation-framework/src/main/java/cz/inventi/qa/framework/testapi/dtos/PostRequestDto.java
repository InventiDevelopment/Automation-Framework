package cz.inventi.qa.framework.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostRequestDto {

    @JsonProperty
    long userId;
    @JsonProperty
    String title;
    @JsonProperty
    String body;

    public PostRequestDto(long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
