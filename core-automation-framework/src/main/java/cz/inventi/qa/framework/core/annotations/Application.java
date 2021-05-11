package cz.inventi.qa.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines an application object. This annotation has to be
 * defined on each of the application-dependent objects or
 * on its ancestors so that the application can be initialized
 * by the framework.
 *
 * Also serves as a unique identifier for AppInstance object.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Application {
    String name() default "";
}
