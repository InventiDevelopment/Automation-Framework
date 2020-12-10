package cz.inventi.qa.framework.core.factories.webobject;

import cz.inventi.qa.framework.core.factories.PageBuilder;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.annotations.NoParent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;


public class WebObjectFactory {

    public static <T extends WebObject, W extends WebPage> void initElements (T webObject, WOProps<W> parentProps) {
        initParentWebObjectFields(webObject, parentProps);
        initElements(webObject.getClass(), webObject, parentProps);
    }

    private static <T extends WebObject, W extends WebPage> void initElements (Class<? extends WebObject> parentWebObjectClass, T webObject, WOProps<W> webPage) {
        for (Field childComponentField : parentWebObjectClass.getDeclaredFields()) {
            if (!Modifier.toString(childComponentField.getModifiers()).equals("")) {
                childComponentField.setAccessible(true);
            }

            if (isRelatedWebComponentField(childComponentField)) {
                assignWebComponentField(childComponentField, webObject, webPage);
            }
        }
    }

    private static boolean isRelatedWebComponentField(Field field) {
        return WebComponent.class.isAssignableFrom(field.getType());
    }

    private static <T extends WebObject, W extends WebPage> void assignWebComponentField(Field childComponentField, T parentWebObject, WOProps<W> parentProps) {
        T childWebComponent = WebObjectFactory.reflectionInitClass((Class<T>) childComponentField.getType(), new Class<?>[]{WOProps.class}, new Object[]{getWOProps(childComponentField, parentWebObject, parentProps)});

        try {
            childComponentField.setAccessible(true);
            childComponentField.set(parentWebObject, childWebComponent);
        } catch (IllegalAccessException e) {
            Log.fail("Could not assign field object '" + childComponentField.getType().getName() + "'.", e);
        }
    }

    private static <T extends WebObject, W extends WebPage> WOProps<W> getWOProps (Field f, T parentWebObject, WOProps<W> parentProps) {
        String finalXpath = "";

        if (!hasNoParentAnnotation(f)) {
            finalXpath = PageBuilder.generateXpathWithParent(parentWebObject.getXpath(), f.getType().getDeclaredAnnotation(FindElement.class));
        }
        return new WOProps<W>(finalXpath, parentWebObject, parentProps);
    }

    private static boolean hasNoParentAnnotation (Field f) {
        NoParent childNoParentAnnotation = f.getType().getDeclaredAnnotation(NoParent.class);
        return childNoParentAnnotation != null && childNoParentAnnotation.value();
    }

    private static <T extends WebObject, W extends WebPage> void initParentWebObjectFields(T webObject, WOProps<W> props) {
        if (WebObject.class.isAssignableFrom(webObject.getClass().getSuperclass())) {
            Class<T> parentClass = (Class<T>) webObject.getClass().getSuperclass();
            while (!parentClass.equals(WebObject.class) && !parentClass.equals(WebComponent.class)) {
                initElements(parentClass, webObject, props);
                parentClass = (Class<T>) parentClass.getSuperclass();
            }
        }
    }

    public static <T extends WebPage> T initPage (Class<T> webPageClass) {
        return reflectionInitClass(webPageClass, new Class[] {WOProps.class}, new Object[] {new WOProps<T>(webPageClass.getDeclaredAnnotation(FindElement.class), webPageClass)});
    }

    public static <T extends WebObject> T reflectionInitClass (Class<T> klass, Class<?>[] constructorArgs, Object[] constructorParams) {
        if (constructorParams == null) constructorParams = new Object[0];
        if (constructorArgs == null) constructorArgs = new Class<?>[0];

        try {
            Constructor<?> klassConstructor = klass.getConstructor(constructorArgs);
            klassConstructor.setAccessible(true);
            return (T) klassConstructor.newInstance(constructorParams);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not reflectively initialize " + klass + ". Please check that all web components extend WebComponent<T> and have generic <T extends WebObject> type defined.\n" + e.getCause());
        }
    }

    public static <T extends WebObject> T reflectionInitClassWithProps (Class<T> klass, WOProps<?> props) {
        return reflectionInitClass(klass, new Class[] {WOProps.class}, new Object[] {props});
    }
}
