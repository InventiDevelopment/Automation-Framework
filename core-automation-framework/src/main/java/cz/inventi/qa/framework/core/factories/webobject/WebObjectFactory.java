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

    public static <T extends WebObject> void initElements (T webObject) {
        initParentWebObjectFields(webObject);
        initElements(webObject.getClass(), webObject);
    }

    private static <T extends WebObject> void initElements (Class<? extends WebObject> parentWebObjectClass, T webObject) {
        for (Field childComponentField : parentWebObjectClass.getDeclaredFields()) {
            if (childComponentField.getType() == null) {
                continue;
            }

            if (!Modifier.toString(childComponentField.getModifiers()).equals("")) {
                childComponentField.setAccessible(true);
            }

            if (isRelatedWebComponentField(childComponentField)) {
                assignWebComponentField(childComponentField, webObject);
            }
        }
    }

    private static boolean isRelatedWebComponentField(Field field) {
        return WebComponent.class.isAssignableFrom(field.getType());
    }

    private static <T extends WebObject> void assignWebComponentField(Field childComponentField, T parentWebObject) {
        T childWebComponent = WebObjectFactory.reflectionInitClass((Class<T>) childComponentField.getType(), new Class<?>[]{WOProps.class}, new Object[]{getWOProps(childComponentField, parentWebObject)});

        try {
            childComponentField.setAccessible(true);
            childComponentField.set(parentWebObject, childWebComponent);
        } catch (IllegalAccessException e) {
            Log.fail("Could not assign field object '" + childComponentField.getType().getName() + "'.", e);
        }
    }

    private static <T extends WebObject> WOProps getWOProps (Field f, T parentWebObject) {
        String finalXpath = "";

        if (!hasNoParentAnnotation(f)) {
            finalXpath = PageBuilder.generateXpathWithParent(parentWebObject.getXpath(), f.getType().getDeclaredAnnotation(FindElement.class));
        }
        return new WOProps(finalXpath, parentWebObject);
    }

    private static boolean hasNoParentAnnotation (Field f) {
        NoParent childNoParentAnnotation = f.getType().getDeclaredAnnotation(NoParent.class);
        return childNoParentAnnotation != null && childNoParentAnnotation.value();
    }

    private static <T extends WebObject> void initParentWebObjectFields(T webObject) {
        if (WebObject.class.isAssignableFrom(webObject.getClass().getSuperclass())) {
            Class<T> parentClass = (Class<T>) webObject.getClass().getSuperclass();
            while (!parentClass.equals(WebObject.class) && !parentClass.equals(WebComponent.class)) {
                initElements(parentClass, webObject);
                parentClass = (Class<T>) parentClass.getSuperclass();
            }
        }
    }

    public static <T extends WebPage> T initPage (Class<T> webPageClass) {
        return reflectionInitClass(webPageClass, new Class[] {WOProps.class}, new Object[] {new WOProps(webPageClass.getDeclaredAnnotation(FindElement.class))});
    }

    public static <T extends WebObject> T reflectionInitClass (Class<T> klass, Class<?>[] constructorArgs, Object[] constructorParams) {
        if (constructorParams == null) constructorParams = new Object[0];
        if (constructorArgs == null) constructorArgs = new Class<?>[0];

        try {
            Constructor<?> klassConstructor = klass.getConstructor(constructorArgs);
            klassConstructor.setAccessible(true);
            return (T) klassConstructor.newInstance(constructorParams);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T extends WebObject> T reflectionInitClassWithProps (Class<T> klass, WOProps props) {
        return reflectionInitClass(klass, new Class[] {WOProps.class}, new Object[] {props});
    }
}
