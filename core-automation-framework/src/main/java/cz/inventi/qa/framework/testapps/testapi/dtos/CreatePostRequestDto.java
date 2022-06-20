package cz.inventi.qa.framework.testapps.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.objects.api.JsonDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public class CreatePostRequestDto extends JsonDto {

    @NotNull
    @JsonProperty
    long userId;
    @NotNull
    @JsonProperty
    String title;
    @NotNull
    @JsonProperty
    String body;

    public static CreatePostRequestDto random(long userId) {
        CreatePostRequestDto rand = new CreatePostRequestDto();
        rand.setBody(RandomStringUtils.randomAlphanumeric(150));
        rand.setTitle(RandomStringUtils.randomAlphanumeric(100));
        return rand;
    }
}