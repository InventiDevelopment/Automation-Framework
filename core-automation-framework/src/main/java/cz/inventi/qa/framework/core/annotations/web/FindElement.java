package cz.inventi.qa.framework.core.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Key annotation to define WebObject's identifiers by XPath
 * and index value when there is more objects in the DOM
 * found while only one is necessary.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FindElement {
    String xpath() default "";
    int index() default 0;
}
