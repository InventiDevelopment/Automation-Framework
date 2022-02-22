package cz.inventi.qa.framework.testapps.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDto {

    @NotNull
    @JsonProperty
    long userId;
    @NotNull
    @JsonProperty
    long id;
    @NotNull
    @JsonProperty
    String title;
    @NotNull
    @JsonProperty
    String body;

}
