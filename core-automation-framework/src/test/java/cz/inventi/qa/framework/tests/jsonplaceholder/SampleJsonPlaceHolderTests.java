package cz.inventi.qa.framework.tests.jsonplaceholder;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.testapi.steps.JsonPlaceHolderSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Epic("Provide Sample API Application Tests")
@Story("Test JsonPlaceholder Application")
public class SampleJsonPlaceHolderTests extends TestBase {
    private JsonPlaceHolderSteps jsonPlaceHolderSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        jsonPlaceHolderSteps = new JsonPlaceHolderSteps();
    }

    @Test(description = "Get Post by ID ({postId})")
    @Parameters({ "postId" })
    public void getPostByIdTest(@Optional("2") String postId) {
        jsonPlaceHolderSteps.getPostById(postId);
    }

    @Test(description = "Get Post's ({postId}) Comments")
    @Parameters({ "postId" })
    public void getPostCommentsTest(@Optional("2") String postId) {
        jsonPlaceHolderSteps.getPostComments(postId);
    }

    @Test(description = "Get All Posts")
    public void getAllPostsTest() {
        jsonPlaceHolderSteps.getAllPosts();
    }

    @Test(description = "Create New Post")
    public void createNewPostTest() {
        jsonPlaceHolderSteps.createNewPost(1);
    }
}

