package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.flows.FrameworkUnitTestsFlow;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import org.testng.annotations.Test;

public class ComponentListTests extends TestBase {

    @Test(description = "Test Working with ComponentLists")
    public void componentListTest() {
        FrameworkUnitTestsFlow.checkSideInfoItemsType(SideInfo.class);
        FrameworkUnitTestsFlow.checkSideInfoItemsCount(2);
        FrameworkUnitTestsFlow.tryToRetrieveSideInfoItem(1);
    }
}
