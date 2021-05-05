package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import org.testng.annotations.AfterClass;

public class WebFlow extends FlowBase {
    @AfterClass
    public void quit() {
        if (!FrameworkManager.getRunMode().equals(RunMode.DEBUG)) {
            getWebDriverManager().cleanDriver();
        }
    }
}
