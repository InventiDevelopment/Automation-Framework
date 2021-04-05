package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import org.testng.annotations.AfterClass;

public abstract class BaseApiTest {

    public BaseApiTest() {
        ConfigFiles configFiles = this.getClass().getSuperclass().getDeclaredAnnotation(ConfigFiles.class);
    }

    @AfterClass
    public void quit() {
    }
}
