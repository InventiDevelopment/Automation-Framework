package cz.inventi.qa.framework.testapi;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.testapi.endpoints.posts.Posts;

@Application(name = "jsonPlaceholder")
public class JsonPlaceHolderApi extends Api {
    public Posts posts;

    public JsonPlaceHolderApi(AOProps props) {
        super(props);
    }
}
