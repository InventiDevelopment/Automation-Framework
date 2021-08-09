package cz.inventi.qa.framework.testapps.testapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.objects.api.JsonDto;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public class CreatePostRequestDto extends JsonDto {

    @JsonProperty
    long userId;
    @JsonProperty
    String title;
    @JsonProperty
    String body;

    public static CreatePostRequestDto random(long userId) {
        CreatePostRequestDto rand = new CreatePostRequestDto();
        rand.setBody(RandomStringUtils.randomAlphanumeric(150));
        rand.setTitle(RandomStringUtils.randomAlphanumeric(100));
        return rand;
    }

    public void compareTo(PostDto postDto) {
        Assert.assertEquals(getBody(), postDto.getBody(), "Check body content");
        Assert.assertEquals(getTitle(), postDto.getTitle(), "Check title");
        Assert.assertEquals(getUserId(), postDto.getUserId(), "Check user ID");
    }
}