package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.ContactsPage;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.EN;

public class LoadingContactsTest extends RegressionTest {

    @Test
    public void contactsPageIsLoaded() {
        homePage.switchLanguageTo(EN)
                .getTopPanel()
                .clickMenuItem(MenuLink.CONTACTS)
                .assertPageTitle(ContactsPage.expectedTitle);
    }
}
