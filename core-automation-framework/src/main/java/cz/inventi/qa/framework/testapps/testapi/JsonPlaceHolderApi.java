package cz.inventi.qa.framework.testapps.testapi;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.api.ApiAuth;
import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.testapps.testapi.endpoints.posts.Posts;

@Application(name = "jsonplaceholder") // Define application endpoint root endpoint class
@ApiAuth(authType = ApiAuthMethod.NONE) // Define authentication method for any endpoint
public class JsonPlaceHolderApi extends Api {
    public Posts posts; // Define child endpoints

    public JsonPlaceHolderApi(AOProps props) {
        super(props);
    }
}
