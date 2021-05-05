package cz.inventi.qa.framework.tests.jsonplaceholder;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.testapi.flows.JsonPlaceHolderBasicFlow;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class JsonPlaceHolderBasicTests extends TestBase {

    @Test(description = "Get Post by ID ('{postId}')")
    @Parameters({ "postId" })
    public void getPostByIdTest(@Optional("2") String postId) {
        JsonPlaceHolderBasicFlow.getPostById(postId);
    }

    @Test(description = "Get Post's ('{postId}') Comments")
    @Parameters({ "postId" })
    public void getPostCommentsTest(@Optional("2") String postId) {
        JsonPlaceHolderBasicFlow.getPostComments(postId);
    }

    @Test(description = "Get All Posts")
    public void getAllPostsTest() {
        JsonPlaceHolderBasicFlow.getAllPosts();
    }

    @Test(description = "Create New Post")
    public void createNewPostTest() {
        JsonPlaceHolderBasicFlow.createNewPost();
    }
}

