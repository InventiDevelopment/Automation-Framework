package cz.inventi.qa.framework.core.annotations.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designed to be used mainly with Endpoint class
 * to define its sole URL address.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EndpointSpecs {
    String url() default "";
}
