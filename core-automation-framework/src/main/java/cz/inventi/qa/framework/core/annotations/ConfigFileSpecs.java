package cz.inventi.qa.framework.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines name of configuration file which is later
 * used by ConfigManager when mapping data from
 * configuration files.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ConfigFileSpecs {
    String name() default "";
}
