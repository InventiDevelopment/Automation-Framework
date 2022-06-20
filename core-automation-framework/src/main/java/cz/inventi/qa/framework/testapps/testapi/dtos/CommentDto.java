package cz.inventi.qa.framework.testapps.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    @NotNull
    @JsonProperty
    private long postId;
    @NotNull
    @JsonProperty
    private long id;
    @NotNull
    @JsonProperty
    private String name;
    @NotNull
    @JsonProperty
    private String email;
    @NotNull
    @JsonProperty
    private String body;
}
