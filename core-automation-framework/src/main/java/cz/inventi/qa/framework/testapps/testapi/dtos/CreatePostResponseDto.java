package cz.inventi.qa.framework.testapps.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePostResponseDto {

    @NotNull
    @JsonProperty
    long id;

}
