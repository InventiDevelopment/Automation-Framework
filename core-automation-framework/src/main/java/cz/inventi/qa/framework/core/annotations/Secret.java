package cz.inventi.qa.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a secret field value that should
 * be hidden in all of the outputs.
 */
// TODO To be implemented to hide sensitive data in all of the outputs.
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Secret {
}
