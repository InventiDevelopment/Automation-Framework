package cz.inventi.qa.framework.core.annotations.api;

import cz.inventi.qa.framework.core.data.enums.api.ResponseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Helps to define what DTO class object
 * can be mapped to given endpoint call.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResponseSpecs {
    Class<?> passedDto() default Object.class;
    Class<?> failedDto() default Object.class;
    ResponseType responseType() default ResponseType.OBJECT;
}
