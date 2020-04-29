package cz.inventi.sample.framework.core.factories;

import cz.inventi.sample.framework.core.objects.web.WebObject;
import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.factories.webelement.WebElementFactory;
import cz.inventi.sample.framework.core.factories.webobject.WebObjectFactory;

public class PageBuilder {

    public static <T extends WebObject> void initElements(T webObject) {
        WebObjectFactory.initElements(webObject); // Building custom WebObjects
        WebElementFactory.initElements(webObject); // Building Selenium WebElement
    }

    public static String generateIndexedXpath (String xpath, int index) {
        if (index > 0) {
            return "(" + xpath + ")[" + index + "]";
        } else if(index < 0) {
            throw new RuntimeException("index in method is not correct. Has to be higher than 0");
        }
        return xpath;
    }

    public static String generateIndexedXpath (FindElement locator) {
        if (locator != null) {
            return generateIndexedXpath(locator.xpath(), locator.index());
        } else {
            return "";
        }
    }

    public static String generateXpathWithParent (String parentXpath, FindElement componentXpath) {
        if (componentXpath != null) {
            return generateIndexedXpath(parentXpath + componentXpath.xpath(), componentXpath.index());
        } else {
            return "";
        }
    }
}
