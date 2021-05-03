package cz.inventi.qa.framework.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostResponseDto {

    @JsonProperty
    long userId;
    @JsonProperty
    long id;
    @JsonProperty
    String title;
    @JsonProperty
    String body;

}
